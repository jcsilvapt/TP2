import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import Utils.myFile;

public class RobotPlayer extends myRobot implements Runnable {

	private boolean isToSave = false;

	private GUI2 gui;
	private myFile file;
	private String thisFile;

	public RobotPlayer(String type) {
		super(type);
		gui = new GUI2(this);
		gui.setVisible();
	}

	public void startSaving() {
		isToSave = !isToSave;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss");
		try {
			thisFile = "pathSaves\\PathSave" + sdf.format(cal.getTime());
			file = new myFile(thisFile, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		gui.RobotLogger("A gravar ...");
	}

	public void stopSaving() {
		isToSave = !isToSave;
		try {
			file.closeChannel();
		} catch (IOException e) {
			e.printStackTrace();
		}
		gui.RobotLogger("A parar gravação ...");
	}

	@Override
	public void Reta(int distancia) {
		if (isToSave) {
			try {
				file.write("Reta="+Integer.toString(distancia));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.Reta(distancia);
	}

	@Override
	public void CurvarDireita(int raio, int angulo) {
		if (isToSave) {
			try {
				file.write("CurvarDireita="+Integer.toString(raio)+";"+Integer.toString(angulo));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.CurvarDireita(raio, angulo);
	}

	@Override
	public void CurvarEsquerda(int raio, int angulo) {
		if (isToSave) {
			try {
				file.write("CurvarEsquerda="+Integer.toString(raio)+";"+Integer.toString(angulo));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.CurvarEsquerda(raio, angulo);
	}

	@Override
	public int SensorToque() {
		int value = super.SensorToque();
		if (isToSave) {
			try {
				file.write("SensorToque="+value);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	@Override
	public int SensorUS() {
		int value = super.SensorUS();
		if (isToSave) {
			try {
				file.write("SensorUS="+value);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	@Override
	public void SetVelocidade(int percentagem) {
		if (isToSave) {
			try {
				file.write("setVelocidade="+percentagem);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.SetVelocidade(percentagem);
	}

	@Override
	public void AjustarVMD(int offset) {
		if (isToSave) {
			try {
				file.write("AjsutarVMD="+offset);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.AjustarVMD(offset);
	}

	@Override
	public void AjustarVME(int offset) {
		if (isToSave) {
			try {
				file.write("AjustarVME="+offset);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.AjustarVME(offset);
	}

	@Override
	public void Parar(boolean value) {
		if (isToSave) {
			try {
				file.write("Parar="+value);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.Parar(value);
	}
	
	public void showTraj() {
		String[] te = null;
		try {
			file = new myFile(thisFile, true);
			te = file.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < te.length; i++) {
			System.out.println(te);
		}
	}

	@Override
	public void run() {
		for (;;) {
			System.out.println("Some");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
