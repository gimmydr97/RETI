import java.util.List;

public class Professore extends Thread {
		private Tutor tutor;
		private int myId;
		
		public Professore(Tutor t, int id) {
			this.tutor=t;
			this.myId=id;
			
		}
		
		public String getRole() {
			return "Professore";
		}
		
		public void run() {
			int k = 1+(int)(Math.random()*10);
			while(k-- > 0) {
				List<Computer> lab = tutor.getLab();
				for(int i = 0 ; i < 20; i++ ) {
					Computer pc = lab.get(i);
					pc.utilizza("il professore "+ myId);
					
				}
				
			}
		}
}
