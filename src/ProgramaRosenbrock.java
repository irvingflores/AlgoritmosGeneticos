public class ProgramaRosenbrock {

	public static void main(String[] args) {
		
		MinimizaRosenbrock modelo1=new MinimizaRosenbrock();
		int longitud=modelo1.encuentra_longitud();		
		System.out.println("l:"+longitud);
		AGe<MinimizaRosenbrock> ag01=new AGe<MinimizaRosenbrock>(modelo1,longitud,longitud*2,0.7);
		ag01.aplica_ag();
	}

}
