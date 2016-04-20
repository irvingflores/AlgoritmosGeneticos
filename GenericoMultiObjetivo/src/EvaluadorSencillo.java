
public class EvaluadorSencillo implements Evaluador {

    @Override
    public Double[] evaluaIndividuo(Modelo mod, Individuo indi) {
        Double[] vectorDeFunciones = new Double[mod.objetivos.size()];
        for (int i = 0; i < mod.objetivos.size(); i++) {
            vectorDeFunciones[i] = mod.objetivos.get(i).evalua(indi.decodifica());
        }
        return vectorDeFunciones;
    }
}
