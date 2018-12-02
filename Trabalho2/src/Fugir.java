import java.util.concurrent.Semaphore;

public class Fugir extends Comportamentos {

	private Comportamentos vaguear;

	private enum runStatus {
		NORMAL, RUN
	};

	private runStatus status = runStatus.NORMAL;

	private boolean memory = false;

	private final int DELAY = 500;
	private final int SAFEDISTANCE = 60;
	private int initialDistance = 0;
	private int actualDistance = 0;
	private int actualSpeed = 50; // default 50%

	public Fugir(String ThreadName, Semaphore oEngTinhaRazao, RobotLegoEV3 robot, Comportamentos vaguear) {
		super(ThreadName, oEngTinhaRazao, robot);

		this.vaguear = vaguear;
	}

	private void setNewSpeed(int actual, int initial) {
		try {
			oEngTinhaRazao.acquire();
				
			int delta =  actual - initial;
			actualSpeed += delta*-1;
			
			System.out.println("Velocidade (antes da censura): " + actualSpeed + " %");
			
			if(actualSpeed < 50) {
				actualSpeed = 50;
			}
			if(actualSpeed > 100) {
				actualSpeed = 100;
			}
			
			System.out.println("Velocidade (depois da censura): " + actualSpeed + " %");
			int toRun = actualDistance - SAFEDISTANCE;
			robot.Reta(Math.abs(toRun));
			robot.SetVelocidade(actualSpeed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			oEngTinhaRazao.release();
		}
	}

	public void run() {
		initialDistance = robot.SensorUS(2);
		for (;;) {
			try {
				Thread.sleep(DELAY);
				if (getStatus() == Status.STOP) {
					autoSuspend();
				}
				actualDistance = robot.SensorUS(2);
				System.out.println("distancia: " + actualDistance);
				if (vaguear.getIsRunning()) {
					memory = true;
				} else {
					memory = false;
				}
				switch (status) {
				case NORMAL:
					if (actualDistance < initialDistance && actualDistance < SAFEDISTANCE) {
						status = runStatus.RUN;
						if (memory) {
							vaguear.Stop();
						}
						setNewSpeed(actualDistance, initialDistance);
					}
					if (actualSpeed != 50 && status != runStatus.RUN) {
						robot.SetVelocidade(50);
					}
					break;
				case RUN:
					if (actualDistance < SAFEDISTANCE) {
						setNewSpeed(actualDistance, initialDistance);
					} else {
						status = runStatus.NORMAL;
						setNewSpeed(actualDistance, initialDistance);
						robot.Parar(true);
						if (memory) {
							vaguear.Start();
							memory = false;
						}
					}
					break;
				default:
					break;
				}
				initialDistance = actualDistance;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
