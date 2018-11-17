package Comportamentos;

import java.util.Random;

public class Vaguear extends Comportamentos {

	public Vaguear(String ThreadName, int baseSleepTime, int sleepTime, boolean avoid) {
		super(ThreadName, baseSleepTime, sleepTime, avoid);
		
	}
	
	private String randomMove() {
		String[] accao = {"|", "/", "->", "<-"};
		
		int t = (int) (0 + Math.random()*4);
		
		return accao[t];
	}
	
	@Override
	public void run() {
		Random rnd = new Random();
		int fullTime;
		for(;;) {
			if(this.getAvoid() && this.getThreadName().equals("Evitar[2]")) {
				System.out.println(this.getThreadName());
				fullTime = this.getBaseSleepTime() + rnd.nextInt(this.getSleepTime());
			}else {
				System.out.println(this.getThreadName() + " : " + randomMove());
				fullTime = this.getBaseSleepTime() + rnd.nextInt(this.getSleepTime());
			}
			try {
				Thread.sleep(fullTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread vaguear1 = new Vaguear("Vaguear[1]", 500, 100, false);
		Thread evitar = new Vaguear("Evitar[2]", 500, 100, true);
		
		vaguear1.start();
		evitar.start();
	}

	
}