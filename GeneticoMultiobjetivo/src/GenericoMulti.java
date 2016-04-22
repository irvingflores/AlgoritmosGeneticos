import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
        	System.out.println("cruza");
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
    	List<Individuo> nueva;
    	for (generacion = 0; generacion < 30; generacion++) {
    		cruzaPoblacion(poblacion);
    		mutaPoblacion(poblacion);
    		Map<Individuo, Double[]> evaluacion = evaluaPoblacion(poblacion);
    		nueva = seleccionador.selecciona(poblacion, evaluacion);
    		poblacion = nueva;
    	}
		return poblacion;
    }
    
    public void escribeResultados(List<Individuo> poblacionFinal) {
    	Map<Individuo, Double[]> evaluacion = evaluaPoblacion(poblacionFinal);
    	//List<Individuo> mejores = seleccionador.losMejores(poblacionFinal, evaluacion);
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
    }
}
