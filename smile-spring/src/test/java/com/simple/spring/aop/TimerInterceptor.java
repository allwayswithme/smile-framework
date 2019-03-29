package com.simple.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 方法拦截器
 */
public class TimerInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		long lTime = System.currentTimeMillis();
		System.out.println("Invocation of Method " + invocation.getMethod().getName() + " start!");

		//执行该方法
		Object proceed = invocation.proceed();

		System.out.println("Invocation of Method " + invocation.getMethod().getName() + " end!");
		System.out.println("It takes " + (System.currentTimeMillis() - lTime) + " ms");

		return proceed;
	}
}
