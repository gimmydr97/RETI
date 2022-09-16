import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class Receiver implements Runnable {
public final int LENGTH=512;
	
	public void run() {
		
		try(MulticastSocket socket = new MulticastSocket(3000);){
		
			DatagramPacket packet = new DatagramPacket(
					new byte[LENGTH], LENGTH);
			InetAddress multicastGroup= InetAddress.getByName(
					"239.1.1.1");// 224.0.0.0 – 239.255.255.255
			socket.setSoTimeout(100000000);
			socket.joinGroup(multicastGroup);
			while(!Thread.interrupted()){
				socket.receive(packet);
				System.out.println(new String(
						packet.getData(),
						packet.getOffset(),
						packet.getLength(),
						"UTF-8"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
