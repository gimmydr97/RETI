
public class Tesista extends Studente{
		
	public Tesista(Tutor t, int id) {
			super(t,id);
	}
	
	public String getRole() {
		return "Tesista";
	}
	
	public void run() {
		int k= 1 + (int)(Math.random()*10);
		while(k-- > 0) {
			Computer pc = tutor.getPc(this.myId % tutor.managedPc());
			pc.occupato(true);
			pc.utilizza("il Tesista " + myId);
			
		}
	}

}
