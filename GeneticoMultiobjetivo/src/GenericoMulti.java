import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenericoMulti {
    
    double probabilidadCruza;
    double probabilidadMuta;
    Modelo modelo;
    Evaluador evaluador;
    Seleccionador seleccionador;
    int generacion;
    
    
    public GenericoMulti(Modelo modelo, Evaluador evaluador, Seleccionador seleccionador, double probabilidadCruza, 
    		double probabilidadMuta) {
		super();
		this.probabilidadCruza = probabilidadCruza;
		this.probabilidadMuta = probabilidadMuta;
		this.modelo = modelo;
		this.evaluador = evaluador;
		this.seleccionador = seleccionador;
		generacion = 0;
	}

	public void cruza(Individuo uno, Individuo dos) {
        Random rand = new Random();
        if (rand.nextDouble() < probabilidadCruza) {
            int random = rand.nextInt(uno.tamanoGenotipo*uno.numeroVariables-1);
            int aux;
            for (int i = random ; i < uno.tamanoGenotipo*uno.numeroVariables; i++) {
                aux = uno.genotipo[i];
                uno.genotipo[i] = dos.genotipo[i];
                dos.genotipo[i] = aux;
            }
        }
    }
    
    public void muta(Individuo indi) {
        int tamano = indi.tamanoGenotipo*indi.numeroVariables;
        Random rand = new Random();
        for (int i = 0; i < tamano; i++) {
            if (rand.nextDouble() < probabilidadMuta) {
                if (indi.genotipo[i] == 0 ) indi.genotipo[i] = 1;
                else indi.genotipo[i] = 0;
            }
        }
    }
    
    public static List<Individuo> copiaPoblacion(List<Individuo> pob) {
    	List<Individuo> nueva = pob.parallelStream().map(s -> (Individuo)s.clone()).collect(Collectors.toList());
    	return nueva;
    }
    
    public void cruzaPoblacion(List<Individuo> pob) {
        int veces = pob.size()/2;
        IntStream.range(0, veces).parallel().forEach(s -> {
            cruza(pob.get(s*2), pob.get(s*2+1));
        });
    }
    
    public void mutaPoblacion(List<Individuo> pob) {
        pob.parallelStream().forEach(s -> muta(s));
    }
    
    public Map<Individuo, Double[]> evaluaPoblacion(List<Individuo> pob) {
        Map<Individuo, Double[]> mapa = pob.stream().collect(
        		Collectors.toMap(s->s, s->evaluador.evaluaIndividuo(modelo,s), (s1,s2)-> {
        			return s1;
        			}));
        return mapa;
    }
    
    public List<Individuo> aplica() {
    	List<Individuo> poblacion = modelo.getPoblacion();
    	List<Individuo> copia;
    	for (generacion = 0; generacion < 1000; generacion++) {
    		copia = copiaPoblacion(poblacion);
    		cruzaPoblacion(copia);
    		mutaPoblacion(copia);
    		Map<Individuo, Double[]> evaluacionCopia = evaluaPoblacion(copia);
    		Map<Individuo, Double[]> evaluacionOriginal = evaluaPoblacion(poblacion);
    		evaluacionCopia.putAll(evaluacionOriginal);
    		poblacion = seleccionador.selecciona(copia, poblacion, evaluacionCopia);
    		Set<Individuo> repetidos = new HashSet<Individuo>();
    		poblacion.stream().forEach(s -> repetidos.add(s));
    		System.out.println(generacion + "-->" + repetidos.size());
    	}		
    	poblacion.stream().forEach(s -> {
    		for (int i = 0; i < s.numeroVariables*s.tamanoGenotipo; i++) {
    			System.out.print(s.genotipo[i]);
    		}
    		System.out.println("");
    	});
		return poblacion;
    }
    
    public void escribeResultados(List<Individuo> poblacionFinal) {
    	Map<Individuo, Double[]> evaluacion = evaluaPoblacion(poblacionFinal);
    	List<Individuo> mejores = seleccionador.losMejores(poblacionFinal, evaluacion);
    	Path path = Paths.get("sources/resultados.txt");
    	try (BufferedWriter writer = Files.newBufferedWriter(path)) {
    		poblacionFinal.stream().forEach(s -> {
    	    	Double[] func = evaluador.evaluaIndividuo(modelo, s);
    	    	StringBuffer linea = new StringBuffer();
    	    	for (int i = 0; i < func.length; i++) {
    	    		linea.append(func[i]);
    	    		linea.append(" ");
    	    	}
    	    	String lineaString = linea.toString().trim();
    	    	try {
					writer.write(lineaString);
					writer.newLine();
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    });
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	System.out.println("Listo");
    }

}
