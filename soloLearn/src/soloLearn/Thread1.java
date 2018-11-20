package soloLearn;

public class Thread1 extends gereThreads {

	public Thread1(String nome) {
		super(nome);
		superMetodo();
	}
	
	private void superMetodo() {
		System.out.println("Sou a Thread1 :)");
	}

}
