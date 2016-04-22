import java.util.List;
import java.util.Map;

public interface Seleccionador {
    List<Individuo> selecciona(List<Individuo> poblacion, Map<Individuo, Double[]> informacion);
    List<Individuo> losMejores(List<Individuo> poblacion, Map<Individuo, Double[]> informacion);
}
