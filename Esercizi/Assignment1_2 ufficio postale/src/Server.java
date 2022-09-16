
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

	public class Server {
		private ThreadPoolExecutor executor;
		
		public static ThreadPoolExecutor newFixedThreadPool(int nThreads,int k) {//metodo che restituisce un threadPool con nThreads threads e una coda di dimensione k
		return new ThreadPoolExecutor(nThreads, nThreads, 0L,TimeUnit.MILLISECONDS,new ArrayBlockingQueue <Runnable>(k)); 
		}
		
		public Server(int nThreads,int k){//costruttore della classe server
				executor = newFixedThreadPool(nThreads, k);
		}
		
		public void executeTask(Task task) {//metodo che fa eseguire un task da uno dei threads del threadPool
			System.out.printf("%s:Ufficio: la persona %d e' arrivata allo sportello\n",Thread.currentThread().getName(),task.getName());
			executor.execute(task);
		}
		
		public int getPoolSize() {//metodo che restituisce il numero di threads del threadPool che sono in attivita' 
			return executor.getPoolSize();
		}
		
		public long getCompletedTaskCount() {//metodo che restituisce il numero di task completati dal threaPool
			return executor.getCompletedTaskCount();
		}
		
	 	public boolean isTerminating() {//metodo che restituisce true se l'executor e' in stato di terminazione a seguito di una shutdown
	 		return executor.isTerminating();
	 	}
	 	
	 	public BlockingQueue<Runnable> getQueue(){//restituisce la coda gestita dal threadPool
	 		return executor.getQueue();
	 	}
	 	
		public void endServer() {//metodo che esecue una shutdown sull'executor 
			executor.shutdown();
		}
		
	}

