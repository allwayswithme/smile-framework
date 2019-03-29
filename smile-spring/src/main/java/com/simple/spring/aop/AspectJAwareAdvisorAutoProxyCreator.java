package com.simple.spring.aop;

import com.simple.spring.beans.BeanPostProcessor;
import com.simple.spring.factory.AbstractBeanFactory;
import com.simple.spring.factory.BeanFactory;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.List;

/**
 * AspectJAwareAdvisorAutoProxyCreator就是AspectJ方式实现织入的核心。
 * 它其实是一个BeanPostProcessor。在这里它会扫描所有Pointcut，并对bean做织入
 */
public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {

	private AbstractBeanFactory abstractBeanFactory;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
		return bean;
	}

	/**
	 * 织入
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
		if (bean instanceof AspectJExpressionPointcutAdvisor) {
			return bean;
		}
		if (bean instanceof MethodInterceptor) {
			return bean;
		}
		List<AspectJExpressionPointcutAdvisor> advisors = abstractBeanFactory.getBeansForType(AspectJExpressionPointcutAdvisor.class);
		//遍历切面然后织入
		for (AspectJExpressionPointcutAdvisor advisor : advisors) {
			if (advisor.getPointcut().getClassFilter().matches(bean.getClass())) {
                ProxyFactory advisedSupport = new ProxyFactory();
				advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
				advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

				TargetSource targetSource = new TargetSource(bean, bean.getClass(), bean.getClass().getInterfaces());
				advisedSupport.setTargetSource(targetSource);

				return advisedSupport.getProxy();
			}
		}
		return bean;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws Exception {
		this.abstractBeanFactory = (AbstractBeanFactory) beanFactory;
	}
}
