import java.io.IOException;
import java.nio.channels.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerTCP {
	
	static RemoteInterface serverObject;
	static Remote RemoteObject;
	
  public ServerTCP() throws IOException{
			
			try {
				Registry r=LocateRegistry.getRegistry(9999);
				RemoteObject = r.lookup("UDbase-Service");
				serverObject = (RemoteInterface)RemoteObject;
			}
			catch(Exception e) {
				System.out.println("Error in invoking object method "+ e.toString());
				e.printStackTrace();
			}
			
			//Istanzio ed apro un ServerSocketChannel e lo registro sul Selettore con SelectionKey = "OP_ACCEPT";
			 HelpServer Help = new HelpServer();
			 Help.aperturaCanale();
			 
			 while(true){
				 	 
					 Help.getSelector().selectedKeys().clear();
					 Help.getSelector().select();

					 //Itero sulle chiavi presenti nel Selettore;
					 for (SelectionKey key : Help.getSelector().selectedKeys()) {
						 	
					         Help.faseAccetazione(key); //fase di accettazione della key  con utilizzo di HelpServer
							
							 if (key.isReadable()) { //Verifico che il canale registrato alla chiave key sia in stato READABLE;
								 
								 String receivedmsg = Help.letturaCanale(key);//leggo dal canale del client grazie a HelpServer
								 String operation=receivedmsg.substring(0, receivedmsg.indexOf("1"));
								 String receivedpassword;
								 String receivedname ="";
								 String sendmessage  ="";
								 String namedoc ="";
								 String sez;
								 String indM="";
								 //questo controllo è fatto perchè non tutti i comandi hanno bisogno del nome di un utente
								 if(operation.compareTo("logout")!=0      &&
									operation.compareTo("exists")!=0      &&
									operation.compareTo("putChat")!=0     &&
									operation.compareTo("removeAss")!=0   &&
									operation.compareTo("getCollegati")!=0&&
									operation.compareTo("decCollegati")!=0&&
									operation.compareTo("incCollegati")!=0)
									 
									 receivedname=receivedmsg.substring(receivedmsg.indexOf("1")+1,receivedmsg.indexOf("2"));
								 
								 switch(operation) {
								 		
								 		case("register"): 
								 		    	receivedpassword = receivedmsg.substring(receivedmsg.indexOf("2")+1,receivedmsg.indexOf("3"));
								 				sendmessage = serverObject.register(receivedname,receivedpassword);
								 				break;
								 			
								 		case("login"):
								 				receivedpassword = receivedmsg.substring(receivedmsg.indexOf("2")+1,receivedmsg.indexOf("3"));
										    	sendmessage = serverObject.login(receivedname,receivedpassword);	
										    	break;
										    
								 		case("logout"):
								 				sendmessage = "logout effettuato con successo";
								 				break;
								 		    
								 		case("create"):
								 			    namedoc = receivedmsg.substring(receivedmsg.indexOf("2")+1, receivedmsg.indexOf("3"));
								 				String nsez = receivedmsg.substring(receivedmsg.indexOf("3")+1);
								 				sendmessage = serverObject.create(namedoc,nsez,receivedname);
								 				break;
								 		
								 		case("share"):
								 		        namedoc = receivedmsg.substring(receivedmsg.indexOf("2")+1, receivedmsg.indexOf("3"));
								 	   			String nameCollab = receivedmsg.substring(receivedmsg.indexOf("3")+1);
								 	   			sendmessage = serverObject.share(namedoc , nameCollab , receivedname);
								 	   			break;
								 	   			
								 		case("msginvite"):
								 		   		String invite = serverObject.msgInvite(receivedname);
								 		   	    sendmessage = invite ;
								 		   		break;
								 		   		
								 		case("show"):
								 		   		namedoc  = receivedmsg.substring(receivedmsg.indexOf("2")+1, receivedmsg.indexOf("3"));
						 	   				    sez = receivedmsg.substring(receivedmsg.indexOf("3")+1);
						 	   					sendmessage = serverObject.show(namedoc , sez  , receivedname);
						 	   					break;
						 	   					
								 		case("list"):
								 		   		sendmessage = serverObject.list(receivedname);
								 	   			break;
								 	   			
								 		case("edit"):
								 				namedoc  = receivedmsg.substring(receivedmsg.indexOf("2")+1, receivedmsg.indexOf("3"));
								 	   			sez = receivedmsg.substring(receivedmsg.indexOf("3")+1);
								 	   			sendmessage = serverObject.edit(namedoc , sez  , receivedname);
								 	   			break;
								 	   	
								 		case("end-edit"):
								 				namedoc  = receivedmsg.substring(receivedmsg.indexOf("2")+1, receivedmsg.indexOf("3"));
						 	   					sez = receivedmsg.substring(receivedmsg.indexOf("3")+1,receivedmsg.indexOf("."));
						 	   					String contFile = receivedmsg.substring(receivedmsg.indexOf(".")+1);
						 	   					sendmessage = serverObject.end_edit(namedoc , sez , receivedname , contFile);
						 	   					break;
						 	   			
								 		case("exists"):
								 				namedoc = receivedmsg.substring(receivedmsg.indexOf("1")+1);
								 				sendmessage = serverObject.exists(namedoc);
								 				break;
								 				
								 		case("putChat"):
								 				namedoc = receivedmsg.substring(receivedmsg.indexOf("1")+1);
								 				sendmessage = serverObject.putChat(namedoc);
								 				break;
								 				
								 		case("removeAss"):
								 				namedoc = receivedmsg.substring(receivedmsg.indexOf("1")+1,receivedmsg.indexOf("2"));
						 						indM = receivedmsg.substring(receivedmsg.indexOf("2")+1);
						 						sendmessage = serverObject.removeAss(namedoc,indM);
						 						break;
						 				
								 		case("decCollegati"):
								 				namedoc = receivedmsg.substring(receivedmsg.indexOf("1")+1);
								 				serverObject.decCollegati(namedoc);
								 				break;
								 				
								 		case("incCollegati"):
							 					namedoc = receivedmsg.substring(receivedmsg.indexOf("1")+1);
							 					serverObject.incCollegati(namedoc);
							 					break;
							 				
								 		case("getCollegati"):
								 				namedoc = receivedmsg.substring(receivedmsg.indexOf("1")+1);
								 				sendmessage = serverObject.getCollegati(namedoc);
								 				break;
								 				
							 	}
								Help.getSC().register(Help.getSelector(),SelectionKey.OP_WRITE);
								key.attach(sendmessage);
							}
							
						    Help.faseWritable(key); //fase di writable della key con utilizzo di HelpServer
					 }
			   
			   }
			
		 }
}
