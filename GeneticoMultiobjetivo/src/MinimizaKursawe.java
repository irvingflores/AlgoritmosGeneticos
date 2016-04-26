import java.util.ArrayList;
import java.util.List;

public class MinimizaKursawe {
	public static void main(String[] args) {
		List<Funcion> funcionesKursawe = new ArrayList<Funcion>();
		funcionesKursawe.add(new Kurzawe1());
		funcionesKursawe.add(new Kurzawe2());
		Modelo modeloKurzawe = new Modelo(funcionesKursawe, null, 3, -5.0, 5.0, 5, 500);
		GenericoMulti genetico = new GenericoMulti(modeloKurzawe, new EvaluadorSencillo(), new SeleccionadorGoldberg(), .8, 0.05);
		List<Individuo> mejor = genetico.aplica();
		genetico.escribeResultados(mejor);
	}
}
