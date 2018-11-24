package Comportamentos;

import java.util.Arrays;
import java.util.Collections;

import myRobot.myRobot;

public class Fugir extends Comportamentos {

	private myRobot robot;
	private Comportamentos vaguear;

	private final int DELAY = 250;
	private final int SAFEDISTANCE = 100;
	private final int SAFESPEED = 75; // Valores precentuais
	private final int NORMALSPEED = 50; // Valores precentuais
	private int[] Storage = new int[3];
	private int count = 0;

	public Fugir(String ThreadName, myRobot robot, Comportamentos Vaguear) {
		super(ThreadName);
		this.robot = robot;
		this.vaguear = Vaguear;
	}

	private void executeRun() throws InterruptedException {
		int sensorValue = robot.SensorUS();
		if(sensorValue <= SAFEDISTANCE) {
			System.out.println(sensorValue);
			Storage[count] = sensorValue;
			count++;
		}
		if(count == 2) {
			Arrays.sort(Storage);
			if(Storage[1] <= SAFEDISTANCE) {
				vaguear.Stop();
				
			}
			count = 0;
		}
	}

	public void run() {
		for (;;) {
			if (oEngTinhaRazao.availablePermits() == 1) {
				if (getStatus() == Status.STOP) {
					try {
						autoSuspend();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					executeRun();
					Thread.sleep(DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
