package com.simple.spring.beans;

/**
 * 对bean里面property的包装
 */
public class PropertyValue {
	
	private final String name;
	private final Object value;
	
	
	public PropertyValue(String name ,Object value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}
	
}
