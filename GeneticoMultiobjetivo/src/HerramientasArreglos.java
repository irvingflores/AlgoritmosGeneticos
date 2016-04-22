
public final class HerramientasArreglos {
    
    private HerramientasArreglos() {
        //Clase utilitaria
    }
    
    static int[] cortaArreglo(int[] arreglo, int indxInicial, int indxFinal) {
        int[] copia = new int[indxFinal-indxInicial+1];
        int j = 0;
        for (int i= indxInicial; i<= indxFinal; i++) {
            copia[j] = arreglo[i];
            j++;
        }
        return copia;
    }
}
