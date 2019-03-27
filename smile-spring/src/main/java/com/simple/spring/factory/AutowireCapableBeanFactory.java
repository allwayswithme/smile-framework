package com.simple.spring.factory;

import java.lang.reflect.Field;
import java.util.List;

import com.simple.spring.beans.BeanDefinition;
import com.simple.spring.beans.BeanReference;
import com.simple.spring.beans.PropertyValue;

/**
 * autowireBean工厂类，内部持有一个map保存beanName和map的关系
 *
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {
	
	/**
	 * 把bean的实例化拆分开来，可以恰当解耦，用BeanDefinition作为参数，可以更加普遍化
	 * @throws Exception 
	 * 
	 */
	@Override
	public Object createBean(BeanDefinition beanDefinition) throws Exception {
		try {
			Class<?> beanClass = beanDefinition.getBeanClass();
			Object bean = beanClass.newInstance();
			
			//applyPropertyValues(bean,beanDefinition);
			beanDefinition.setBean(bean);
			return bean;
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 将beanDefinition里面propertyValues的值通过反射机制放入bean中
	 */
	private void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {
		List<PropertyValue> pvs = beanDefinition.getPropertyValues().getPropertyValueList();
		for (PropertyValue propertyValue : pvs) {
			Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
			declaredField.setAccessible(true);
			Object value = propertyValue.getValue();
			if(value instanceof BeanReference) {
				BeanReference beanReference = (BeanReference)value;
				value = getBean(beanReference.getName());
			}
			
			declaredField.set(bean, value);
			
		}
		
		
	}


}
