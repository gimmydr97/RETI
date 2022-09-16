import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ChatRoomServer{
	

	public final int LENGTH;
	HelpClient help;
	
	public ChatRoomServer() {
		LENGTH=512;
		help = new HelpClient(); 
	}

	public static void main(String[] args) {
		ChatRoomServer server = new ChatRoomServer();
		server.go();
	}
	
	public void go(){
		try(DatagramSocket socket = new DatagramSocket(2000);){
			
			DatagramPacket packet = new DatagramPacket(new byte[LENGTH],LENGTH);
			msg = help.connWithServer("msginvite"+"1"+name+"2");
			System.out.println(indMulticast);
			InetAddress multicastGroup= InetAddress.getByName(indMulticast); //224.0.0.0 – 239.255.255.255
		
			while(true){
				socket.receive(packet);
				System.out.println("server ha ricevuto: "+new String(
						packet.getData(),
						packet.getOffset(),
						packet.getLength(),
						"UTF-8")+" da "+packet.getAddress());
				DatagramPacket multicastPacket = 
						new DatagramPacket(packet.getData(),
								packet.getOffset(), 
								packet.getLength(),
								multicastGroup, 3000);
				socket.send(multicastPacket);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
