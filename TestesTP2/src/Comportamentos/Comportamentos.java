package Comportamentos;

public class Comportamentos extends Thread {

	private boolean isWorld = false;

	public Comportamentos(String nome, boolean isWorld) {
		super(nome);
		this.isWorld = isWorld;
	}

	public void run() {
		for(;;) {
				try {
					if(isWorld)
						this.wait();
					
				} catch (InterruptedException e) {

					e.printStackTrace();
				} finnaly {
					this.notifyAll();
				}
		}
	}

}
