
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ChatRoomServer implements Runnable{
	

	public final int LENGTH;
	HelpClient help;
	String nameDoc;
	
	public ChatRoomServer(String nameDoc) {
		LENGTH=512;
		help = new HelpClient(); 
		this. nameDoc = nameDoc;
	}

	
	public void run(){
		try(DatagramSocket socket = new DatagramSocket(2000);){
			
			DatagramPacket packet = new DatagramPacket(new byte[LENGTH],LENGTH);
			String indMulticast = help.connWithServer("putChat"+"1"+nameDoc);//richiedo un ind multicast
			InetAddress multicastGroup= InetAddress.getByName(indMulticast); //224.0.0.0 – 239.255.255.255
			
			do{
				//fase di smistamento messaggi sul multicast group
				socket.receive(packet);
				DatagramPacket multicastPacket = new DatagramPacket(packet.getData(),packet.getOffset(), packet.getLength(),multicastGroup, 3000);
				socket.send(multicastPacket);
				
			}while(help.connWithServer("getCollegati"+"1"+nameDoc).compareTo("0")!=0);//termina quando nn c'è più nessun utente associato a questa chat
			help.connWithServer("removeAss"+"1"+nameDoc+"2"+indMulticast);//richiede la rimozione dell'associazione nameDoc-indMulticast
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
