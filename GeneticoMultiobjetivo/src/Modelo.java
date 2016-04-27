import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Modelo {
    
    List<Funcion> objetivos;
    List<Restriccion> restricciones;
    int numeroDeVariables;
    double rangoMenor;
    double rangoMayor;
    int precision;
    int poblacion;
    
    
    public Modelo(List<Funcion> objetivos, List<Restriccion> restricciones, int numeroDeVariables, double rangoMenor, double rangoMayor, int precision, int poblacion) {
        super();
        this.objetivos = objetivos;
        this.restricciones = restricciones;
        this.numeroDeVariables = numeroDeVariables;
        this.rangoMenor = rangoMenor;
        this.rangoMayor = rangoMayor;
        this.precision = precision;
        this.poblacion = poblacion;
    }
    
    List<Individuo> getPoblacion() {
        Stream<Individuo> pob = IntStream.range(0,poblacion).parallel().mapToObj(s -> new Individuo(rangoMenor, rangoMayor, precision, numeroDeVariables));
        return pob.collect(Collectors.toList());
    }

}
