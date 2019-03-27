package com.simple.spring.context;

import org.junit.Test;

import com.simple.spring.context.ApplicationContext;
import com.simple.spring.context.ClassPathXmlApplicationContext;
import com.simple.spring.ioc.HelloWorldService;

public class ApplicationContextTest {

    /**
     * 单纯的ioc注入
     * @throws Exception
     */
	@Test
	public void testIOC() throws Exception {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-ioc.xml");
		HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
		helloWorldService.helloWorld();

	}

    /**
     * 加上了aop
     * @throws Exception
     */
	@Test
	public void testAOP() throws Exception {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-aop.xml");
		//此处实现了懒加载，aop的切面也是在这一步动的手脚
		HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
		helloWorldService.helloWorld();

	}
}
