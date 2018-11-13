package Comportamentos;

public class Vaguear extends Comportamentos {
	
	public Vaguear() {
		System.out.println("Thread Vaguear - Inicializada");
	}
	
	public void run() {
		for(;;){
			break;
		}
	}
	
	public static void main(String[] args) {
		Vaguear vaguear = new Vaguear();
		vaguear.run();

	}

}