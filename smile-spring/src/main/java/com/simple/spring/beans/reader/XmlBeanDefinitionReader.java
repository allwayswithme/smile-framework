package com.simple.spring.beans.reader;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.simple.spring.beans.BeanDefinition;
import com.simple.spring.beans.BeanReference;
import com.simple.spring.beans.PropertyValue;
import com.simple.spring.io.ClassLoaderResourceLoader;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
	

	
	public XmlBeanDefinitionReader(ClassLoaderResourceLoader resourceLoader) {
		super(resourceLoader);
	}

	/**
	 * 读取xml文件
	 * @param location classpath:spring-ioc.xml
	 */
	@Override
	public void loadBeanDefinitions(String location) throws Exception {
		InputStream inputStream = getResourceLoader().getResource(location).getInputStream();
		doLoadBeanDefinition(inputStream);
		
	}

    private void doLoadBeanDefinition(InputStream inputStream) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document doc = docBuilder.parse(inputStream);
		
		registerBeanDefinitions(doc);
		
	}

	private void registerBeanDefinitions(Document doc) {
		
		Element root = doc.getDocumentElement();
		
		parseBeanDefinitions(root);
		
	}

	protected void parseBeanDefinitions(Element root) {
		NodeList nl = root.getChildNodes();
		//遍历，并把bean一个个进行注册
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				processBeanDefinition(ele);
			}
		}
	}

	protected void processBeanDefinition(Element ele) {
		String name = ele.getAttribute("id");
		String className = ele.getAttribute("class");
		
		//新建一个bean的包装对象BeanDefinition并设置className
		BeanDefinition beanDefinition = new BeanDefinition();
		try {
			Class<?> c = Class.forName(className);
			beanDefinition.setBeanClass(c);
		} catch (ClassNotFoundException e) {
			System.out.println(className + "is not found;注入bean失败");
			e.printStackTrace();
		}
		
		//在当前注册信息中添加该bean，表示该bean已经注册
		getRegistry().put(name, beanDefinition);
		
		//继续读取xml信息并填充bean的属性
		processProperty(ele, beanDefinition);
	}

	private void processProperty(Element ele, BeanDefinition beanDefinition) {
		NodeList propertyNode = ele.getElementsByTagName("property");
		for (int i = 0; i < propertyNode.getLength(); i++) {
			Node node = propertyNode.item(i);
			if (node instanceof Element) {
				Element propertyEle = (Element) node;
				String name = propertyEle.getAttribute("name");
				String value = propertyEle.getAttribute("value");
				//将属性填进beanDefinition中，此时的bean并没有实例化
				if (value != null && value.length() > 0) {
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
				} else {
					String ref = propertyEle.getAttribute("ref");
					if (ref == null || ref.length() == 0) {
						throw new IllegalArgumentException("Configuration problem: <property> element for property '"
								+ name + "' must specify a ref or value");
					}
					
					//处理引用属性
					BeanReference beanReference = new BeanReference(ref);
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
				}
			}
		}
	}
	
}
