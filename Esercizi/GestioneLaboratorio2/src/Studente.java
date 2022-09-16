import java.util.Random;

public class Studente extends Utente implements Runnable {
	
	public Studente(Tutor t) {
		this.tutor = t;
		Random generator = new Random();
		this.numberOfAccesses = 1+ Math.abs(generator.nextInt())%10;
	}
	
	@Override
	public void sendAccessRequest() {
		System.out.println("Studente " + Thread.currentThread().getName() + "vorrebbe accedere al laboratorio " + numberOfAccesses + "volte");
		for(int i =  0; i<this.numberOfAccesses; i++)
				this.tutor.manageStudente();
	}
	
}
