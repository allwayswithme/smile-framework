package com.simple.spring.context;

import org.junit.Test;

import com.simple.spring.context.ApplicationContext;
import com.simple.spring.context.ClassPathXmlApplicationContext;
import com.simple.spring.ioc.HelloWorldService;

public class ApplicationContextTest {

	@Test
	public void test() throws Exception {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-ioc.xml");
		HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
		helloWorldService.helloWorld();

	}
}
