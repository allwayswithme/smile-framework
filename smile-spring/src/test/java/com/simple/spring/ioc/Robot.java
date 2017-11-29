package com.simple.spring.ioc;

public class Robot {
	private String robotName;
	
	public Robot() {
		this.robotName = "Tom";
	}
	
	
	public void sayHello() {
		System.out.println("Hello,myName is " + this.robotName);
	}
}
