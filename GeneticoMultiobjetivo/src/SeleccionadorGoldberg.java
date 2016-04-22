import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SeleccionadorGoldberg implements Seleccionador {

	@Override
	public List<Individuo> selecciona(List<Individuo> poblacion, Map<Individuo, Double[]> informacion) {
		List<Individuo> actuales = poblacion;
		List<Individuo> finales = new ArrayList<Individuo>();
		List<Integer> puntos = new ArrayList<Integer>();
		List<Individuo> elegidos = new ArrayList<Individuo>();
		int nivel = 1;
		while (!actuales.isEmpty()) {
			Particion parti = particiona(actuales, informacion);
			System.out.println(parti.noDominados.size());
			for (int i = 0; i < parti.noDominados.size(); i++)
				puntos.add(nivel);
			finales.addAll(parti.noDominados);
			actuales = parti.dominados;
			nivel++;
		}
		double[] aptitudes = new double[poblacion.size()];
		double aptitudTotal = 0.0;
		for (int i = 0; i< finales.size(); i++) {
			aptitudes[i] = 1.0/puntos.get(i);
			aptitudTotal += aptitudes[i];
		}
		for (int i = 0; i< finales.size(); i++) {
			aptitudes[i] /= aptitudTotal;
		}
		
		double [] escoge=new double[poblacion.size()];
		
		for(int i = 0; i < finales.size(); i++) {
			if(i>0)
				escoge[i]=aptitudes[i]+escoge[i-1];
			else
				escoge[i]=aptitudes[i];
		}
		for(int i = 0; i < finales.size(); i++) {
			double aleatorio=Math.random();
			for(int k=0; k<finales.size(); k++) {
				if(aleatorio<=escoge[k]) {
					elegidos.add(finales.get(k));
					break;
				}
			}
		}
		return elegidos;
	}
	
	public Particion particiona(List<Individuo> poblacion, Map<Individuo, Double[]> informacion) {
		List<Individuo> dominados = new ArrayList<Individuo>();
		List<Individuo> noDominados = new ArrayList<Individuo>();
		int[] dominadosMarca = new int[poblacion.size()];
		for (int i = 0; i < poblacion.size(); i++) {
			for (int j = 0; j< poblacion.size(); j++) {
				if (domina(informacion.get(poblacion.get(i)), informacion.get(poblacion.get(j)))) {
					dominadosMarca[j] = 1;
				}
			}
		}
		for (int i = 0; i< poblacion.size(); i++) {
			if (dominadosMarca[i] == 0)
				noDominados.add(poblacion.get(i));
			else
				dominados.add(poblacion.get(i));
		}
		return new Particion(noDominados, dominados);
	}
	
	public boolean domina(Double[] eval1, Double[] eval2) {
		boolean menorIgual = true;
		int menorEstricto = 0;
		int tamano = eval1.length;
		for (int i = 0; i < tamano; i++) {
			menorIgual &= (eval1[i] <= eval2[i]);
			if (eval1[i] < eval2[i])
				menorEstricto++;
		}
		return menorIgual && menorEstricto > 0;
	}
	
	public static class Particion {
		public List<Individuo> noDominados;
		public List<Individuo> dominados;
		public Particion(List<Individuo> noDominados, List<Individuo> dominados) {
			super();
			this.noDominados = noDominados;
			this.dominados = dominados;
		}
	}

	@Override
	public List<Individuo> losMejores(List<Individuo> poblacion, Map<Individuo, Double[]> informacion) {
		Particion parti = particiona(poblacion, informacion);
		return parti.noDominados;
	}
}
