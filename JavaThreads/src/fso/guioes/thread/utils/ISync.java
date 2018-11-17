package fso.guioes.thread.utils;

public interface ISync {
	public void syncWait() throws InterruptedException;
	
	public void syncSignal() throws InterruptedException;
}
