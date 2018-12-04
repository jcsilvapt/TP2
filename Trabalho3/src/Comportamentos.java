

import java.util.concurrent.Semaphore;

public class Comportamentos extends Thread {
	
	protected final RobotLegoEV3 robot;
	
	public enum Status {STOP, RUN};
	
	protected Semaphore control = new Semaphore(0);
	
	protected final Semaphore oEngTinhaRazao;
	
	protected Status currentStatus = Status.STOP;
	
	protected boolean isItRunning = false;
	
	public Comportamentos(String ThreadName, Semaphore oEngTinhaRazao, RobotLegoEV3 robot) {
		super(ThreadName);
		
		this.oEngTinhaRazao = oEngTinhaRazao;
		this.robot = robot;
	}
	
	public boolean getIsRunning() {
		return isItRunning;
	}
	
	public void setIsRunning(boolean value) {
		isItRunning = value;
	}
	
	public void Stop() throws InterruptedException {
		currentStatus = Status.STOP;
	}
	
	public void Start() {
		control.release();
	}
	
	public Status getStatus() {
		return currentStatus;
	}
	
	public Semaphore getControl() {
		return control;
	}
	
	protected void autoSuspend() throws InterruptedException {
		control.acquire();
		currentStatus = Status.RUN;
	}
	
}
