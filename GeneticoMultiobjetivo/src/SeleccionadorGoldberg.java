import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class SeleccionadorGoldberg implements Seleccionador {

	@Override
	public List<Individuo> selecciona(List<Individuo> poblacion, List<Individuo> antigua, Map<Individuo, Double[]> informacion) {
		List<Individuo> totales = new ArrayList<Individuo>();
		totales.addAll(poblacion);
		totales.addAll(antigua);
		List<Individuo> actuales = totales;
		List<Individuo> finales = new ArrayList<Individuo>();
		List<Integer> puntos = new ArrayList<Integer>();
		List<Individuo> elegidos = new ArrayList<Individuo>();
		int nivel = 1;
		while (!actuales.isEmpty()) {
			Particion parti = particiona(actuales, informacion);
			for (int i = 0; i < parti.noDominados.size(); i++)
				puntos.add(nivel);
			finales.addAll(parti.noDominados);
			actuales = parti.dominados;
			nivel++;
		}
		Random rand = new Random();
		Map<Individuo, Double> mapaDistancias = distancias(finales, informacion);
		for (int i = 0; i< poblacion.size(); i++) {
			int aleatorio1 = rand.nextInt(totales.size());
			int aleatorio2 = rand.nextInt(totales.size());
			if (puntos.get(aleatorio1) < puntos.get(aleatorio2)) {
				elegidos.add(finales.get(aleatorio2));
			}
			else if (puntos.get(aleatorio2) < puntos.get(aleatorio1)) {
				elegidos.add(finales.get(aleatorio2));
			}
			else {
				if (mapaDistancias.get(finales.get(aleatorio1)) > mapaDistancias.get(finales.get(aleatorio2))) {
					elegidos.add(finales.get(aleatorio1));
				}
				else if (mapaDistancias.get(finales.get(aleatorio1)) < mapaDistancias.get(finales.get(aleatorio2)) ){
					elegidos.add(finales.get(aleatorio2));
				}
				else {
					elegidos.add(finales.get(aleatorio2));
				}
			}
		}
		return elegidos;
	}
	
	public Map<Individuo, Double> distancias(List<Individuo> total, Map<Individuo, Double[]> informacion) {
		int tamano = total.size();
		int objetivos = informacion.values().iterator().next().length;
		List<Distancia> dist = total.stream().map(s -> new Distancia(s, 0)).collect(Collectors.toList());
		for (int i = 0; i< objetivos; i++) {
			final int a = i;
			Comparator<Distancia> mejorEval = (s1, s2) -> 
			Double.compare(informacion.get(s2.ind)[a], informacion.get(s1.ind)[a]);
			dist.sort(mejorEval);
			double fMin = informacion.get(dist.get(tamano-1).ind)[i];
			double fMax = informacion.get(dist.get(0).ind)[i];
			double ancho = fMax - fMin;
			dist.get(0).distancia = Integer.MAX_VALUE;
			dist.get(tamano-1).distancia = Integer.MAX_VALUE;
			for (int j = 1; j < tamano -1; j++) {
				Distancia individuoEnCuestion = dist.get(j);
				Individuo indSuc = dist.get(j+1).ind;
				Individuo indPrec = dist.get(j-1).ind;
				individuoEnCuestion.distancia += (Math.abs((informacion.get(indSuc)[i]-informacion.get(indPrec)[i]))/ancho);
			}
		}
		Comparator<Distancia> mejorEval = (s1, s2) -> 
		Double.compare(s2.distancia,s1.distancia);
		dist.sort(mejorEval);
		Map<Individuo, Double> mapaDistancias = new HashMap<Individuo, Double>();
		dist.stream().forEach(s -> mapaDistancias.put(s.ind, s.distancia));
		return mapaDistancias;
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
	
	public static class Distancia {
		public Individuo ind;
		public double distancia;
		public Distancia(Individuo ind, double distancia) {
			this.ind = ind;
			this.distancia = distancia;
		}
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
