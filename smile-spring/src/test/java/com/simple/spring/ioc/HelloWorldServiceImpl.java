package com.simple.spring.ioc;

public class HelloWorldServiceImpl implements HelloWorldService {
	
	private RobotPOJO robot;
	private String worldName;

	@Override
	public void helloWorld() {
		System.out.println("hello," + this.worldName);
		//this.robot.sayHello();
	}

	public RobotPOJO getRobot() {
		return robot;
	}

	public void setRobot(RobotPOJO robot) {
		this.robot = robot;
	}

}
