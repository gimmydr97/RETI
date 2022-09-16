
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.swing.JTextArea;


public class Receiver implements Runnable {
	
	public final int LENGTH=512;
	JTextArea textArea;
	String indM;
	
	public Receiver(JTextArea textArea,String indM) {
		
		this.textArea = textArea;
		this.indM = indM;
	}
	public void run() {
		
		try(MulticastSocket socket = new MulticastSocket(3000);){
		
			DatagramPacket packet = new DatagramPacket( new byte[LENGTH], LENGTH );
			InetAddress multicastGroup= InetAddress.getByName(indM);
			socket.setSoTimeout(100000000);
			socket.joinGroup(multicastGroup);//mi unisco al multicast group identificato dall'ind multicast associato al documento che sto editando
			while(!Thread.interrupted()){//termina se il thread viene interrotto
				
				socket.receive(packet);//ricevo i messaggi inviati al multicast group
				textArea.append("\n"+new String(packet.getData(),packet.getOffset(),packet.getLength(),"UTF-8"));//stampo i messaggi nella casella di testo dell'interfaccia 
			}
		} catch (IOException e) {e.printStackTrace();}
		
	}
}
