package Comportamentos;

public class SyncMonitor implements ISync {
	
	private Object syncPoint;
	private boolean isToWait;
	
	public SyncMonitor() {
		this.syncPoint = new Object();
		this.isToWait = true;
	}

	@Override
	public void syncWait() throws InterruptedException {
		synchronized (this.syncPoint) {
			while(this.isToWait) {
				this.syncPoint.wait();
			}
			this.isToWait= true;
		}
		
	}

	@Override
	public void syncSignal() throws InterruptedException {
		synchronized(this.syncPoint) {
			this.isToWait = false;
			this.syncPoint.notifyAll();
		}
		
	}

}
