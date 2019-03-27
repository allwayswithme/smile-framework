package com.simple.spring.ioc;

public class RobotPOJO {
	private String robotName;
	
	public RobotPOJO() {
		this.robotName = "Tom";
	}
	
	
	public void sayHello() {
		System.out.println("Hello,myName is " + this.robotName);
	}
}
