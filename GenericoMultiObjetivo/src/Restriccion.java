import java.util.List;

public abstract class Restriccion implements Funcion {
    public static enum Condicion {IGUALQUE, MENORQUE, MAYORQUE}
    
    Condicion tipoDeCondicion;
    
    public boolean cumpleRestriccion(List<Double> parametros) {
        Double evaluacion = evalua(parametros);
        if (tipoDeCondicion == Condicion.IGUALQUE) {
            return evaluacion == 0;
        }
        else if (tipoDeCondicion == Condicion.MENORQUE) {
            return evaluacion < 0;
        }
        else if (tipoDeCondicion == Condicion.MAYORQUE) {
            return evaluacion > 0;
        }
        return false;
    }
}
