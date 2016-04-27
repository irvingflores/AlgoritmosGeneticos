import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Individuo implements Cloneable {
    
    int[] genotipo;
    double rangoMenor;
    double rangoMayor;
    int precision;
    int numeroVariables;
    int tamanoGenotipo;

    public Individuo(double rangoMenor, double rangoMayor, int precision, int numeroVariables) {
        this.rangoMenor = rangoMenor;
        this.rangoMayor = rangoMayor;
        this.precision = precision;
        this.numeroVariables = numeroVariables;
        tamanoGenotipo = getTamanoGenotipo();
        genotipo = new int[numeroVariables*tamanoGenotipo];
        Random random = new Random();
        for (int i = 0; i < numeroVariables*tamanoGenotipo; i++) {
            genotipo[i] = moneda(random);
        }
    }
    
    private int moneda(Random rand) {
        
        boolean booleano = rand.nextBoolean();
        if (booleano) return 1;
        else return 0;
    }
    
    private int convierteANumero(int[] arreglo) {
        int potencia = 1;
        int total = 0;
        for(int i=arreglo.length-1;i>=0;i--){
            total += potencia*arreglo[i];
            potencia*=2;
        }
        return total;
    }
    
    List<Double> decodifica() {
        double x=0;
        List<Double> variables = new ArrayList<Double>(numeroVariables);
        for (int i = 0; i<numeroVariables; i++) {
            int[] variable = HerramientasArreglos.cortaArreglo(genotipo, i*tamanoGenotipo, (i+1)*tamanoGenotipo-1);
            x=rangoMenor+(convierteANumero(variable)*((rangoMayor-rangoMenor)/(Math.pow(2,tamanoGenotipo)-1)));
            variables.add(x);
        }
        return variables;
    }
    
	public void imprimeIndividuo() {
		this.decodifica().stream().forEach(s -> System.out.print(s+ " "));
	}
	
    public Object clone(){  
        try{  
            return super.clone();  
        }catch(Exception e){ 
            return null; 
        }
    }
    
    private int getTamanoGenotipo()
    {
        int longitud_total=0;
        double longitud=0;
        longitud=rangoMayor-rangoMenor;
        double nuevaPrecision=(int)Math.pow(10,precision);
        longitud=longitud*nuevaPrecision;
        longitud_total=(int)(Math.log(longitud)/Math.log(2));
        return (longitud_total+1);
    }

	@Override
	public String toString() {
		return "Individuo [genotipo=" + Arrays.toString(genotipo) + ", rangoMenor=" + rangoMenor + ", rangoMayor="
				+ rangoMayor + ", precision=" + precision + ", numeroVariables=" + numeroVariables + ", tamanoGenotipo="
				+ tamanoGenotipo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(genotipo);
		result = prime * result + numeroVariables;
		result = prime * result + precision;
		long temp;
		temp = Double.doubleToLongBits(rangoMayor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(rangoMenor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + tamanoGenotipo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Individuo other = (Individuo) obj;
		if (!Arrays.equals(genotipo, other.genotipo))
			return false;
		if (numeroVariables != other.numeroVariables)
			return false;
		if (precision != other.precision)
			return false;
		if (Double.doubleToLongBits(rangoMayor) != Double.doubleToLongBits(other.rangoMayor))
			return false;
		if (Double.doubleToLongBits(rangoMenor) != Double.doubleToLongBits(other.rangoMenor))
			return false;
		if (tamanoGenotipo != other.tamanoGenotipo)
			return false;
		return true;
	}
}
