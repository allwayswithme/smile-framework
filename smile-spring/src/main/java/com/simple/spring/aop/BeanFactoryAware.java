package com.simple.spring.aop;


import com.simple.spring.factory.BeanFactory;

/**
 * BeanFactoryAware接口暴露了获取beanFactory的能力，继承该接口的类拥有操作beanFactory的能力，也就能具体的操作bean了。
 */
public interface BeanFactoryAware {

    void setBeanFactory(BeanFactory beanFactory) throws Exception;
}
