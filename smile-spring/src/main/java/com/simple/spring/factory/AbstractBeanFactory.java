package com.simple.spring.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.simple.spring.beans.BeanDefinition;

public abstract class AbstractBeanFactory implements BeanFactory {
	
	private Map<String,BeanDefinition> beanDefinitonMap = new ConcurrentHashMap<String,BeanDefinition>();
	
	private final List<String> beanDefinitionName = new ArrayList<String>();

	/**
	 * 在beanfactory内部的引用beanDefinitonMap添加一个beanDefinition
	 * @param name
	 * @param beanDefinition
	 */
	public void registerBeanDefinition(String name,BeanDefinition beanDefinition) {
		this.beanDefinitionName.add(name);
		this.beanDefinitonMap.put(name, beanDefinition);
	}
	
	

	public Map<String, BeanDefinition> getBeanDefinitonMap() {
		return beanDefinitonMap;
	}



	public void setBeanDefinitonMap(Map<String, BeanDefinition> beanDefinitonMap) {
		this.beanDefinitonMap = beanDefinitonMap;
	}



	public abstract Object createBean(BeanDefinition beanDefinition) throws Exception;

	@Override
	public Object getBean(String name) throws Exception{
		
		BeanDefinition beanDefinition = this.beanDefinitonMap.get(name);
		//createBean(beanDefinition);
		return beanDefinition.getBean();
		
	}
}
