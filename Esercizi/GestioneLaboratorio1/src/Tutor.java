import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.*;


public class Tutor {
	private List<Computer> pc;
	private boolean lab_occupato = false;
	private Condition codas ;
	private Condition codat ;
	private Condition codap ;
	private Lock pc_lock;
	private Lock lab_lock;
	private int pinattesa = 0;
	private int tinattesa = 0;
	
	public Tutor(List<Computer> computers) {
		
		this.pc = computers;
		pc_lock =  new ReentrantLock();
		lab_lock = new ReentrantLock();
		codas = pc_lock.newCondition();
		codat = pc_lock.newCondition();
		codap = lab_lock.newCondition();
		}
	
	
	public Computer getPc() {

		return pc.get(ThreadLocalRandom.current().nextInt(pc.size()));
	
		}
	
	public void getPc(int i,String id) {
		
		if(pc.get(i).isFree() && pinattesa == 0 && tinattesa == 0)  
			 pc.get(i).utilizza("il Tesista " +id, codat,pc_lock);
		else { 
				try {
					tinattesa++;
					codat.wait();
				}catch(Exception e ) {}
		}
		
	}
	
	public void getLab(){
		int i = 0;
		while(i < 20) {
			if( pc.get(i).isFree() && pinattesa == 0 ) { 
				pc.get(i).occupa(true);
				i++;
			}
			else {
					try {
							pinattesa++;
							codap.wait();
					}
			}
		
		}
		
	}
	
	public int managedPc() {
		return pc.size();
	}
	
}
