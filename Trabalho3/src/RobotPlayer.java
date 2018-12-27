import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Base64.Decoder;

import Utils.myFile;

public class RobotPlayer extends myRobot implements Runnable {

	private boolean isToSave = false;

	private GUI2 gui;
	private myFile file;
	private String thisFile;
	private String rep;
	private final String Recta 			= "r";
	private final String CurvarDireita	= "cd";
	private final String CurvarEsquerda	= "ce";
	private final String SensorToque	= "st";
	private final String SensorUS		= "sus";
	private final String SetVelocidade	= "sv";
	private final String AjustarVMD 	= "avmd";
	private final String AjustarVME		= "avme";
	private final String Parar			= "s";
	

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
			rep = sdf.format(cal.getTime());
			thisFile = "pathSaves\\PathSave" + rep ;
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
				file.write(Recta+"="+Integer.toString(distancia));
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
				file.write(CurvarDireita+"="+Integer.toString(raio)+";"+Integer.toString(angulo));
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
				file.write(CurvarEsquerda+"="+Integer.toString(raio)+";"+Integer.toString(angulo));
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
				file.write(SensorToque+"="+value);
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
				file.write(SensorUS+"="+value);
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
				file.write(SetVelocidade+"="+percentagem);
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
				file.write(AjustarVMD+"="+offset);
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
				file.write(AjustarVME+"="+offset);
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
				file.write(Parar+"="+value);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.Parar(value);
	}
	
	public void showTraj(boolean reproducao) {
		String[] ti = null;
		try {
			//file = new myFile("pathSaves\\PathSave"+rep, true);
			file = new myFile("pathSaves\\PathSave22_39_24", true);
			ti =  file.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!reproducao) {
			for(int i = 0; i < ti.length; i++) {
				gui.RobotLogger(ti[i]);
				decode(ti[i], reproducao);
			}
		}else {
			CurvarDireita(0, 150);
			for(int i = ti.length-1; i >= 0; i--) {
				gui.RobotLogger(ti[i]);
				decode(ti[i], reproducao);
				
			}
			Parar(false);
		}
	}
	
	private void decode(String v, boolean reproducao) {
		String action = v.substring(0, v.lastIndexOf("="));
		String aValue = v.substring(v.lastIndexOf("=") + 1);
		String v1 = "";
		String v2 = "";
		int delay = 0;
		
		switch (action) {
		case Recta:
			Reta(Integer.parseInt(aValue));
			delay = Utils.Utils.delay(Integer.parseInt(aValue), false, 0);
			break;
		case CurvarDireita:
			v1 = aValue.substring(0, aValue.lastIndexOf(";"));
			v2 = aValue.substring(aValue.lastIndexOf(";")+1);
			if(reproducao)
				CurvarEsquerda(Integer.parseInt(v1), Integer.parseInt(v2));
			else
				CurvarDireita(Integer.parseInt(v1), Integer.parseInt(v2));
			delay = Utils.Utils.delay(Integer.parseInt(v1), true, Integer.parseInt(v2));
			break;
		case CurvarEsquerda:
			v1 = aValue.substring(0, aValue.lastIndexOf(";"));
			v2 = aValue.substring(aValue.lastIndexOf(";")+1);
			if(reproducao)
				CurvarDireita(Integer.parseInt(v1), Integer.parseInt(v2));
			else
				CurvarEsquerda(Integer.parseInt(v1), Integer.parseInt(v2));
			delay = Utils.Utils.delay(Integer.parseInt(v1), true, Integer.parseInt(v2));
			break;
		case Parar:
			if(aValue.equalsIgnoreCase("true"))
				Parar(true);
			else
				Parar(false);
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
	

	@Override
	public void run() {
		for (;;) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		RobotPlayer r = new RobotPlayer("Simular");
		
	}
}
