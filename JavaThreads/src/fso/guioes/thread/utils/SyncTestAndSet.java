package fso.guioes.thread.utils;

import java.util.concurrent.atomic.AtomicBoolean;

public class SyncTestAndSet implements ISync {
	
	private static final boolean ResourceIsBusy = true;
	private static final boolean ResourceIsFree = false;
	
	private AtomicBoolean syncPoint;

	public SyncTestAndSet() {
		this.syncPoint = new AtomicBoolean( ResourceIsBusy );
	}
	
	@Override
	public void syncWait() throws InterruptedException {
		while ( this.syncPoint.compareAndSet( 
				ResourceIsFree, ResourceIsBusy)==false )
			;
	}

	@Override
	public void syncSignal() throws InterruptedException {
		this.syncPoint.set( false );
	}	
}

