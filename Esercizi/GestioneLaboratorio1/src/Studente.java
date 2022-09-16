
public class Studente extends Thread{
	protected Tutor tutor;
	protected int myId;
	
	public Studente(Tutor t, int id ) {
		this.tutor = t;
		this.myId = id;
	}
	
	public String getRole() {
		return "Studente";
	}
	
	public void run() {
		int k=1 + (int)(Math.random()*10);
		while(k-- > 0) {
			Computer pc = tutor.getPc();
			pc.occupato(true);
			pc.utilizza("lo studente " + myId);
		}
	} 

}
