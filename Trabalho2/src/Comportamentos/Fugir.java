package Comportamentos;

import myRobot.myRobot;

public class Fugir extends Comportamentos {
	
	private myRobot robot;
	
	public Fugir(String ThreadName, myRobot robot) {
		super(ThreadName);
		this.robot = robot;
	}
	
	private void doSomething() {
		
	}
	
	public void run() {
		for(;;) {
			if(oEngTinhaRazao.availablePermits() == 1) {
				if(getStatus() == Status.STOP) {
					try {
						autoSuspend();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				doSomething();
			}
		}
	}
}
