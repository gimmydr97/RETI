import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.*;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.Hashtable;

public class Server extends RemoteServer implements UserInterface {

	private static final long serialVersionUID = 1L;
   static  Hashtable<String,User> UserList;
   static Hashtable<SelectionKey,String> ListaKey; 
	
	
	
	public Server() {
		 UserList = new Hashtable<String,User>();	//nome e' la chiave e User il valore
		 ListaKey = new Hashtable<SelectionKey,String>();
	}
	
	public String register(String nome, String password) throws RemoteException{
		String result;
		if(nome==null || password==null)result="Nome o password nulli";
		else {
			if(UserList.containsKey(nome))result="Nome utente già esistente";
			else {
				User user=new User(nome, password);
				user.stato = "started";
				UserList.put(nome,user);
				result="Utente registrato con successo";
				//bisogna serializzare
			}
		}
		return result;			
	}
	public static void main(String args[])throws IOException{
	
	
	try {
		Registry r;
		int port = 3838;
		Server service=new Server();
		UserInterface stub= (UserInterface) UnicastRemoteObject.exportObject(service, 0); // Esportazione dell'Oggetto
		LocateRegistry.createRegistry(port);
		r=LocateRegistry.getRegistry(port);  // Creazione di un registry sulla porta 3838
		r.rebind("USER-SERVER", stub);
		System.out.println("Server ready");
	}catch(RemoteException e) {System.out.println("Communication error " + e.toString());} 
	
	
	Selector selector;
	ServerSocketChannel server;
	String receivedmsg=" ";
    String sendmessage = null;
    File sendFile = null;
	String receivedname;
	String receivedpassword;
	String receivednamedoc;
	int numSezioni;
	
	server = ServerSocketChannel.open();
	ServerSocket ss ;
	ss = server.socket();
	ss.bind(new InetSocketAddress(3838));;
	server.configureBlocking(false);
	selector = Selector.open();
	server.register(selector,SelectionKey.OP_ACCEPT);
		  
			  
		         
				 
		
		  
		  while(true){
				 System.out.println("Attendo una richiesta...");
				 
				 selector.select();
				 
				 //Itero sulle chiavi presenti nel Selettore;
				 for (SelectionKey key : selector.selectedKeys()) {

						 if (key.isAcceptable()) {

								 SocketChannel client = ((ServerSocketChannel) key.channel()).accept();
								 client.configureBlocking(false);
								 client.register(selector, SelectionKey.OP_READ);
						 }

						 if (key.isReadable()) {

								 //Verifico che il canale registrato alla chiave key sia in stato READABLE;
								 //Leggo dal SocketChannel client associato alla chiave key per reperire il nome del File da cercare;

								 SocketChannel client =(SocketChannel)key.channel();
								 ByteBuffer buffer = ByteBuffer.allocate(64);
								 buffer.clear();
								 while(client.read(buffer)!= 0){
										 buffer.flip();
										 while(buffer.hasRemaining()){
												 receivedmsg += (char)buffer.get();
										 }
										 buffer.clear();
								 }

								 String operation=receivedmsg.substring(0, receivedmsg.indexOf(" ")-1);
								 switch (operation){
									 case("login"):
									 	    receivedname=receivedmsg.substring(receivedmsg.indexOf(" ")+1, receivedmsg.indexOf(",")-1);
										    receivedpassword=receivedmsg.substring(receivedmsg.indexOf(",")+1);
										 	if(!UserList.containsKey(receivedname)) sendmessage="nome utente errato";
										 	
										 	else if(!receivedpassword.equals(UserList.get(receivedname).getPassword()))
													sendmessage="password errata";
											
										 	else {
												sendmessage="login avvenuto con successo";
												UserList.get(receivedname).stato = "logged";
												ListaKey.put(key,receivedname);
												}
										 	break;
										case("create"):
											if(ListaKey.containsKey(key)) {
												 receivednamedoc=receivedmsg.substring(receivedmsg.indexOf(" ")+1,receivedmsg.indexOf(",")-1) ;
												 numSezioni=Integer.parseInt(receivedmsg.substring(receivedmsg.indexOf(",")+1));
												 Document documento = new Document(numSezioni,UserList.get(ListaKey.get(key)).getName());
												 UserList.get(ListaKey.get(key)).addCreatedDoc(receivednamedoc ,documento);
												 //serializzare
												 sendmessage="documento: "+receivednamedoc + " creato con successo composto da: "+ numSezioni + " sezioni";
												
											}else {sendmessage = "utente non loggato";}
									
											break;
											
										case("logout"): 
											if(ListaKey.containsKey(key)) {
												sendmessage = "utente : "+ListaKey.get(key)+"ha effettuato il logout con successo";
												ListaKey.remove(key);
											}else {sendmessage = "utente non loggato";}
											break;
											
										case("shows"):
											if(ListaKey.containsKey(key)) {
											  receivednamedoc=receivedmsg.substring(receivedmsg.indexOf(" ")+1,receivedmsg.indexOf(",")-1);
											  numSezioni=Integer.parseInt(receivedmsg.substring(receivedmsg.indexOf(",")+1));
											  sendFile = UserList.get(ListaKey.get(key)).getSez(receivednamedoc, numSezioni);
											  sendmessage = "sezione "+numSezioni+" scaricata con successo";
											  
											}else {sendmessage = "utente non loggato";}
									
										
									}
									if(operation == "shows" && ListaKey.containsKey(key) ) {
										
										key.channel().register(selector,SelectionKey.OP_WRITE,sendmessage);
										key.channel().register(selector,SelectionKey.OP_WRITE,sendFile);
										
									}else {key.channel().register(selector,SelectionKey.OP_WRITE,sendmessage);}
						 }
						 if (key.isWritable()) {

								 //Verifico che il canale registrato alla chiave key sia in stato WRITABLE;
								 //Reperisco il File dal Server e lo Scrivo sul SocketChannel associato alla chiave key;

								 SocketChannel client = (SocketChannel)key.channel();
								 FileChannel input = FileChannel.open(Paths.get((String)key.attachment()),StandardOpenOption.READ); //speriamo sia sendmessage

								 ByteBuffer buffer = ByteBuffer.allocate(64);

								 boolean finito = false;
								 while (!finito) {
										 int bytesRead = input.read(buffer);
										 if (bytesRead == -1) {
												 finito = true;
										 }

										 buffer.flip();
										 while (buffer.hasRemaining()) {
												 client.write(buffer);
										 }
										 buffer.clear();
								 }

								 if(!ListaKey.containsKey(key)) {key.cancel();}
								 input.close();
								 
						 }
				 }
		 }


				
	}
}
	
	
	
	
	


