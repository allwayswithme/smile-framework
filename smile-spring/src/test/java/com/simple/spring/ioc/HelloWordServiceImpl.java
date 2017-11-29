package com.simple.spring.ioc;

public class HelloWordServiceImpl implements HelloWorldService {
	
	private Robot robot;
	private String worldName;

	@Override
	public void helloWorld() {
		System.out.println("worldName============" + this.worldName);
		this.robot.sayHello();
	}

	public Robot getRobot() {
		return robot;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	
	
}
