package com.simple.spring.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 对propertyValues做一个包装，为什么不单独用List
 * 这是因为包装后可以对List进行一番操作
 * @author Administrator
 *
 */
public class PropertyValues {
	private List<PropertyValue> propertyValueList = new ArrayList<PropertyValue>();
	
	
	
	
	public List<PropertyValue> getPropertyValueList() {
		return propertyValueList;
	}
	public void setPropertyValueList(List<PropertyValue> propertyValueList) {
		this.propertyValueList = propertyValueList;
	}
	public void addPropertyValue(PropertyValue propertyValue) {
		//TODO 可以判断是否是重复属性，单独用List实现则判断不了
		this.propertyValueList.add(propertyValue);
	}
	public PropertyValue getPropertyValue(String name) {
		for (PropertyValue propertyValue : propertyValueList) {
			if(propertyValue.getName().equals(name)) {
				return propertyValue;
			}
		}
		return null;
	}
}
