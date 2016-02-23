
public class ProgramaAckleys {

	public static void main(String[] args) {
		MinimoAckleys modelo1=new MinimoAckleys();
		int longitud=modelo1.encuentra_longitud();
		/*AG<MinimoAckleys> ag01=new AG<MinimoAckleys>(modelo1,longitud,longitud*2,0.8);
		ag01.aplica_ag();*/
		AGe<MinimoAckleys> ag01=new AGe<MinimoAckleys>(modelo1,longitud,longitud*2,0.6);
		ag01.aplica_ag();
	}

}
