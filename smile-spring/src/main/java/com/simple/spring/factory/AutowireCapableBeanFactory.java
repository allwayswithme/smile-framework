package com.simple.spring.factory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.simple.spring.aop.BeanFactoryAware;
import com.simple.spring.beans.BeanDefinition;
import com.simple.spring.beans.BeanReference;
import com.simple.spring.beans.PropertyValue;

/**
 * 可自动装配内容的BeanFactory
 * 内部持有一个map保存beanName和map的关系
 *
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

	/**
	 * 将beanDefinition里面propertyValues的值通过反射机制放入bean中
	 */
	protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {

		if(bean instanceof BeanFactoryAware){
			((BeanFactoryAware)bean).setBeanFactory(this);
		}

		List<PropertyValue> pvs = beanDefinition.getPropertyValues().getPropertyValueList();
		for (PropertyValue propertyValue : pvs) {
			Object value = propertyValue.getValue();
			if(value instanceof BeanReference){
				BeanReference beanReference = (BeanReference)value;
				value = getBean(beanReference.getName());
			}

			try {
				Method declaredMethod = bean.getClass().getDeclaredMethod(
						"set" + propertyValue.getName().substring(0,1).toUpperCase()
								+ propertyValue.getName().substring(1),value.getClass());
				declaredMethod.setAccessible(true);
				declaredMethod.invoke(bean,value);
			}catch (NoSuchMethodException e){
				Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
				declaredField.setAccessible(true);
				declaredField.set(bean, value);
			}
		}
	}
}
