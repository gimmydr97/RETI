import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
	
	public static void main(String[] args) throws Exception{ 
		int k;//lunghezza della coda del threadPool o anche la grandezza della sala interna dell'ufiicio
		int niniz;//numero di persone che entrano nell'ufficio
		
		System.out.println("inserire il numero di persone che sono presenti nell'ufficio");//ricevo in input niniz
		Scanner in2 = new Scanner(System.in);
		niniz= in2.nextInt();
		
		BlockingQueue<Task> queue = new LinkedBlockingQueue<Task>(niniz);//creo una coda linkata di grandezza niniz
		
		for(int i=0;i<niniz;i++) { queue.add(new Task(i)); }//riempio la coda con niniz task differenti 
		
		System.out.println("inserire il numero di persone che possono aspettare contemporaneamente nella sala interna");//ricevo in input k
	    Scanner in1 = new Scanner(System.in);
		k= in1.nextInt();
		
		Server server= new Server(4,k);//creo un threaPool di 4 threads e con coda interna lunga k
		
		while(server.isTerminating() == false ) {//eseguo i task che sono in queue; il ciclo si ferma solo quando e' stata chiamata al suo interno una shutdown
			
			if(queue.size()!=0 && server.getPoolSize()+ server.getQueue().size() < k+ 4)//mando i task che sono in queue nel threadPool solo quando 
																						//queue non e' vuota e in contemporanea 
																						//i task all'interno del nostro threadpool sono minori di k+4 
																						//che e' la dimensione massima della coda interna + il numero di thread del threadPool
				server.executeTask(queue.take());
			
			if(server.getCompletedTaskCount() == niniz ) {//se tutti i task sono stati completati mando una shutdown
				System.out.printf("task nel pool: %d , task completati: %d \n",server.getPoolSize(),server.getCompletedTaskCount());
				server.endServer();
			}
			
		}
		
		in1.close();//chiusura dello scanner in1
		in2.close();//chiusura dello scanner in2
		
	}
}
