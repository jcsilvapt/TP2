package Comportamentos;

import Utils.Utils;
import myRobot.myRobot;

public class Vaguear extends Comportamentos {

	private String nome;
	private int tempo;
	
	private int MAXDISTANCE = 20;
	private int MAXANGLE = 120;
	
	private myRobot robot;

	public Vaguear(String ThreadName, myRobot robot) {
		super(ThreadName);
		this.nome = ThreadName;
		this.robot = robot;
	}

	private void randomMove() {
		int action = (int) (0 + Math.random() * 2);
		int move, radius, angle, toLeft;

		int delay = 0;

		switch (ACCOES[action]) {
		case "andar":
			move = (int) (1 + Math.random() * MAXDISTANCE);
			int toBack = (int) (0 + Math.random() * 2);
			if ( toBack == 1) {
				move = move * -1;
			}
			robot.Reta(move);
			delay = Utils.delay(move, false, 0);
			break;
		case "virar":
			toLeft = (int) (0 + Math.random() * 2 );
			radius = (int) (1 + Math.random() * MAXDISTANCE);
			angle = (int) (1 + Math.random() * MAXANGLE);
			if(toLeft == 1){
				robot.CurvarEsquerda(radius, angle);
			}else {
				robot.CurvarDireita(radius, angle);
			}
			delay = Utils.delay(radius, true, angle);
			break;
		default:
			break;
		}
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		for (;;) {
			if(oEngTinhaRazao.availablePermits() == 1) {
				if (getStatus() == Status.STOP) {
					try {
						autoSuspend();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				randomMove();
			}
		}
	}

}