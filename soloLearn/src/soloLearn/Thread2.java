package soloLearn;

public class Thread2 extends gereThreads{

	public Thread2(String nome) {
		super(nome);
		superMetodo2();
	}
	
	public void superMetodo2() {
		System.out.println("Sou a thread-2 :DDDD");
	}

}
