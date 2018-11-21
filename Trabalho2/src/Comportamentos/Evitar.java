package Comportamentos;

import javax.rmi.CORBA.Util;

import Utils.Utils;
import myRobot.myRobot;

public class Evitar extends Comportamentos {
	
	private String name;
	private myRobot robot;
	private Comportamentos vaguear;
	
	public Evitar(String ThreadName, myRobot robot, Comportamentos Vaguear) {
		super(ThreadName);
		this.name = ThreadName;
		this.robot = robot;
		this.vaguear = Vaguear;
	}
	
	private boolean checkSensor() {
		return robot.SensorToque();
	}
	
	private void avoid() throws InterruptedException {
		int delay = 0;
		if(checkSensor()) {
			try {
				oEngTinhaRazao.acquire();
				System.out.println("EVITAR ON");
				robot.Parar(true);
				robot.Reta(-15);
				delay = Utils.delay(-15, false, 0);
				Thread.sleep(delay);
				robot.CurvarEsquerda(0, 90);
				delay = Utils.delay(1, true, 90);
				Thread.sleep(delay);
				System.out.println("EVITAR OFF");
			}catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				oEngTinhaRazao.release();
			}

		}
	}
	
	public void run() {
		for (;;) {
			if (getStatus() == Status.STOP) {
				try {
					autoSuspend();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				avoid();
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
