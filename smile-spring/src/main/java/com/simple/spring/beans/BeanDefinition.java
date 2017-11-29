package com.simple.spring.beans;

/**
 * bean的包装类，内部持有对bean的引用
 * @author Administrator
 *
 */
public class BeanDefinition {
	
	private Class<?> beanClass;
	private Object bean;
	private String beanName;

	private PropertyValues propertyValues = new PropertyValues();
	
	public Object getBean() {
		return bean;
	}
	public void setBean(Object bean) {
		this.bean = bean;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public Class<?> getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}
	public PropertyValues getPropertyValues() {
		return propertyValues;
	}
	public void setPropertyValues(PropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}
	
	
}
