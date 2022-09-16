import java.io.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
public class webLogMultiT {
	
	public static void main(String args[])throws IOException {
		
		String webL ="web.log.txt";
		File 		file = new  File(webL);//file per leggere web.log.txt
		FileWriter     w = new FileWriter("web1.log.txt");//scrittore delnuovo file da creare
    	BufferedWriter b = new BufferedWriter (w);//buffer dello scrittore del nuovo file
    	ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newCachedThreadPool();
    	final Lock lock = new ReentrantLock();
		try(FileReader fr = new FileReader(file)){
			BufferedReader br = new BufferedReader(fr);
			boolean flag = false;
			int i =0;
			while (!flag) {
				String tmp = br.readLine();
				if(tmp == null) {flag= true;}
				if(tmp != null) {
					Task task = new Task(b,tmp,i,lock);
					executor.execute(task);
					i++;
				}
				
		
			}
			executor.shutdown();
			
			while(!executor.isTerminated()) {}
			
			b.flush();
			b.close();
		}
		catch(FileNotFoundException e) {e.printStackTrace();}
		catch(IOException e1) {e1.printStackTrace();}
		
		
	}
	
}

