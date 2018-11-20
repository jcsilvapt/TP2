package Comportamentos;

import java.util.Random;

public abstract class Comportamentos extends Thread {

	private String name;
	private int time;
	
	private ISync actual;
	
	public Comportamentos(String name, ISync actual, int valor) {
		super(name);
		this.name = name;
		this.actual = actual;
		this.time = valor;

	}
	
	public void run() {
		for(;;) {
			try {
				this.actual.syncWait();
				
				Thread.sleep((new Random()).nextInt(time));
				System.out.print(this.name);
				
				this.actual.syncSignal();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
