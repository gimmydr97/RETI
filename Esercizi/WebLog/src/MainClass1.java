import java.io.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
public class MainClass1 {
	//main della versione multi-thread
	public static void main(String args[])throws IOException {
		
		String webL ="web.log.txt";
		File 		file = new  File(webL);//file per leggere web.log.txt
		FileWriter     w = new FileWriter("web1.log.txt");//scrittore delnuovo file da creare
    	BufferedWriter b = new BufferedWriter (w);//buffer dello scrittore del nuovo file
    	ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newCachedThreadPool();//creazione del threadPool
    	final Lock lock = new ReentrantLock();//creo una lock per gestire la scrittura sul file
		try(FileReader fr = new FileReader(file)){//lettore del file da leggere
			BufferedReader br = new BufferedReader(fr);//buffer del lettore del file da leggere
			boolean flag = false;//flag per leggere tutte le righe del file
			Long time1=System.currentTimeMillis();
			while (!flag) {//esco dal while quando non ci sono piu' righe da leggere
				String tmp = br.readLine();//leggo una riga
				if(tmp == null) {flag= true;}
				if(tmp != null) {
					Task task = new Task(b,tmp,lock);//creo un task che tradurra' l'ind IP in nome dell'host e lo scrive su terminale e sul nuovo file
					executor.execute(task);//il threadpool prende il task 
				}
				
		
			}
			executor.shutdown();//il threadpoll non acceta piu' task
			
			while(!executor.isTerminated()) {}//aspetto che finiscono tutti i task
			
			b.flush();
			b.close();
			Long time2=System.currentTimeMillis();
			System.out.println(time2-time1);//stampa del tempo impiegato
		}
		catch(FileNotFoundException e) {e.printStackTrace();}
		catch(IOException e1) {e1.printStackTrace();}
		
		
	}
	
}

