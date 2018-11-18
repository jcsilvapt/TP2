package Comportamentos;

import java.util.Random;

public abstract class Comportamentos extends Thread {

	private String name;
	
	private ISync actual, next;
	
	public Comportamentos(String name, ISync actual, ISync next) {
		super(name);
		this.name = name;
		this.actual = actual;
		this.next = next;
	}
	
	public void run() {
		for(;;) {
			try {
				this.actual.syncWait();
				
				Thread.sleep((new Random()).nextInt(250));
				System.out.print(this.name);
				
				this.next.syncSignal();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
