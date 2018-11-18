package Comportamentos;

public class Vaguear extends Comportamentos {

	public Vaguear(String name, ISync actual, ISync next) {
		super(name, actual, next);
	}


	public static void main(String[] args) throws InterruptedException {
		
		ISync sync1, sync2, sync3;
		
		sync1 = new SyncSemaphore();
		sync2 = new SyncSemaphore();
		sync3 = new SyncSemaphore();
		
		Thread evitar1, evitar2, evitar3;
		
		evitar1 = new Vaguear("Vaguear-1\n", sync1, sync2);
		evitar2 = new Vaguear("Vaguear-2\n", sync2, sync3);
		evitar3 = new Vaguear("Vaguear-3\n", sync3, sync1);
		
		evitar1.start();
		evitar2.start();
		evitar3.start();
		
		sync1.syncSignal();
	}
	
	
}