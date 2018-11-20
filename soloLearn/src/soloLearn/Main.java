package soloLearn;

public class Main {
	
	Thread disto;
	
	public Main(Thread disto) {
		this.disto = disto;
		init();
	}
	
	private void init() {
		disto.start();
	}
	
	
	public static void main(String[] args) {
		
		Thread primeira = new Thread1("teste1");
		Thread segunda = new Thread2("Teste 2");
		
		
		Main cenas1 = new Main(primeira);
	}

}
