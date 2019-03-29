package com.simple.spring.context;

import com.simple.spring.beans.BeanDefinition;
import com.simple.spring.beans.reader.AbstractBeanDefinitionReader;
import com.simple.spring.beans.reader.XmlBeanDefinitionReader;
import com.simple.spring.factory.AbstractBeanFactory;
import com.simple.spring.factory.AutowireCapableBeanFactory;
import com.simple.spring.io.ClassLoaderResourceLoader;

import java.util.Map;

/**
 * ClassPathXmlApplicationContext对 Resouce 、 BeanFactory、BeanDefinition 进行了功能的封装，
 * 解决 根据地址获取资源通过 IoC 容器注册bean定义并实例化，初始化bean的问题，并提供简单运用他们的方法
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

	//Logger log = LogManager.getLogger(this.getClass());

	private String configLocation;
	
	public ClassPathXmlApplicationContext(String configLocation) throws Exception {
		this(configLocation ,new AutowireCapableBeanFactory());
	}

	public ClassPathXmlApplicationContext(String configLocation,AbstractBeanFactory beanFactory) throws Exception {
		super(beanFactory);
		this.configLocation = configLocation;
		super.refresh();
	}


	/**
	 * 将reader的数据填进beanFactory
	 */
	@Override
	public void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception {
		//调用reader读取xml中的内容，并将内容存储进Map<String, BeanDefinition>里面
		AbstractBeanDefinitionReader abstractBeanDefinitionReader = new XmlBeanDefinitionReader(new ClassLoaderResourceLoader());
		abstractBeanDefinitionReader.loadBeanDefinitions(configLocation);
		
		Map<String, BeanDefinition> mRegistry = abstractBeanDefinitionReader.getRegistry();

		for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : mRegistry.entrySet()) {
			beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
		}
	}
}
