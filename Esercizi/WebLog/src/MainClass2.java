import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainClass2 {
	//main della versione mono-thread
	public static void main(String args[])throws IOException {
		
		String webL ="web.log.txt";//nome del file da leggere
		File 		file = new  File(webL);//file per leggere web.log.txt
		FileWriter     w = new FileWriter("web2.log.txt");//scrittore del nuovo file da creare
    	BufferedWriter b = new BufferedWriter (w);//buffer dello scrittore del nuovo file
		try(FileReader fr = new FileReader(file)){//lettore del file da leggere
			BufferedReader br = new BufferedReader(fr);//buffer del file da leggere
			boolean flag = false;//flag per leggere tutte le righe del file
			Long time1=System.currentTimeMillis();
			while (!flag) {
				String tmp = br.readLine();//stringa che rappresenta una riga del file
				if(tmp == null) {flag= true;}//se tmp e' uguale a null allora esco dal ciclo
				if(tmp != null) {//se la stringa letta non e' null procedo alla traduzione
					try{
						InetAddress host= InetAddress.getByName(getHost(tmp));//traduco l'ind IP del host remoto 
						String newline = sostituisci(tmp,host.getHostName());// linea tradotta da scrivere nel nuovo file
						System.out.println(newline);//output della linea tradotta
						b.write(newline+"\n");//scrittura sul nuovo file
					}catch(UnknownHostException e) {}
				}
		
		  }
		  b.flush();
		  b.close();
		  Long time2=System.currentTimeMillis();
		  System.out.println(time2-time1);//stampa del tempo impiegato
		}
		catch(FileNotFoundException e) {e.printStackTrace();}
		catch(IOException e1) {e1.printStackTrace();}
		
		
	}
	private static String getHost(String line) {//metodo che restituisce l'IP dando come paramentro una linea del file
		String h = line.substring(0,line.indexOf(" "));//si estrae una sotto stringa che va dalla pos 0 alla prima posizione dove e' presente uno spazio
		return h;
	}
	private static String sostituisci(String line, String nameHost) {//metodo che prende come parametri una linea del file da leggere 
		String h = line.substring(0,line.indexOf(" "));				//e il nome dell'host e restituisce la linea tradotta
		String newline = line.replace(h,nameHost);
		return newline;
		
	}
}
