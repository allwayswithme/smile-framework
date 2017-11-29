package com.simple.spring.beans.reader;

public interface BeanDefinitionReader {
	
	void loadBeanDefinitions(String location) throws Exception;
	
}
