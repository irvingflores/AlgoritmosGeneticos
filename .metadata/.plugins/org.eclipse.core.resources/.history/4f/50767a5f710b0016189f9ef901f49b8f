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
    
    public static void main(String[] args) {
        Modelo modelin = new Modelo(null,null,3,-5,5,4,1000);
        List<Individuo> pobla = modelin.getPoblacion();
        pobla.stream().forEach(individuo -> {
            List<Double> variables = individuo.decodifica();
            System.out.println(variables.get(0)+ " " + variables.get(1) + " " + variables.get(2));
        });
        System.out.println("Ya acabe");
    }
}
