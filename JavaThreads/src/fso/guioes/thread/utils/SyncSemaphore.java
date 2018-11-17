package fso.guioes.thread.utils;

import java.util.concurrent.Semaphore;

public class SyncSemaphore implements ISync {
	
	private Semaphore syncPoint;

	public SyncSemaphore() {
		this.syncPoint = new Semaphore(0);
	}
	
	@Override
	public void syncWait() throws InterruptedException {
		this.syncPoint.acquire();
	}

	@Override
	public void syncSignal() throws InterruptedException {
		this.syncPoint.release();
	}	
}
