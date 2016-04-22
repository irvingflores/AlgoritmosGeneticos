import java.util.List;
import java.util.Map;

public interface Seleccionador {
    List<Individuo> selecciona(Map<Individuo, Double[]> informacion);
}
