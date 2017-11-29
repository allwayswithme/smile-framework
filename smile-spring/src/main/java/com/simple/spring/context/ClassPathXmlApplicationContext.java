package com.simple.spring.context;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.simple.spring.beans.BeanDefinition;
import com.simple.spring.beans.BeanReference;
import com.simple.spring.beans.PropertyValue;
import com.simple.spring.beans.reader.XmlBeanDefinitionReader;
import com.simple.spring.factory.AbstractBeanFactory;
import com.simple.spring.factory.AutowireCapableBeanFactory;
import com.simple.spring.io.ResourceLoader;
/**
 * 已经有了beanFactory了，为什么要用ApplicationContext
 * @author Administrator
 *
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

	private String configLocation;
	
	public ClassPathXmlApplicationContext(String configLocation) throws Exception {
		this(configLocation ,new AutowireCapableBeanFactory());
		
	}

	public ClassPathXmlApplicationContext(String configLocation,
			AbstractBeanFactory beanFactory) throws Exception {
		super(beanFactory);
		this.configLocation = configLocation;
		loadBeanDefinition();
	}


	/**
	 * 将reader的数据填进beanFactory
	 */
	@Override
	public void loadBeanDefinition() throws Exception {
		//调用reader读取xml中的内容，并将内容存储进Map<String, BeanDefinition>里面
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
		xmlBeanDefinitionReader.loadBeanDefinitions(configLocation);
		
		Map<String, BeanDefinition> mRegistry = xmlBeanDefinitionReader.getRegistry();
		
		//此时如果是懒加载，则不进行实例化，否则，则进行实例化
		this.beanFactory.getBeanDefinitonMap().putAll(mRegistry);
		
		//进行bean的实例化
		for (Entry<String, BeanDefinition>  entry : mRegistry.entrySet()) {
			BeanDefinition beanDefinition = entry.getValue();
			Class<?> c = beanDefinition.getBeanClass();
			Object bean = c.newInstance();
			beanDefinition.setBean(bean);
			
		}
		//进行bean里面属性的填充，如果是引用类型直接实例化，基本类型则直接赋值
		for (Entry<String, BeanDefinition>  entry : mRegistry.entrySet()) {
			
			BeanDefinition beanDefinition = entry.getValue();
			Object bean = beanDefinition.getBean();
			Class<?> c = bean.getClass();
			
			List<PropertyValue> valueList = beanDefinition.getPropertyValues().getPropertyValueList();
			
			for (PropertyValue propertyValue : valueList) {
				Object beanPorpertyValue = propertyValue.getValue();
				String name = propertyValue.getName();
				if(beanPorpertyValue instanceof BeanReference) {
					String refName = ((BeanReference) beanPorpertyValue).getName();
					beanPorpertyValue = mRegistry.get(refName).getBean();
					
					//将beanPorpertyValue设置进bean里面
					Field fs = c.getDeclaredField(name);
					fs.setAccessible(true);
					fs.set(bean, beanPorpertyValue);
				}else {
					
					Field fs = c.getDeclaredField(name);
					fs.setAccessible(true);
					fs.set(bean, beanPorpertyValue);
				}
			}
			
		}
		
	}
	
	@Override
	public void refresh() throws Exception {
		
	}


}
