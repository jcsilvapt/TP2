package Comportamentos;

import java.util.concurrent.Semaphore;

public class Comportamentos extends Thread {
	
	public enum Status {STOP, RUN};
	
	public String[] ACCOES = {"andar", "virar"};
	
	private Semaphore control = new Semaphore(0);
	
	public static Semaphore oEngTinhaRazao = new Semaphore(1);
	
	private Status currentStatus = Status.STOP;
	
	public Comportamentos(String ThreadName) {
		super(ThreadName);
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
