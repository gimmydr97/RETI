
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
	public static void main(String args[])throws IOException {//poi lo cancelliamo perch� avremo l'interfaccia cos� togliamo i metodi statici
		boolean finito=false;		//diventa true quando l'utente chiude il programma
		Scanner scanner= new Scanner(System.in);
		String input;
		String fileName;
		String nome;
		String password;
		String nomeDoc;

		while(!finito) {
			System.out.println("Command list:");
			System.out.println("1) r per registrarti");
			System.out.println("2) l per effettuare il login");
			System.out.println("3) e per uscire dal programma");
			System.out.println("4) c per creare un documento");
			System.out.println("5) s per condividere un documento");
			System.out.println("6) q per effetuare il logout");
			input=scanner.next();
			switch(input) {
				case("r"):
					//facciamo metodi registrazione ecc cos� snelliamo il codice
					System.out.println("Inserisci il tuo username:");
					nome=scanner.next();
					System.out.println("Inserisci la tua password:");
					password=scanner.next();
					String result=registerC(nome,password);
					System.out.println(result);
					break;
				case("l"):
					System.out.println("Inserisci il tuo username:");
					nome=scanner.next();
					System.out.println("Inserisci la tua password:");
					password=scanner.next();

					fileName = "login "+nome+","+password; //Nome del file da reperire dal Server nel formato "nomefile.estensione";
				  System.out.println(connWithServer(fileName));
					break;

				case("c"):
					System.out.println("Inserisci il nome del documento:");
					String nomedoc=scanner.next();
					System.out.println("Inserisci il numero di sezioni in cui vuoi dividere il documento:");
					int numSezioni=scanner.nextInt();
				  fileName = "create "+nomedoc+","+numSezioni;//Nome del file da reperire dal Server nel formato "nomefile.estensione";
					System.out.println(connWithServer(fileName));
					break;

				case("w"):
					System.out.println("Inserisci il nome del documento:");
				     nomedoc=scanner.next();
					fileName= "showDoc "+nomedoc;
					System.out.println(connWithServer(fileName));
					break;

				case("k"): //le fantasie per le lettere iniziano a scarseggiare xd hahahaha
					System.out.println("Inserisci il nome del documento:");
					 nomedoc=scanner.next();
					System.out.println("Inserisci il numero della sezione che ti interessa:");
					int numSezione=scanner.nextInt();
					fileName="showSez "+nomedoc+","+numSezione;
					System.out.println(connWithServer(fileName));
					break;

				case("e"): //modifica del Documento
					System.out.println("Inserisci il nome del documento:");
					 nomedoc=scanner.next();
					fileName="edit "+nomedoc;
					System.out.println(connWithServer(fileName));
					break;

				case("s"): //condivisione del Documento
					System.out.println("Inserisci il nome del documento:");
					 nomedoc=scanner.next();
					System.out.println("Inserisci il nome dell'utente con cui vuoi condividere il documento:");
					String user=scanner.next();
					fileName="share "+nomedoc+","+user;
					System.out.println(connWithServer(fileName));
					break;

			}
		}
		scanner.close();
	}
	public static String connWithServer(String fileName)throws IOException {

		SocketAddress address = new InetSocketAddress(3838);		//Istanzio un Socket Address per stabilire una connessione locale con il Server sulla porta 1919;
		ByteBuffer byteBuffer = ByteBuffer.allocate(64);			 //Alloco il ByteBuffer per letture e scritture;

		//Apro il Socket Channel e cerco di stabilire una connessione con il Server;
		System.out.println("Tento la connessione...");
   	    SocketChannel sc;
        sc = SocketChannel.open();
	    sc.connect(address);

	  //Scrivo nel byteBuffer il nome da reperire e conseguentemente scrivo sul SocketChannel sc
	    //per inviare la stringa al Server;
		byteBuffer.clear();
		byteBuffer.put(fileName.getBytes());
		byteBuffer.flip();
		while(byteBuffer.hasRemaining()) {
			 sc.write(byteBuffer);
		 }
	    byteBuffer.clear();

	  //Attendo che il SocketChannel sc abbia qualcosa da leggere (Il File restituito dal Server);
	    String msgreceived="";
		while(sc.read(byteBuffer)!= -1){
			 byteBuffer.flip();
			 while(byteBuffer.hasRemaining()){
				 msgreceived+=((char)byteBuffer.get());
			 }
			 byteBuffer.clear();
		}
		return msgreceived;

	}

	public static String registerC(String nome, String pass){
		String result;
		try {
			Registry r = LocateRegistry.getRegistry(3839);
			Remote RemoteObject = r.lookup("USER-SERVER");
			UserInterface serverObject = (UserInterface) RemoteObject;
			result=serverObject.register(nome,pass);
		}
		catch (Exception e) {
			result="Error in invoking object method " + e.toString();
			e.printStackTrace();
		}

		return result;

	}

}