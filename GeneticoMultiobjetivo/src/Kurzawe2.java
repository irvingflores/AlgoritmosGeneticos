import java.util.List;

public class Kurzawe2 implements Funcion {
	@Override
	public Double evalua(List<Double> parametros) {
		double x1 = parametros.get(0);
		double x2 = parametros.get(1);
		double x3 = parametros.get(2);
		double uno = Math.pow(Math.abs(x1), 0.8) + 5*Math.sin(x1*x1*x1);
		double dos = Math.pow(Math.abs(x2), 0.8) + 5*Math.sin(x2*x2*x2);
		double tres = Math.pow(Math.abs(x3), 0.8) + 5*Math.sin(x3*x3*x3);
		return uno + dos + tres;
	}
}
