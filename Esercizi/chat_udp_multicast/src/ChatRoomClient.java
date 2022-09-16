
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatRoomClient {
	public void go(){
		try(BufferedReader in= new BufferedReader(
				new InputStreamReader(System.in));
				DatagramSocket s= new DatagramSocket()){
			
			InetAddress  address= InetAddress.getByName("127.0.0.1");
			String message="";
			ExecutorService es = Executors.newSingleThreadExecutor();
			es.submit(new Receiver());
			System.out.println("Insert message or 'exit'");
			while(!(message=in.readLine()).equalsIgnoreCase("exit")){
				DatagramPacket p= new DatagramPacket(
						message.getBytes("UTF-8"),0,
						message.getBytes("UTF-8").length,
						address,2000);
				s.send(p);
			}
			es.shutdownNow();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ChatRoomClient client= new ChatRoomClient();
		client.go();
	}
}
