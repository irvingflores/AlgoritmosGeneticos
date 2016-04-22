import java.util.List;

public class Kurzawe1 implements Funcion {

	@Override
	public Double evalua(List<Double> parametros) {
		double x1 = parametros.get(0);
		double x2 = parametros.get(1);
		double x3 = parametros.get(2);
		double uno = -10*Math.exp(-0.2*Math.sqrt(x1*x1+x2*x2));
		double dos = -10*Math.exp(-0.2*Math.sqrt(x2*x2+x3*x3));
		return uno + dos;
	}

}
