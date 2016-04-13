public class ProgramaNReinas {

	public static void main(String[] args) {
		PReinas modelo1=new PReinas(4);
		int longitud=modelo1.encuentra_longitud();
		AGe<PReinas> ag01=new AGe<PReinas>(modelo1,longitud,longitud*2,0.8);
		ag01.aplica_ag();
	}

}
