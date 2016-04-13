import java.util.List;

public class Individuo {
    
    int[] genotipo;
    double rangoMenor;
    double rangoMayor;
    int precision;

    int convierteANumero() {
        int potencia = 1;
        int total = 0;
        for(int i=genotipo.length-1;i>=0;i--){
            total += potencia*genotipo[i];
            potencia*=2;
        }
        return total;
    }
    
    List<Double> decodifica() {
        double x=0;
        x=rangoMayor+((convierteANumero())*((rangoMayor-rangoMenor)/(Math.pow(2,precision)-1)));
        //TODO Falta regresar los numeros en una lista
        return null;
    }
}
