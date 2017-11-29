package com.simple.spring.context;

import com.simple.spring.factory.AbstractBeanFactory;

public abstract class AbstractApplicationContext implements ApplicationContext{
	
	protected AbstractBeanFactory beanFactory;
	
	public AbstractApplicationContext(AbstractBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public Object getBean(String name) throws Exception {
		return this.beanFactory.getBean(name);
	}
	
	
	public abstract void refresh() throws Exception;
	
	public abstract void loadBeanDefinition() throws Exception;

}
