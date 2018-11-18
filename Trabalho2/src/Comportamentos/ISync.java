package Comportamentos;

public interface ISync {
	public void syncWait() throws InterruptedException;
	
	public void syncSignal() throws InterruptedException;
}
