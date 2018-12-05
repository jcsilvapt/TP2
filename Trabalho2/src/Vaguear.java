
import java.util.concurrent.Semaphore;

import Utils.Utils;

public class Vaguear extends Comportamentos {

	private String nome;
	private int tempo;

	private int MAXDISTANCE = 60;
	private int MAXANGLE = 120;

	private String[] ACCOES = { "andar", "virar" };

	public Vaguear(String ThreadName, Semaphore oEngTinhaRazao, RobotLegoEV3 robot) {
		super(ThreadName, oEngTinhaRazao, robot);
	}

	private void randomMove() {
		int delay = 0;
		try {
			oEngTinhaRazao.acquire();
			int action = (int) (0 + Math.random() * 2);
			int move, radius, angle, toLeft;

			switch (ACCOES[action]) {
			case "andar":
				move = (int) (1 + Math.random() * MAXDISTANCE);
				int toBack = (int) (0 + Math.random() * 2);
				if (toBack == 1) {
					move = move * -1;
				}
				robot.Reta(move);
				delay = Utils.delay(move, false, 0);
				break;
			case "virar":
				toLeft = (int) (0 + Math.random() * 2);
				radius = (int) (1 + Math.random() * MAXDISTANCE);
				angle = (int) (1 + Math.random() * MAXANGLE);
				if (toLeft == 1) {
					robot.CurvarEsquerda(radius, angle);
				} else {
					robot.CurvarDireita(radius, angle);
				}
				delay = Utils.delay(radius, true, angle);
				break;
			default:
				break;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			oEngTinhaRazao.release();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void run() {
		for (;;) {
			if (getStatus() == Status.STOP) {
				try {
					robot.Parar(false);
					autoSuspend();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			randomMove();
		}
	}

}