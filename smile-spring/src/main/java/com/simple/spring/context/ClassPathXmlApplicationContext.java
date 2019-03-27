package com.simple.spring.context;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.simple.spring.beans.BeanDefinition;
import com.simple.spring.beans.BeanReference;
import com.simple.spring.beans.PropertyValue;
import com.simple.spring.beans.reader.AbstractBeanDefinitionReader;
import com.simple.spring.beans.reader.XmlBeanDefinitionReader;
import com.simple.spring.factory.AbstractBeanFactory;
import com.simple.spring.factory.AutowireCapableBeanFactory;
import com.simple.spring.io.ClassLoaderResourceLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

	//Logger log = LogManager.getLogger(this.getClass());

	private String configLocation;
	
	public ClassPathXmlApplicationContext(String configLocation) throws Exception {
		this(configLocation ,new AutowireCapableBeanFactory());
	}

	public ClassPathXmlApplicationContext(String configLocation,AbstractBeanFactory beanFactory) throws Exception {
		super(beanFactory);
		this.configLocation = configLocation;
		super.refresh();
	}


	/**
	 * 将reader的数据填进beanFactory
	 */
	@Override
	public void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception {
		//调用reader读取xml中的内容，并将内容存储进Map<String, BeanDefinition>里面
		AbstractBeanDefinitionReader abstractBeanDefinitionReader = new XmlBeanDefinitionReader(new ClassLoaderResourceLoader());
		abstractBeanDefinitionReader.loadBeanDefinitions(configLocation);
		
		Map<String, BeanDefinition> mRegistry = abstractBeanDefinitionReader.getRegistry();

		for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : mRegistry.entrySet()) {
			beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
		}

		/*this.beanFactory.getBeanDefinitionMap().putAll(mRegistry);
		
		//在spring中如果是懒加载，则不进行实例化，等到需要用到时再实例化
		//在本demo中不做懒加载优化，直接进行bean的实例化
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

			//
			for (PropertyValue propertyValue : valueList) {
				Object beanPropertyValue = propertyValue.getValue();
				String name = propertyValue.getName();
				if(beanPropertyValue instanceof BeanReference) {
					String refName = ((BeanReference) beanPropertyValue).getName();
					beanPropertyValue = mRegistry.get(refName).getBean();
					
					//将beanPorpertyValue设置进bean里面
					Field fs = c.getDeclaredField(name);
					fs.setAccessible(true);
					fs.set(bean, beanPropertyValue);
				}else {
					//log.info(name);
					Field fs = c.getDeclaredField(name);
					fs.setAccessible(true);
					fs.set(bean, beanPropertyValue);
				}
			}
			
		}*/
		
	}

}
