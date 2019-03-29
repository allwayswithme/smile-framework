package com.simple.spring.aop;

import com.simple.spring.ioc.HelloWorldService;
import com.simple.spring.ioc.HelloWorldServiceImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * AspectJ表达式匹配测试
 */
public class AspectJExpressionPointcutTest {

    /**
     * 测试类名匹配
     * @throws Exception
     */
    @Test
    public void testClassFilter() throws Exception {
        String expression = "execution(* com.simple.spring.ioc.*.*(..))";
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(expression);
        boolean matches = aspectJExpressionPointcut.getClassFilter().matches(HelloWorldService.class);
        Assert.assertTrue(matches);
    }

    /**
     * 测试方法名匹配
     * @throws Exception
     */
    @Test
    public void testMethodInterceptor() throws Exception {
        String expression = "execution(* com.simple.spring.ioc.*.*(..))";
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(expression);
        boolean matches = aspectJExpressionPointcut.getMethodMatcher().matches(HelloWorldServiceImpl.class.getDeclaredMethod("helloWorld"),HelloWorldServiceImpl.class);
        Assert.assertTrue(matches);
    }
}
