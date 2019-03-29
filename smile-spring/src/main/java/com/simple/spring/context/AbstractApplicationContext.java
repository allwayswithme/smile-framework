package com.simple.spring.context;

import com.simple.spring.beans.BeanPostProcessor;
import com.simple.spring.factory.AbstractBeanFactory;

import java.util.List;

public abstract class AbstractApplicationContext implements ApplicationContext{
	
	protected AbstractBeanFactory beanFactory;
	
	public AbstractApplicationContext(AbstractBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public void refresh() throws Exception{
		loadBeanDefinitions(beanFactory);
		registerBeanPostProcessors(beanFactory);
		onRefresh();
	};

	public abstract void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception;

	/**
	 * 注册aop切面bean
	 */
	protected void registerBeanPostProcessors(AbstractBeanFactory beanFactory) throws Exception {
		List beanPostProcessors = beanFactory.getBeansForType(BeanPostProcessor.class);
		for (Object beanPostProcessor : beanPostProcessors) {
			beanFactory.addBeanPostProcessor((BeanPostProcessor) beanPostProcessor);
		}
	}

	protected void onRefresh() throws Exception{
		beanFactory.preInstantiateSingletons();
	}

	@Override
	public Object getBean(String name) throws Exception {
		return this.beanFactory.getBean(name);
	}
}
