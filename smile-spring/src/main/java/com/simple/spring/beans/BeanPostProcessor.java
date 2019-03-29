package com.simple.spring.beans;

/**
 * BeanPostProcessor是BeanFactory提供的，在Bean初始化过程中进行扩展的接口。
 * 只要你的Bean实现了BeanPostProcessor接口，
 * 那么Spring在初始化时，会优先找到它们，并且在Bean的初始化过程中，调用这个接口，从而实现对BeanFactory核心无侵入的扩展
 */
public interface BeanPostProcessor {

	Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception;

	Object postProcessAfterInitialization(Object bean, String beanName) throws Exception;

}