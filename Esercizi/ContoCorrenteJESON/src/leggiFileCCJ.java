import java.io.*;

import java.util.*;
import java.util.concurrent.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

public class leggiFileCCJ {
	public static void  main(String[]args)throws IOException{
			 
			 statisticheJ stat =new statisticheJ();
			 int ntotMov = 0;
			 ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newCachedThreadPool();
			 JSONParser parser= new JSONParser(); 
			 System.out.println("inserisci il nome del file da leggere :");
				Scanner in1 = new Scanner(System.in); 
				String nfile = in1.next();
				Long time1=System.currentTimeMillis();
		   try {
			  
				Object obj =parser.parse(new FileReader(nfile));
				JSONObject jobj0 = (JSONObject)obj;
				System.out.println(jobj0.toString());
				JSONArray jsonArray = (JSONArray)jobj0.get("cc");
				System.out.println("la dimensione del file di descrizione dei conti correnti e':"+ jobj0.get("gfile").toString());
				for(int i = 0; i< jsonArray.size();i++) {
					JSONObject jobj= (JSONObject)jsonArray.get(i);
					JSONArray listaCausali = (JSONArray)jobj.get("causali");
					ntotMov = ntotMov + listaCausali.size();
					Iterator<String> iterator = listaCausali.iterator();
					TaskJ task = new TaskJ(iterator,stat);
					executor.execute(task);
					
				}
			}catch(ParseException e) {  e.printStackTrace(); }
			 catch(FileNotFoundException e) {  e.printStackTrace();  }
			 catch(IOException e) {  e.printStackTrace();  }
		    Long time2=System. currentTimeMillis();
		   	System.out.println(" il tempo totale impiegato e':" +(time1-time2)+ "mls");
		    System.out.println("il numero totale di movimenti e': " + ntotMov);
			System.out.println();
			System.out.println("il numero complessivo di bonifici e': "   + stat.getBon()+ "\n" + 
								   "il numero complessivo di Accrediti e': "  + stat.getAcc()+ "\n" +
								   "il numero complessivo di Bollettini e':"  + stat.getBol()+ "\n" +
								   "il numero complessivo di F24 e':"         + stat.getF()  + "\n" +
								   "il numero complessivo di PagoBancomat e':"+ stat.getPB() + "\n" );
			
				
			 
	}	
}
