import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class HelpClient {
	
	public String connWithServer(String msg) throws IOException {
		
		SocketAddress address = new InetSocketAddress(3838); //Istanzio un Socket Address per stabilire una connessione locale con il Server sulla porta 1919;
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);	 //Alloco il ByteBuffer per letture e scritture;
		String msgreceived="";
		
		//Apro il Socket Channel e cerco di stabilire una connessione con il Server;
   	    SocketChannel sc;
        sc = SocketChannel.open();
	    sc.connect(address);
	    
	    //Scrivo nel byteBuffer il nome da reperire e conseguentemente scrivo sul SocketChannel sc
	    //per inviare la stringa al Server;
		byteBuffer.clear();
		byteBuffer.put(msg.getBytes());
		byteBuffer.flip();
		while(byteBuffer.hasRemaining()) { sc.write(byteBuffer);}
	    byteBuffer.clear();
	    
	    //Attendo che il SocketChannel sc abbia qualcosa da leggere (Il File restituito dal Server);
	    while(sc.read(byteBuffer)!= -1){
			 byteBuffer.flip();
			 while(byteBuffer.hasRemaining()){ msgreceived+=((char)byteBuffer.get()); }
			 byteBuffer.clear();
		}
	    sc.close();
		return msgreceived;
	}
    
}
