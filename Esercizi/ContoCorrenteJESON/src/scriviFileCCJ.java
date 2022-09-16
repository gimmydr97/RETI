import java.io.*;
import java.util.Scanner;
import java.util.Random;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
public class scriviFileCCJ{
			public  static void main(String[] args) throws IOException {
				
				Scanner in1;
				Scanner in2;
				Scanner in3;
				Random random = new Random();
				
				System.out.println("inserire il numero di conti correnti presenti nella banca");
				in1 = new Scanner(System.in);
				int ncc = in1.nextInt();
				
				JSONObject jcc = new JSONObject();
				JSONArray listOfCC = new JSONArray(); 
				System.out.println("inserisci il nome del file da creare :");
				in3 = new Scanner(System.in); 
				String nfile = in3.next();
				for(int i=0 ; i < ncc; i++ ) {
				
					System.out.println("inserire il nome del " + i + " correntista" );
					in2 = new Scanner(System.in); 
					String name = in2.next();
					contoCorrenteJ cc = new contoCorrenteJ(name);
					
					int nm = random.nextInt(10)+10;
					
					for(int c=0 ; c <= nm; c++) {
						movimentoJ mov = new movimentoJ();
						cc.addMovimento(mov);
					}
					
					listOfCC.add(cc.getJSON());
					try {
						File file = new File(nfile);
						file.createNewFile();
						FileWriter filewriter = new FileWriter(file);
						jcc.put("cc",listOfCC );
						filewriter.write(jcc.toJSONString());
						jcc.put("gfile", file.length());
						filewriter.flush();
						filewriter.close();
					} catch(IOException e) {e.printStackTrace();}
				}
			
				
				in1.close();
				System.out.println("File scritto con successo");
		}
		
	}

