
import java.util.Random;

public class myRobot {

	private RobotLegoEV3 rEV;
	private RobotLegoNXT rNX;

	private boolean sim = false;
	private boolean isEV3;

	private final String ROBOTEV3 = "EV3";
	private final String ROBOTENXT = "NXT";

	private final int MAXDISTANCE = 255;

	public myRobot(String type) {
		if (type.equalsIgnoreCase(ROBOTEV3)) {
			rEV = new RobotLegoEV3();
			this.isEV3 = true;
		} else if (type.equalsIgnoreCase(ROBOTENXT)) {
			rNX = new RobotLegoNXT();
			this.isEV3 = false;
		} else {
			sim = true;
		}
	}

	public boolean Open(String name) {
		if (sim) {
			System.out.println("My Robot # Open Comms**(" + name + ")");
			return true;
		} else {
			if (this.isEV3) {
				return rEV.OpenEV3(name);
			} else {
				return rNX.OpenNXT(name);
			}
		}
	}

	public void CurvarDireita(int raio, int angulo) {
		if (this.sim)
			System.out.println("A virar (direita)" + angulo + " graus num raio de " + raio + ".");
		else if (this.isEV3)
			rEV.CurvarDireita(raio, angulo);
		else
			rNX.CurvarDireita(raio, angulo);
	}

	public void CurvarEsquerda(int raio, int angulo) {
		if (this.sim)
			System.out.println("A virar (esquerda)" + angulo + " graus num raio de " + raio + ".");
		else if (this.isEV3)
			rEV.CurvarEsquerda(raio, angulo);
		else
			rNX.CurvarEsquerda(raio, angulo);
	}

	public void Reta(int distancia) {
		if (this.sim) {
			if (distancia >= 0) {
				System.out.println("A andar " + distancia + "cm");
			} else {
				System.out.println("A recuar " + Math.abs(distancia) + "cm");
			}
		} else {
			if (this.isEV3)
				rEV.Reta(distancia);
			else
				rNX.Reta(distancia);
		}
	}

	public void Parar(boolean value) {
		if (this.sim) {
			if (value) {
				System.out.println("Parar!");
			} else {
				System.out.println("Parar assim que conseguir!");
			}
		} else {
			if (this.isEV3)
				rEV.Parar(value);
			else
				rNX.Parar(value);
		}
	}

	public void Close() {
		if (this.sim)
			System.out.println("Desligar");
		else if (this.isEV3)
			rEV.CloseEV3();
		else
			rNX.CloseNXT();
	}

	public void AjustarVME(int offset) {
		if (this.sim)
			System.out.println("VME ajustado: " + offset);
		else if (this.isEV3)
			System.out.println("RobotEV3 não têm este parametro");
		else
			rNX.AjustarVME(offset);
	}

	public void AjustarVMD(int offset) {
		if (this.sim)
			System.out.println("VMD ajustado: " + offset);
		else if (this.isEV3)
			System.out.println("RobotEV3 não têm este parametro");
		else
			rNX.AjustarVMD(offset);
	}

	public void SetVelocidade(int percentagem) {
		if (this.sim)
			System.out.println("Speed ajustada: " + percentagem);
		else if (this.isEV3)
			rEV.SetVelocidade(percentagem);
		else
			rNX.SetVelocidade(percentagem);
	}

	public int SensorToque() {
		if (this.sim) {
			int bater = (int) Math.round(Math.random());
			System.out.println("Sensor: " + bater);
			if (bater == 1) {
				return 1;
			}
			return 0;
		} else {
			if (this.isEV3)
				return rEV.SensorToque(1);
			else
				return rNX.SensorToque(1);
		}
	}

	public int SensorUS() {
		if (this.sim) {
			Random rnd = new Random();
			int sensor = rnd.nextInt(MAXDISTANCE);
			return sensor;
		} else {
			if (this.isEV3)
				return rEV.SensorUS(2);
			else
				return rNX.SensorUS(2);
		}
	}
}
