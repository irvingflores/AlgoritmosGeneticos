import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
public class MinimizaBeales implements AGe.Modelo
{
	int [][] px;
	int [][] py;
	int[][] poblacion;
	int m_x,m_y;
	double a_x=0,b_x=0,a_y=0,b_y=0;
	@Override
	public double[] aptitud(int[][] poblacion) {
		double x,y,minimo=0;
		double[] aptitudes = new double[poblacion.length];
		this.poblacion=poblacion;
		valores();
		for(int i=0;i<poblacion.length;i++){
			x=decodifica(px,a_x,b_x,m_x,i);
			y=decodifica(py,a_y,b_y,m_y,i);
			aptitudes[i]=(double)(Math.pow(1.5-x+(x*y),2)+Math.pow(2.25-x+(x*Math.pow(y,2)),2)+Math.pow(2.625-x+(x*Math.pow(y,3)),2));
			if(aptitudes[i]<minimo)
				minimo=aptitudes[i];
		}
		for(int i=0;i<poblacion.length;i++)
			aptitudes[i]=1/(aptitudes[i]+Math.abs(minimo));
		return aptitudes;
	}	
	@Override
	public void finales(int [][] poblacion,int ind)
	{
		double x=0,y=0;
		this.poblacion=poblacion;
		valores();
		DecimalFormat df = new DecimalFormat("#.####");
		x=decodifica(px,a_x,b_x,m_x,ind);
		y=decodifica(py,a_y,b_y,m_y,ind);
		System.out.println("valores =x:"+df.format(x)+" y:"+df.format(y)+" f:"+df.format(Math.pow(1.5-x+(x*y),2)+Math.pow(2.25-x+(x*Math.pow(y,2)),2)+Math.pow(2.625-x+(x*Math.pow(y,3)),2)));
	}
	int num_codifica(double a,double b,int presicion)
	{
		int longitud_total=0;
		double longitud=0;
		longitud=b-a;
		presicion=(int)Math.pow(10,presicion);
		longitud=longitud*presicion;
		longitud_total=(int)(Math.log(longitud)/Math.log(2));
		return longitud_total+1;
	}
	int encuentra_longitud()
	{
		Scanner sc=new Scanner(System.in);
		int presicion=0;
		System.out.println("Escriba el rango minimo de x:");
		a_x=sc.nextDouble();
		System.out.println("Escriba el rango maximo de x:");
		b_x=sc.nextDouble();
		System.out.println("Escriba el rango minimo de y:");
		a_y=sc.nextDouble();
		System.out.println("Escriba el rango maximo de y:");
		b_y=sc.nextDouble();
		System.out.println("Escriba la presiciÃ³n:");
		presicion=sc.nextInt();
		m_x=num_codifica(a_x,b_x,presicion);
		m_y=num_codifica(a_y,b_y,presicion);
		return (m_x+m_y);
	}
	void valores()
	{
		int j=0,i=0,s=0;
		int presicion;
		double funcion=0;
		px=new int[poblacion.length][m_x];
		for(i=0;i<poblacion.length;i++)
			for(j=0;j<m_x;j++)
				px[i][j]=poblacion[i][j];
		py=new int[poblacion.length][m_y];
		for(i=0;i<poblacion.length;i++)
		{
			s=0;
			for(j=m_x;j<(m_x+m_y-1);j++)
			{
				py[i][s]=poblacion[i][j];
				s++;
			}
		}
	}
	double decodifica(int [][] poblacion,double a,double b,int m, int ind)
	{
		double x=0;
		x=a+((convierteANumero(poblacion[ind]))*((b-a)/(Math.pow(2,m)-1)));
		return x;
	}
	int convierteANumero(int[] individuo){
		int potencia = 1;
		int total = 0;
		for(int i=individuo.length-1;i>=0;i--){
			total += potencia*individuo[i];
			potencia*=2;
		}
		return total;
	}
}

