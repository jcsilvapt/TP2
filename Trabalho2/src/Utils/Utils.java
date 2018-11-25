package Utils;

public class Utils {
	public static int delay(int valor, boolean raio, int angulo) {
		// Robot demora 5 segundos a precorrer 100 cm
		if(valor < 0) 
			valor = valor *-1;
		int convCm = 100;
		int convMs = 5000;
		int delay = 0;
		int aux = valor;

		if (raio) {
			aux = (int) (2. * Math.PI * valor);
			aux = aux * angulo / 360;
		}

		delay = aux * convMs / convCm;
		return delay;
	}
	
	public static int convertionSpeed(int distance) {
		
		float x0, x1, x2, y0, y2;
		float y1;
		
		/**
		 *  x0 = 50cm | y0 = 50%
		 *  x1 = dist | y1 = x
		 *  x2 = 0cm  | y2 = 75%
		 */
		
 		x0 = 50;
		x1 = distance;
		x2 = 0;
		y0 = 50;
		y2 = 75;
		
		y1 = ((x1-x0)/(x2-x0))*(y2-y0)+y0;
		
		return (int) y1;
	}
}