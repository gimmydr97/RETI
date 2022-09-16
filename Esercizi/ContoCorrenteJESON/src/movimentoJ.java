import java.util.Random;

public class movimentoJ {
		
		private String data ;
		private String causale;
		private Random random = new Random();
		
		public movimentoJ() {
			
			data = RandDate();
			causale = RandCausal();
		}
		
		
		
		public String RandCausal() {
			int rand = random.nextInt(5);
			if(rand == 0) return "Bonifico";
			if(rand == 1) return "Accredito";
			if(rand == 2) return "Bollettino";
			if(rand == 3) return "F24";
			return "PagoBancomat";
		}
		
		public String RandDate() {
			
			int a = random.nextInt(2)+2016;
			int m = random.nextInt(11)+1;
			int g;
			if(m == 11 || m==4 || m==6 || m==9)
				g = random.nextInt(30)+1;
			else if(m==2)
				g = random.nextInt(27)+1;
			else
			    g = random.nextInt(30)+1;
			
		    String tmp = String.valueOf(g).toString()+"-" + String.valueOf(m).toString() +"-"+ String.valueOf(a).toString();
		    return tmp;
			 
			
		}
		
		public String getCausal() {
			return causale;
		}
		public String getDate() {
			return data;
		}
		
	}

