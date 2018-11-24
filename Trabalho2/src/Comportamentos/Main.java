package Comportamentos;

import myRobot.myRobot;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		Comportamentos vaguear, fugir;
		myRobot robot = new myRobot();	
		
		
		robot.OpenEV3("EV3");
		
		vaguear = new Vaguear("vag1", robot);
		fugir = new Fugir("fugi", robot, vaguear);
		
		vaguear.start();
		fugir.start();
		fugir.Start();
	}

}
