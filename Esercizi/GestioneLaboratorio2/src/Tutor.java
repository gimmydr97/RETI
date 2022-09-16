import java.util.ArrayList;
import java.util.concurrent.locks.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Tutor {
	int waitingProf = 0;/*numero dei professori in attesa*/
	int waitingTes  = 0;/*numero dei tesisti in attesa*/
	int workingProf = 0;/*numero dei professori al lavoro massimo 1*/
	
	ReentrantLock lockProf,/*lock per la variabile waitingProf*/
				  lockWaitingTes;/*lock per la variabile waitingTes*/
	
	Condition condwaitingTes,/*condition per la variabile waitingTes*/
			  condwaitingProf;/*condition per la variabile waitingProf e working prof*/
	
	private Laboratorio lab;/*Lista di studenti, tesisti e professori in attesa*/
	private List<Studente> studenti;
	private List<Professore> professori;
	private List<Tesista> tesisti;
	
	public Tutor(int nstudenti, int nprofessori, int ntesisti, Laboratorio lab ) {
		
		this.studenti = new ArrayList<Studente>(nstudenti);
		this.professori = new ArrayList<Professore>(nprofessori);
		this.tesisti = new ArrayList<Tesista>(ntesisti);
		this.lab = lab;
		
		for(int i = 0 ; i < nstudenti ;i++ ) 
			this.studenti.add(i,new Studente(this));
		
		for(int i = 0 ; i < nprofessori ;i++ ) 
			this.professori.add(i,new Professore(this));
		
		for(int i = 0 ; i < ntesisti ;i++ ) 
			this.tesisti.add(i,new Tesista (this));
		
		this.lockProf = new ReentrantLock(true);
		this.lockWaitingTes = new ReentrantLock(true);
		this.condwaitingProf = lockProf.newCondition();
		this.condwaitingTes = lockWaitingTes.newCondition();
	}
		public void manageProf() {
			lockProf.lock();
			waitingProf++;
			lockProf.unlock();
			System.out.println("Prof "+ Thread.currentThread().getName()+ "e' in attesa");
			lab.inUseLock.lock();
			
			while(lab.computersInUse !=0) {/*aspetta finche' il laboratorio non e' vuoto*/
				try {
					lab.inUseCond.await();
				}
				catch(InterruptedException e) {e.getMessage();}
			}
			
			lab.computersInUse = 20;
			lab.inUseLock.unlock();
			lockProf.lock();
			workingProf++;
			waitingProf--;
			lockProf.unlock();
			int i;
			
			for(i = 0; i < 20 ; i++) {
				lab.computersLock.get(i).lock();/*occupa il laboratorio*/
			}
			
			System.out.println("Prof " + Thread.currentThread().getName()+ " ha occupato il laboratorio");
			
			try {
				Thread.sleep(20000);
			}
			catch(InterruptedException e) {
				e.getMessage();
			}
			
			lab.inUseLock.lock();
			lab.computersInUse = 0;
			lab.inUseCond.signalAll();
			lab.inUseLock.unlock();
			
			for(i=0;i<20;i++) {
				lab.computersLock.get(i).unlock();
			}
			
			lockProf.lock();
			workingProf--;
			
			if(workingProf == 0 || waitingProf ==0)
					condwaitingProf.signalAll();
					lockProf.unlock();
					
					System.out.println("il prof ha finito");
			}
		
			public void manageTesista(int pc){
				lockWaitingTes.lock();
				waitingTes++;
				lockWaitingTes.unlock();
				System.out.println("il tesista e' in attesa " + Thread.currentThread().getName());
				lockProf.lock();
				while(waitingProf >0 || workingProf > 0) {
					try {
						condwaitingProf.await();
					}
					catch(InterruptedException e) {e.getMessage();}
				}
				lockProf.unlock();
				
				lockWaitingTes.lock();
				waitingTes--;
				condwaitingTes.signalAll();
				lockWaitingTes.unlock();
				
				lab.computersLock.get(pc).lock();
				
				lab.inUseLock.lock();
				lab.computersInUse++;
				lab.inUseLock.unlock();
				System.out.println("il tesista ha occupato il pc");
				
				try {
						Thread.sleep(2000);
				}
				catch(InterruptedException e ) { e .getMessage();}
				
				lab.inUseLock.lock();
				lab.computersInUse--;
				lab.inUseCond.signalAll();
				lab.inUseLock.unlock();
				
				lab.computersLock.get(pc).unlock();
				System.out.println("il tesista ha finito");
				
			}
			
			public void manageStudente() {
				lockProf.lock();
				System.out.println("studente in attesa "+ Thread.currentThread().getName());
				while(waitingProf > 0 || workingProf > 0) {
					try {
						condwaitingProf.await();
					}
					catch(InterruptedException e) {e.getMessage();}
					
				}
				
				lockProf.unlock();
				
				lockWaitingTes.lock();
				while(waitingTes >0 ) {
					try {
						condwaitingTes.await();
					}
					catch(InterruptedException e) {e.getMessage();}
					
				}
				lockWaitingTes.unlock();
				
				int i=0;
				while(lab.computersLock.get(i).tryLock()==false)
					i=i+1%20;
				
				lab.inUseLock.lock();
				lab.computersInUse++;
				lab.inUseLock.unlock();
				System.out.println("lo studente ha occupato il pc");
				try {
					Thread.sleep(2000);
				}
				catch(InterruptedException e) {e.getMessage();}
				
				lab.inUseLock.lock();
				lab.computersInUse--;
				lab.inUseCond.signalAll();
				lab.inUseLock.unlock();
				
				lab.computersLock.get(i).unlock();
				System.out.println("lo studente ha finito");
			}
			
			private void openLab() {
				ExecutorService e = Executors.newCachedThreadPool();
				int i;
				for(i=0;i<this.studenti.size();i++)
					e.execute(this.studenti.get(i));
				for(i=0;i<this.tesisti.size();i++)
					e.execute(this.tesisti.get(i));
				for(i=0;i<this.professori.size();i++)
					e.execute(this.professori.get(i));
				e.shutdown();
				try {
					e.awaitTermination(1, TimeUnit.HOURS);
				}catch(InterruptedException e1) {e1.getMessage();}
			}
		
			public static void main(String[]args) {
				int studenti,professori,tesisti;
				//if(args.length<3) {
					//System.out.println("inserire nella linea di comando il numero di professori,studenti e tesisti");
					//return;
					//}
				//else {
						professori=1;
						studenti=4;
						tesisti=3;
				//}
				Laboratorio labo = new Laboratorio();
				Tutor tutor =new  Tutor(studenti,professori,tesisti,labo);
				
				tutor.openLab();
				
				}
}
	

