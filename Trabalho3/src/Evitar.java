

import java.util.concurrent.Semaphore;

import Utils.Utils;

public class Evitar extends Comportamentos {

	public Evitar(String ThreadName, Semaphore oEngTinhaRazao, RobotLegoEV3 robot) {
		super(ThreadName, oEngTinhaRazao, robot);
	}
	
	private int checkSensor() {
		//try {
		//	oEngTinhaRazao.acquire();
				return robot.SensorToque(1);
		//} finally {
		//	oEngTinhaRazao.release();
		//}
	}
	
	private void avoid() throws InterruptedException {
		int delay = 0;
		if(checkSensor() == 1) {
	
			System.out.println("EVITAR ON");
			try {
				oEngTinhaRazao.acquire();
					robot.SetVelocidade(50);
					robot.Parar(true);
					robot.Reta(-15);
					robot.CurvarEsquerda(0, 90);
					robot.Parar(false);
			}catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				oEngTinhaRazao.release();
				Thread.sleep( Utils.delay(-15, false, 0) + Utils.delay(1, true, 90));
			}
			System.out.println("EVITAR OFF");

		}
	}
	
	public void run() {
		for (;;) {
			if (getStatus() == Status.STOP) {
				try {
					autoSuspend();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				avoid();
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
