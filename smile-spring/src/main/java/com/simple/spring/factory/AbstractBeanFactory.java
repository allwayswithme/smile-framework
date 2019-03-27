package com.simple.spring.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.simple.spring.beans.BeanDefinition;
import com.simple.spring.beans.BeanPostProcessor;

public abstract class AbstractBeanFactory implements BeanFactory {
	
	private Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String,BeanDefinition>();
	
	private final List<String> beanDefinitionNames = new ArrayList<String>();

	private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

	/**
	 * 在beanfactory内部的引用beanDefinitonMap添加一个beanDefinition
	 * @param name
	 * @param beanDefinition
	 */
	public void registerBeanDefinition(String name,BeanDefinition beanDefinition) {
		this.beanDefinitionNames.add(name);
		this.beanDefinitionMap.put(name, beanDefinition);
	}

	@Override
	public Object getBean(String name) throws Exception{
		
		BeanDefinition beanDefinition = this.beanDefinitionMap.get(name);
		if(beanDefinition == null){
			throw new IllegalArgumentException("No bean named " + name + "is defined");
		}
		Object bean = beanDefinition.getBean();
		//如果已经存在则返回该单例bean，如果不存在则新建该bean的实例
		if(bean == null){
			bean = this.doCreateBean(beanDefinition);
			bean = initializeBean(bean,name);
			beanDefinition.setBean(bean);
		}
		return bean;
	}

	public void preInstantiateSingletons() throws Exception {
		for (Iterator it = this.beanDefinitionNames.iterator(); it.hasNext();) {
			String beanName = (String) it.next();
			getBean(beanName);
		}
	}

	protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
		Object bean = createBeanInstance(beanDefinition);
		beanDefinition.setBean(bean);
		applyPropertyValues(bean, beanDefinition);
		return bean;
	}

	protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {

	}

	protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
		return beanDefinition.getBeanClass().newInstance();
	}

	/**
	 * aop注入切面时需要用到
	 */
	protected Object initializeBean(Object bean, String name) throws Exception {
		for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
			bean = beanPostProcessor.postProcessBeforeInitialization(bean, name);
		}

		// TODO:call initialize method
		for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
			bean = beanPostProcessor.postProcessAfterInitialization(bean, name);
		}
		return bean;
	}

	/**
	 * aop注入切面时需要用到
	 */
	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) throws Exception {
		this.beanPostProcessors.add(beanPostProcessor);
	}

	public List getBeansForType(Class type) throws Exception {
		List beans = new ArrayList<Object>();
		for (String beanDefinitionName : beanDefinitionNames) {
			if (type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {
				beans.add(getBean(beanDefinitionName));
			}
		}
		return beans;
	}
}
