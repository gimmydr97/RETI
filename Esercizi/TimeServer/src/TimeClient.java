import java.net.*;
import java.io.*;

public class TimeClient {
		public static void main(String [] args) {
			
			//TimeClient indirizzo_IP_dategroup
			//indirizzo_IP_dategroup(String)
			
			InetAddress group =null;
			try {
				 group = InetAddress.getByName(args[0]);
				
			}catch(Exception e)
			 {
				System.out.println("Uso: java TimeClient indirizzo_IP_dategroup");
				System.exit(1);
			 }
			try{
				MulticastSocket ms = new MulticastSocket(4000);// creo un multicastSocket sul porto he condivido con i DatagramPacket del server
				ms.joinGroup(group);//unisco il ms al dategroup group
				byte[] buffer = new byte[1000];//inizializzo il ByteArray
				DatagramPacket dp = new DatagramPacket(buffer,buffer.length);//inizializzo un DatagramPacket per ricevere i pacchetti spediti sul dategroup
				System.out.println("sto partendo");
				for(int i = 0 ; i < 10 ; i++) {//per 10 volte
					try {
						ms.receive(dp);//ricevo i pacchetti spediti al dategroup su dp
						ByteArrayInputStream bin = new ByteArrayInputStream(dp.getData(),0,dp.getLength());//inizializzo un ByteArrayInputStream con le misure di dp
						DataInputStream ddis = new DataInputStream(bin);//lo incapsulo in un DataInputStream
						String data = ddis.readUTF();//leggo una stringa
						
						System.out.println("data e ora :" + data );
						
					}catch(IOException e1) {}
				}
				try {
						ms.leaveGroup(group);//quando e' finito il for dissocio il ms dal gruppo
						ms.close();// e lo chiudo
				}catch(IOException e2) {}
								
					
			}catch(IOException e) 
				{System.out.println(e);}
		}
}
