package com.simple.spring.beans.reader;

import java.util.HashMap;
import java.util.Map;

import com.simple.spring.beans.BeanDefinition;
import com.simple.spring.io.ClassLoaderResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
	
	private ClassLoaderResourceLoader resourceLoader;
	private Map<String,BeanDefinition> registry;
	
	public AbstractBeanDefinitionReader(ClassLoaderResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
		this.registry = new HashMap<String, BeanDefinition>();
	}

	public ClassLoaderResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public Map<String, BeanDefinition> getRegistry() {
		return registry;
	}


}
