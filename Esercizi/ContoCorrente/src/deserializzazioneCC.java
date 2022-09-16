import java.io.*;
import java.util.concurrent.*;

public class deserializzazioneCC {
	
	 
	public static void  main(String[]args)throws Exception {
		 
		 statistiche stat =new statistiche();
		 int ntotMov = 0;
		 ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newCachedThreadPool();
			
			try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("dati.dat"));){
				
				while(input!=null) {
					
					contoCorrente cc = (contoCorrente) input.readObject();
					ntotMov=ntotMov + cc.getLim();
					Task task = new Task(cc.getMovList(),stat, cc.getLim());
					executor.execute(task);
				}
				executor.shutdown();
			}
			catch(IOException e1) {}
			catch(ClassNotFoundException e2){
				e2.printStackTrace();
				System.exit(1);
			}
			System.out.println("il numero totale di movimenti e': " + ntotMov);
			System.out.println();
			System.out.println("il numero complessivo di bonifici e': "   + stat.getBon()+ "\n" + 
							   "il numero complessivo di Accrediti e': "  + stat.getAcc()+ "\n" +
							   "il numero complessivo di Bollettini e':"  + stat.getBol()+ "\n" +
							   "il numero complessivo di F24 e':"         + stat.getF()  + "\n" +
							   "il numero complessivo di PagoBancomat e':"+ stat.getPB() + "\n" );
			
			
	}
}	
