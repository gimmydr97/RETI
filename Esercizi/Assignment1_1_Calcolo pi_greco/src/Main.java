
	import java.util.Scanner;

	public class Main {
		 public static void main(String[] args) {
			 double accuracy;
			 long tmax;
			 
			 System.out.println("inserire l'accuratezza");
			 Scanner in1 = new Scanner(System.in);//input 
			 accuracy = in1.nextDouble();	      //di accuracy
			
			 System.out.println("inserire  il tempo massimo che il thread che si occupera' del calcolo del pigreco puo' rimanere attivo:");
			 Scanner in2 = new Scanner(System.in);//input
			 tmax = in2.nextLong();		      //di tmax
		
			 
			 Calculator calculator = new Calculator(accuracy);//creazione del task(calcucator) 
			 Thread thread=new Thread(calculator);		  //creazione del thread che andra' ad eseguire il task(calculator)
			 thread.start();				  //faccio partire il thread che ho creato
			 
			 try {Thread.sleep(tmax);}	//dato che dopo tempo tmax il thread che ho creato deve essere interrotto per    								  contare il tempo fino a tmax metto in stato di sleep un altro thread per 							 	 tmax tempo
			 catch(InterruptedException x) {   };//cerco di intercettare un interruzione nel thread che ho messo in sleep che 									pero' non dovrebbe subirne in teoria 
			 thread.interrupt();//metodo che richiede l'interruzione del thread creato da me

			 in1.close();//chiusura dello scanner in1
			 in2.close();//chiusura dello scanner in2
		}
	}

