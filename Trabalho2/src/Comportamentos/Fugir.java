package Comportamentos;

import java.util.Arrays;
import java.util.Collections;

import myRobot.myRobot;

public class Fugir extends Comportamentos {

	private myRobot robot;
	private Comportamentos vaguear;

	private final int DELAY = 250;
	private final int SAFEDISTANCE = 100;
	private final int MINDISTANCE  = 50;
	private int[] Storage = new int[3];
	private int count = 0;

	public Fugir(String ThreadName, myRobot robot, Comportamentos Vaguear) {
		super(ThreadName);
		this.robot = robot;
		this.vaguear = Vaguear;
	}

	private void executeRun() {
		int sensorValue = robot.SensorUS();
		if(sensorValue <= SAFEDISTANCE) {
			System.out.println(sensorValue);
			Storage[count] = sensorValue;
			count++;
		}
		if(count == 2) {
			Arrays.sort(Storage);
			if(Storage[1] > Storage[0] && Storage[1] < Storage[2] && Storage[1] <= MINDISTANCE) {
				try {
					vaguear.Stop();
					robot.SetVelocidade(Utils.Utils.convertionSpeed(Storage[1]));
					robot.Reta(Storage[1]+5);
					robot.SetVelocidade(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					vaguear.Start();
				}
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
