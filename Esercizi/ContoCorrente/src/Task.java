import java.util.ArrayList;

public class Task implements Runnable {
		private statistiche s;
		private ArrayList<movimento> Mov;
		private int lim;
		
		public Task(ArrayList<movimento> Mov, statistiche s,int lim){
			this.s = s;
			this.Mov =Mov ;
			this.lim = lim;
		}
		public void run() {
				for(int i = 0; i < lim ; i++) {
					if (Mov.get(i).getCausal().equals("Bonifico")) {s.setBon();}
					if (Mov.get(i).getCausal().equals("Accredito")){s.setAcc();}
					if (Mov.get(i).getCausal().equals("Bollettino")) {s.setBol();}
					if (Mov.get(i).getCausal().equals("F24")) {s.setF();}
					if (Mov.get(i).getCausal().equals("PagoBancomat")){s.setPB();}
				}
		}
}
