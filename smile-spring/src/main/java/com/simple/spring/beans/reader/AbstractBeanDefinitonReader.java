package com.simple.spring.beans.reader;

import java.util.HashMap;
import java.util.Map;

import com.simple.spring.beans.BeanDefinition;
import com.simple.spring.io.ResourceLoader;

public abstract class AbstractBeanDefinitonReader implements BeanDefinitionReader {
	
	private ResourceLoader resourceLoader;
	private Map<String,BeanDefinition> registry;
	
	public AbstractBeanDefinitonReader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
		this.registry = new HashMap<String, BeanDefinition>();
	}

	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public Map<String, BeanDefinition> getRegistry() {
		return registry;
	}


}
