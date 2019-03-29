package com.simple.spring.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.simple.spring.beans.BeanDefinition;
import com.simple.spring.beans.BeanPostProcessor;

/**
 * 在AbstractBeanFactory中规范了bean的加载，实例化，初始化，获取的过程。
 */
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

	/**
	 * 获取bean
	 * @param name
	 * @return
	 * @throws Exception
	 */
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
			bean = this.initializeBean(bean,name);
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
		Object bean = this.createBeanInstance(beanDefinition);
		beanDefinition.setBean(bean);
		//父类调用子类的方法，AbstractBeanFactory调用AutowireCapableBeanFactory的方法
		this.applyPropertyValues(bean, beanDefinition);
		return bean;
	}

	protected abstract void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception;

	//todo 此处有线程安全问题
	protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
		return beanDefinition.getBeanClass().newInstance();
	}

	/**
	 * 初始化bean
	 * aop注入切面时需要用到
	 */
	protected Object initializeBean(Object bean, String name) throws Exception {
		/*for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
			bean = beanPostProcessor.postProcessBeforeInitialization(bean, name);
		}*/

		for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
			//此时返回的bean已经是一个代理类了
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

	/**
	 * 根据classType获取相关的bean
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List getBeansForType(Class type) throws Exception {
		List beans = new ArrayList<Object>();
		for (String beanDefinitionName : beanDefinitionNames) {
			//判断两个class是否有相互extends或者implement的关系，Class1.isAssignableFrom(Class2);
			if (type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {
				beans.add(getBean(beanDefinitionName));
			}
		}
		return beans;
	}
}
