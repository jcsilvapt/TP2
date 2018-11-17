package fso.guioes.thread;

import java.util.Random;

public class Worker extends Thread {
	
	private IMessage workerLogger;
	
	private ITimes times;

	public Worker(IMessage logger, ITimes times) {
		this.workerLogger = logger;
		this.times = times;
	}
	
	public void run() {
		Random rnd;
		rnd = new Random();
		
		int fullSleepTime, baseSleepTime, sleepTime;
		for(;;) {
			try {
				baseSleepTime = this.times.getBaseSleepTime();
				sleepTime = this.times.getSleepTime();
				
				fullSleepTime = baseSleepTime + rnd.nextInt( sleepTime );
				
				this.workerLogger.show( "Going to sleep %1.3f seconds", ((float)fullSleepTime)/1000.0);
				
				Thread.sleep( fullSleepTime );
			}
			catch (Exception e) {
				e.printStackTrace( System.err );
			}
		}
	}
}
