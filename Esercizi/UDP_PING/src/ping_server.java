import java.io.*;
import java.net.*;
import java.util.Random;

public class ping_server {//mandare in esecuzione prima del ping_client
	public static void main(String[] args) {
		//MainClass  porta_server
		//porta_Server(int)

		int  portaS;
		
		if (args.length != 1) {//se non viene inserita la porta del server termino il programma
			System.out.println("inserire la porta dove e' attivo il server ");
			return ;
		}
		else {
			try {
				
				portaS = Integer.valueOf(args[0]).intValue();
				
			}catch(Exception e) {
				System.out.println("ERR - arg 0");
				return;
			 }
		}
		try {
			DatagramSocket ds = new DatagramSocket(portaS);//inizializzo un ds sulla porta del server
			byte[] buff = new byte[100];
			DatagramPacket dp = new DatagramPacket(buff, buff.length);//creo un dp per riceere con un buffer creato in precedenza
			for(int i=0; i < 10; i++) {
				ds.receive(dp);//tento di ricevere dal client
				ByteArrayInputStream bin = new ByteArrayInputStream(dp.getData(),0,dp.getLength());//creo un ByteArrayInputStream con i dati ricevuti sul dp che parte dall'indice 0 fino a dp.getlength
				DataInputStream ddis = new DataInputStream(bin);//lo incapsulo in un DataInputStream 
				String ping = ddis.readUTF();//leggo la stringa ' PING '
				int segno = ddis.readInt();//l'inter segno
				long timestamp = ddis.readLong();//e il timestamp dal ddis
				Random rand = new Random();//inizializzo una variabile randomica
				int r1 = rand.nextInt(3);//assegno a r1 un numero random tra 0 e 3 per implementare la perdita di pacchetti del 25 %
				int r2 = rand.nextInt(290)+10;//assegno poi a r2 un numero random tra 10 e 300 che sara' il ritardo del server
				try {
					if(r1!=0) {// se il pacchetto non e' stato perso 
						Thread.sleep(r2);//creo un ritardo di r2 ms
						ds.send(dp);//e mando l'eco al client
						
						System.out.println(dp.getAddress() + ":" + dp.getPort() + "> " + ping + " " + segno + " " + timestamp + " ACTION : delayed " + r2 + " ms");
						}
					else {
						System.out.println(dp.getAddress() + ":" + dp.getPort() + "> " + ping + " " + segno + " " + timestamp + " ACTION : not send ");
					}
					
					
				}catch(InterruptedException e) {}
				
			}
			ds.close();//chiudo il ds
			
		}catch(SocketException e) {}
		 catch(IOException e1) {}
		 
	}
}
