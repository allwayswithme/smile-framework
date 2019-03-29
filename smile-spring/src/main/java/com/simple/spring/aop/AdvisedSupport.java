package com.simple.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * 代理相关的元数据，保存方法拦截器对象，被代理的对象，和匹配的规则对象
 */
public class AdvisedSupport {

	private TargetSource targetSource;

    private MethodInterceptor methodInterceptor;

    /**
     * 匹配的规则对象
     */
    private MethodMatcher methodMatcher;

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
