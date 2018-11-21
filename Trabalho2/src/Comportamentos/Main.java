package Comportamentos;

import myRobot.myRobot;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Comportamentos vaguear, evitar;
		myRobot robot = new myRobot();
		
		robot.OpenEV3("EV3");
		
		vaguear = new Vaguear("Vaguear-1", robot);
		evitar = new Evitar("Evitar-1", robot, vaguear);


		vaguear.start();
		evitar.start();
		vaguear.Start();
		evitar.Start();

	}

}
