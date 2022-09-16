import java.util.Random;

public  class Tesista extends Utente implements Runnable {
	int neededComputer;
	
	public Tesista(Tutor t) {
		this.tutor = t;
		Random generator = new Random();
		neededComputer = Math.abs(generator.nextInt())%7;
		this.numberOfAccesses = 1+Math.abs(generator.nextInt())%7;
	}
	
	@Override
	public void sendAccessRequest() {
		System.out.println("Tesista " + Thread.currentThread().getName() + "vorrebbe accedere al laboratorio " + numberOfAccesses + "volte");
		for(int i =  0; i<this.numberOfAccesses; i++)
				this.tutor.manageTesista(this.neededComputer);
	
	}
}
