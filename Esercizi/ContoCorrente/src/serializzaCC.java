import java.util.Scanner;
import java.util.Random;
import java.io.*;

public class serializzaCC {
		public  static void main(String[] args) {
			
			Scanner in1;
			Scanner in2;
			Random random = new Random();
			
			System.out.println("inserire il numero di conti correnti presenti nella banca");
			in1 = new Scanner(System.in);
			int ncc = in1.nextInt();
			try(ObjectOutputStream output= new ObjectOutputStream(new FileOutputStream("dati.dat"));){
				
				
			
				for(int i=0 ; i < ncc; i++ ) {
				
					System.out.println("inserire il nome del " + i + " correntista" );
				
					in2 = new Scanner(System.in); 
				
					contoCorrente cc = new contoCorrente(in2.next());
				
					int nm = random.nextInt(10)+10;
				
					for(int c=0 ; c <= nm; c++) {
						movimento mov = new movimento();
						cc.addMovimento(mov);
					}
					output.writeObject(cc);
				}
			}	
			catch(IOException e1) {
					System.out.println("impossibile serializzare l'oggetto: ");
					e1.printStackTrace();
					System.exit(1);
			}
			
			System.out.println("la serializzazione e' stata completata");
			
			in1.close();
			
	}
	
}
