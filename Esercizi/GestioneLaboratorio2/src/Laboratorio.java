import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.*;

public class Laboratorio {
	
		public List<ReentrantLock> computersLock;/*lista dei computer disponibili nel lab*/
		public ReentrantLock inUseLock;/*lock per la variabile "computersInUse"*/
		public Condition inUseCond;/*condizione per la variabile "computersInUse"*/
		public int computersInUse;/*numero di computer in uso*/
		
		public Laboratorio() {
			computersInUse = 0;
			computersLock = new ArrayList<ReentrantLock>();
			inUseLock = new ReentrantLock();
			inUseCond = inUseLock.newCondition();
			
			for(int i = 0; i<20; i++) 
				computersLock.add(i,new ReentrantLock(true));
		}
}
