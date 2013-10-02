import java.io.*;


public class ClosestPair {
	String a;
	String b;
	double dist;
	
	public static void sort(double a[][],int low,int high)
	{
		if((high-low)<=1)
			return;
		
		double pivot = a[high-1][0];
		int split=low;
		for(int i=low; i< high-1; i++)
		{
			if(a[i][0] < pivot)
			{
				double temp[] = a[i];
				a[i] = a[split];
				a[split]=temp;
				split++;
			}
			
		}
		double temp[] =a[high-1];
		a[high-1]=a[split];
		a[split]=temp;
		sort(a,low,split);
		sort(a,split+1,high);
	}
	
	public static void sortWithY(double a[][],int low,int high)
	{
		if((high-low)<=1)
			return;
		
		double pivot = a[high-1][1];
		int split=low;
		for(int i=low; i< high-1; i++)
		{
			if(a[i][1] < pivot)
			{
				double temp[] = a[i];
				a[i] = a[split];
				a[split]=temp;
				split++;
			}
			
		}
		double temp[] =a[high-1];
		a[high-1]=a[split];
		a[split]=temp;
		sortWithY(a,low,split);
		sortWithY(a,split+1,high);
	}
	
	public static ClosestPair findShortest(double [][] x)
	{
		if (x.length <=2)
		{
			double ax = x[0][0];double ay= x[0][1];
			double bx=x[1][0];double by= x[1][1];
			ClosestPair p = new ClosestPair();
			p.a=""+ax+" "+ay;
			p.b=""+bx+" "+by;
			p.dist=Math.sqrt(Math.pow(ax-bx, 2)+Math.pow(ay-by, 2));
			return p;
		}
		else
		{
			ClosestPair p = new ClosestPair();
			double ax = x[0][0];double ay= x[0][1];
			double bx=x[1][0];double by= x[1][1];
			double cx=x[2][0];double cy= x[2][1];
			double dab=(double)Math.sqrt(Math.pow(ax-bx, 2)+Math.pow(ay-by, 2));
			double dbc=(double)Math.sqrt(Math.pow(bx-cx, 2)+Math.pow(by-cy, 2));
			double dac=(double)Math.sqrt(Math.pow(ax-cx, 2)+Math.pow(ay-cy, 2));
			if(dab <= dbc)
				if(dab<=dac)
				{
					p.a=""+ax+" "+ay;
					p.b=""+bx+" "+by;
					p.dist=dab;
					return p;
				}
					
				else
					{
					p.a=""+ax+" "+ay;
					p.b=""+cx+" "+cy;
					p.dist=dac;
					return p;
					}
			else if(dbc <= dac)
			{
				p.a=""+bx+" "+by;
				p.b=""+cx+" "+cy;
				p.dist=dbc;
				return p;
			}
					
			else
			{
				p.a=""+ax+" "+ay;
				p.b=""+cx+" "+cy;
				p.dist=dac;
				return p;
			}
			
		}
	}
	
	public static ClosestPair closestPair(double SortX [][])
	{
		
		if(SortX.length <= 3)
			{
				return findShortest(SortX);
			}
		else
			{
			double Pl[][], Pr[][];
			int m = SortX.length/2;
			
			if((SortX.length%2) == 0)
			{
				Pl=new double [m][2];
				Pr=new double [m][2];
			}
			else
			{
				Pl=new double [m][2];
				Pr=new double [m+1][2];
			}
			
			int i=0,j=0;
		    while (i< SortX.length) 
		    {
		        if(i<m)
		        	{
			        	Pl[i][0]=SortX[i][0];
			        	Pl[i][1]=SortX[i][1];	
		        	}
		        else
			        {
			        	Pr[j][0]=SortX[i][0];
			        	Pr[j][1]=SortX[i][1];
			        	j++;
		        	}
		        
		       i++; 
		    }
		    
		   ClosestPair dl = closestPair(Pl);
		   ClosestPair dr = closestPair(Pr);
		   ClosestPair d;
		   d =(dl.dist <= dr.dist )? dl:dr;
		   double SortY[][]=new double [SortX.length][2]; 
		   SortY=SortX;
		   sortWithY(SortY,0,SortY.length-1);
		   
		   double yBar[][]=new double [SortY.length][2];
		   int k=0;
		   for(i=0;i<SortY.length;i++)
		   {
			   if((Math.sqrt(Math.pow(SortY[i][0],2)) <= d.dist) && (Math.sqrt(Math.pow(SortY[i][1],2)) <= d.dist/2))
				   {
				   		yBar[i][0]=SortY[i][0];
				   		yBar[i][1]=SortY[i][1];
				   		k++;
				   }  	
		   }
		   
		  
		   ClosestPair dmin=d;
		   for( i=0;i<k-1;i++)
		   {
			   for( j=i+1;j<k;j++)
			   {
				     double ax = yBar[i][0];double ay= yBar[i][1];
					 double bx=yBar[j][0]; double by= yBar[j][1];
					 double dt=Math.sqrt(Math.pow(ax-bx, 2)+Math.pow(ay-by, 2)); 
					 if(dt<= dmin.dist)
					 { 
						ClosestPair p= new ClosestPair();
					    p.a=""+ax+" "+ay;
						p.b=""+bx+" "+by;
						p.dist=dt;
						dmin=p;
					 }
			   }
		   }
		   return dmin;
		}
	}
	
	public static void main(String ag[])
	{
		FileInputStream fstreamX=null;
		FileInputStream fstreamY=null;
		try {
			fstreamX = new FileInputStream("C:/closestPair/PX.txt");
			fstreamY = new FileInputStream("C:/closestPair/PY.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double X [][] = new double[1000000][2];
		DataInputStream inX = new DataInputStream(fstreamX);
		BufferedReader brX = new BufferedReader(new InputStreamReader(inX));
		String strLineX;
		//double Y [][] = new double[1000000][2];
		DataInputStream inY = new DataInputStream(fstreamY);
		BufferedReader brY = new BufferedReader(new InputStreamReader(inY));
		String strLineY;
		try {
			int i=0;
			strLineX = brX.readLine();
			strLineY = brY.readLine();
			strLineX= strLineX.replace("[","");
			strLineX= strLineX.replace("]","");
			strLineY= strLineY.replace("[","");
			strLineY= strLineY.replace("]","");
			//strLineY= strLineY.trim();
			String mx []= new String [1000000];
			mx=	strLineX.split(",");
			String my []= strLineY.split(",");
			
			while (i<mx.length&&i<my.length)  
			{
				mx[i]=mx[i].trim();
				my[i]=my[i].trim();
				X[i][0]=Double.parseDouble(mx[i]);
				X[i][1]=Double.parseDouble(my[i]);
				i++;	  
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sort(X,0,X.length-1);
		ClosestPair d=closestPair(X);
		System.out.println("("+d.a+") ("+d.b+") "+d.dist);
		
		
		
		}
	}

//Final Answer : point 1 :(6.7974804E7 3.6208084E7) 
//				 point 2 :( 6.7948969E7 3.6244457E7)
//				 Distance : 44614.37384969109