package com.simple.spring.aop;

/**
 * 工厂类ProxyFactory，用于根据TargetSource类型自动创建代理，这样就需要在调用者代码中去进行判断。
 * spring中并不是所有被继承的类都是抽象类，也有实体类直接被继承的
 */
public class ProxyFactory extends AdvisedSupport implements AopProxy {

	@Override
	public Object getProxy() {
		return this.createAopProxy().getProxy();
	}

	protected final AopProxy createAopProxy() {
		//把自己当作一个构造函数的参数传递给另外一个类，这里不会导致一个继承环吗？
		return new Cglib2AopProxy(this);
	}
}
