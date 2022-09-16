import java.io.*;
import java.net.*;
import java.util.Date;

public class TimeServer {
	public static void main (String[] args) {
		
		//TimeServer indirizzo_IP_dategroup
		//indirizzo_IP_dategroup(String)
		//il server essendo appunto un server non si blocca mai qundi per fermare la sua esecuzione premere Ctrol + C
		try {
				InetAddress ia = null;  
				try {
					ia =  InetAddress.getByName(args[0]);
				 
				}catch(Exception e) {
					System.out.println("Uso: java TimeServer  indirizzo_IP_dategroup");
					System.exit(1);
				}

				ByteArrayOutputStream bout = new ByteArrayOutputStream();//inizializzo un ByteArrayOutputStream
				DataOutputStream dout = new DataOutputStream(bout);//lo incapsulo in un DataOutputStream
				byte[] dat = new byte[1000];//inizializzo un array di byte
				DatagramPacket dp = new DatagramPacket(dat,dat.length,ia,4000);//inizializzo un DatagramPacket che ha come ia un indirizzo_IP_dategroup 
																			   //e come port un porto che condividera' con il MulticastSocket del client
				DatagramSocket ds = new DatagramSocket();//inizializzo un DatagramSocket anonimo
			
				while(true) {
					Date data = new  Date( System.currentTimeMillis());//creo una variabile di tipo Date che prendera' la data corrente
					String deo = new String(data.toString());//la trasformo in stringa
					dout.writeUTF(deo);//e la scrivo sul DataOutputStream
					dat= bout.toByteArray();//il byte array prende il ByteArrayOutputStream convertito in ByteArray
					dp.setData(dat, 0, dat.length);//setto il 	DatagramPacket alla nuova conformazione di dat
					dp.setLength(dat.length);//setto anche la sunghezza in base a quella del byte Array
					ds.send(dp);//lo invio al dategroup
					System.out.println(" ho spedito :" + deo);
					bout.reset();//resetto il ByteArrayOutputStream
			 
					try {
						Thread.sleep(8000);//faccio la sleep per simulare la latenza
					}catch(InterruptedException e) {}
			 
				}
	
		}catch(IOException e1) {System.out.println(e1);}
	}
}
