import java.util.Random;

public class Professore extends Utente implements Runnable {
	
	public Professore(Tutor t) {
		this.tutor = t;
		Random generetor = new Random();
		this.numberOfAccesses = 1+ Math.abs(generetor.nextInt())%5;
		
	}
	
	@Override
	public void sendAccessRequest() {
		
		System.out.println("Professore" + Thread.currentThread().getName()+ "vorrebbe accedere al laboratorio" + numberOfAccesses + "vole");
		
		for(int i =0 ; i < this.numberOfAccesses ; i++) 
			this.tutor.manageProf();
		
	}

}
