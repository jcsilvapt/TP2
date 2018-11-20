package Comportamentos;

public class Vaguear extends Comportamentos {

	public Vaguear(String name, ISync actual, int valor) {
		super(name, actual, valor);
	}


	public static void main(String[] args) throws InterruptedException {
		
		ISync sync1, sync2, sync3;
		
		sync1 = new SyncMonitor();
		sync2 = new SyncMonitor();
		
		Thread evitar1, evitar2, evitar3;
		
		evitar1 = new Vaguear("Vaguear-1\n", sync1, 100);
		evitar2 = new Vaguear("Vaguear-2\n", sync1, 10);
		
		evitar1.start();
		evitar2.start();
		
		sync1.syncSignal();
		sync2.syncSignal();		
		
	}
	
	
}