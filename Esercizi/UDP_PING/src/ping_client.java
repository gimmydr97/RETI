import java.io.*;
import java.net.*;

public class ping_client {//mandare in esecuzione dopo il ping_server
	//MainClass nome_server porta_server
	//nome_Server(String)
	//porta_Server(int)
	
	public static void main(String[] args) {
			 
		String nomeS;
		int  portaS;
		
		if (args.length != 2) {//se non vengono inseriti la porta e il nome del server il programma termina
			System.out.println("inserire il nome e la porta del server ");
			return ;
		}
		else {
			try {
				
				nomeS = args[0];
				
			}catch(Exception e) {
				System.out.println("ERR -arg 0");
				return;
			 }	
			try {
				
				portaS = Integer.valueOf(args[1]).intValue();
				
			}catch(Exception e) {
				System.out.println("ERR - arg 1");
				return;
			 }
		}
	try {		
		try {
			DatagramSocket ds = new DatagramSocket();//inizializzo un datagramsocket
		try {
			InetAddress ia = InetAddress.getByName(nomeS);// e un inetaddress con  il nome del server
			
		
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream dout = new DataOutputStream(bout);//incapsulo un baos in un das
			byte[]  data = new byte[100];
			DatagramPacket dp = new DatagramPacket(data,data.length,ia,portaS);//inizializzo un dp per invio di dati con l'array di byte creato sopra 
			long min=300,max=0;												   //la sua lunghezza , l'inetaddress, e il nome del server
			float avg ;
			long tmp = 0;//variabile tmp per il calcolo dell'average
			int pr = 0;//contatore che conta i pacchetti ricevuti indietro dal server
			
			for(int segno= 0 ; segno < 10; segno ++ ) {
				dout.writeUTF("PING");//scrivo la stringa 'PING' nel dataoutputstream
				dout.writeInt(segno);//il segno(int)
				long timestamp = System.currentTimeMillis();
				dout.writeLong(timestamp);//e il timestamp  nel dataoutputstream
				data = bout.toByteArray();
				dp.setData(data, 0, data.length);
				dp.setLength(data.length);
				ds.send(dp);//mando il dp sul ds 
				System.out.print("PING "+ segno + " " + timestamp + " RTT : " );
				bout.reset();//resetto il 	ByteArrayOutputStream
				try {	
						ds.setSoTimeout(2000);//setto un timeout di 2 secondi come tempo massimo per l'attesa della recive
						ds.receive(dp);//ricevo l'eco dal server
						long  rtt= System.currentTimeMillis()- timestamp ;//calcolo il rtt
						if(rtt < min) {min = rtt;}//setto il min
						if(rtt > max) {max = rtt;}//e il max
						pr++;//incremento i pr
						tmp+=rtt;//incremento tmp per il calcolo dell' avg
						System.out.println(rtt + " ms");
				}
				catch(InterruptedIOException e) {//se il 2 sec di attesa scadono prima che avvenga la recive
					System.out.println("*");//al posto del rtt stampo *
				 }
			}	
			avg = tmp/10;//calcolo avg
			avg = (float)(Math.rint(avg*120))/100;//lo setto a sole 2 cifre dopo la virgola
			System.out.println("--- PING Statistics ---");
			System.out.println("10 packets transmitted, "+ pr + " packets received, "+ (10-pr)*10 +"% packet loss ");//stampo le
			System.out.println("round-trip (ms) min/avg/max = " + min + "/" + avg + "/" + max) ;//statistiche
			ds.close();//chiudo il datagram socket
		}catch( UnknownHostException e) {}
		}catch( SocketException e1) {}
	}catch(IOException e2) {}
		
		
		
	}
}
