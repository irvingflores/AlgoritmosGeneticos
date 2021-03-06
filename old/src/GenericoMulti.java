import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenericoMulti {
    
    double probabilidadCruza;
    double probabilidadMuta;
    Modelo modelo;
    Evaluador evaluador;
    Seleccionador seleccionador;
    
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
    
    public void cruzaPoblacion(List<Individuo> pob) {
        int veces = pob.size()/2;
        IntStream.range(0, veces).parallel().forEach(s -> {
            cruza(pob.get(s*2), pob.get(s*2+1));
        });
    }
    
    public void mutaPoblacion(List<Individuo> pob) {
        pob.parallelStream().forEach(s -> muta(s));
    }
    
    public void evaluaPoblacion(List<Individuo> pob) {
        Map<Individuo, Double[]> mapa = pob.stream().collect(Collectors.toMap(s->s, s->evaluador(modelo,s)));
    }
}
