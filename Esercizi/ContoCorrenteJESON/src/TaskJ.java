
import java.util.*;
public class TaskJ implements Runnable {
			private statisticheJ s;
			private Iterator<String> Iterator;
			
			
			public TaskJ(Iterator<String> It, statisticheJ s){
				this.s = s;
				this.Iterator =It ;
			}
			public void run() {
					while(Iterator.hasNext()) {
						String i = Iterator.next();
						if (i.equals("Bonifico")) {s.setBon();}
						if (i.equals("Accredito")){s.setAcc();}
						if (i.equals("Bollettino")) {s.setBol();}
						if (i.equals("F24")) {s.setF();}
						if (i.equals("PagoBancomat")){s.setPB();}
					}
			}
	}

