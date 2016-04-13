public class ProgramaBeales {

	public static void main(String[] args) {
		MinimizaBeales modelo1=new MinimizaBeales();
		int longitud=modelo1.encuentra_longitud();
		AGe<MinimizaBeales> ag01=new AGe<MinimizaBeales>(modelo1,longitud,longitud*2,0.8);
		ag01.aplica_ag();
	}

}
