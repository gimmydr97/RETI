import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


//classe che nasce per snellire il codice del server

public class HelpServer {
	 ServerSocketChannel server;
	 Selector selector;
	 SocketChannel client;
	
	public ServerSocketChannel getSSC() {return server;}
	
    public Selector getSelector() {return selector;}
    
    public SocketChannel getSC() {return client;}
    
	public void aperturaCanale()throws IOException {//Istanzio ed apro un ServerSocketChannel e lo registro sul Selettore con SelectionKey = "OP_ACCEPT";
		
		 server = ServerSocketChannel.open();
		 server.socket().bind(new InetSocketAddress(3838));
		 server.configureBlocking(false);
		 selector  = Selector.open();
		 server.register(selector,SelectionKey.OP_ACCEPT);
		 System.out.println("Server ready....");
	}
	
	public String letturaCanale(SelectionKey key) throws IOException { //Leggo dal SocketChannel client associato alla chiave key 
		 
		 client =(SocketChannel)key.channel();
		 ByteBuffer buffer = ByteBuffer.allocate(64);
		 String receivedmsg = "";
		 while(client.read(buffer)!= 0){
			     
			     buffer.flip();
				 while(buffer.hasRemaining()){ receivedmsg += (char)buffer.get(); }
				 buffer.clear();
				 
		 }
		 return receivedmsg;
	}
	
	public void faseAccetazione(SelectionKey key) throws IOException {//metodo che sostituisce la fase di accettazione della key 
		
		 if (key.isAcceptable()) { 
		     
		     SocketChannel client = ((ServerSocketChannel) key.channel()).accept();
			 client.configureBlocking(false);
			 client.register(selector, SelectionKey.OP_READ);
			 
		 }
	}
	
    public void faseWritable(SelectionKey key)throws IOException {//metodo che sostituisce la fase di scrittura della key
	   
	  if (key.isWritable()) { 

		  	  String sendmessage = (String)key.attachment();
		      SocketChannel socketChannel =(SocketChannel) key.channel();
		      ByteBuffer buffer = ByteBuffer.wrap(sendmessage.getBytes());
			  while(buffer.hasRemaining()) { socketChannel.write(buffer); }
			  buffer.clear();
			  socketChannel.close();
			  key.cancel();
	  }
   }
}
