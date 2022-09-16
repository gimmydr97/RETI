
import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;



public class ServerRMI extends RemoteServer implements RemoteInterface{
	
	private static final long serialVersionUID = 1L;
	Hashtable<String,UtenteData> UDbase;
	Hashtable<String,indMulticast> Doc_IndMulti;
	int i1=224,i2=0,i3=0,i4=0; // interi utili alla costruzione degli indirizzi multicast
	
	public ServerRMI()throws RemoteException {
		 UDbase = new Hashtable<String,UtenteData>();
		 Doc_IndMulti = new Hashtable<String,indMulticast>();
	}
	//REGISTER
    public String register(String Name,String password)throws RemoteException {
    	
    	if(Name == null || password == null)throw new NullPointerException();
		if(!UDbase.containsKey(Name)) {
			UDbase.put(Name, new UtenteData(Name,password));
			return "registrazione eseguita con successo ";
		}
		else return "un utente con questo username e' gia' presente utilizzare un altro username ";
	}	
	//RESTITUISCE IL NUMERO DI UTENTI REGISTRATI
	public int  getNumber()throws RemoteException{
			int size =UDbase.size();
			return size;
	}	
	//LOGIN
	public String login(String name , String password)throws RemoteException{
		
		if(UDbase.containsKey(name) && UDbase.get(name).getPassword().compareTo(password) == 0) return "login effettuato con successo";
		else return "registrati oppure nome o password errata ";
	}
	//CREATE
	public String create(String name , String nSez, String nomeC)throws RemoteException, IOException {
		
		if(nomeC == null || name == null )throw new NullPointerException();
		if(!UDbase.get(nomeC).getList().containsKey(name)) {
			
				Documento doc = new Documento(name ,nSez, nomeC );
				UDbase.get(nomeC).addDocCreati(doc);
				return "documento " + name + " creato con successo composto da "+ nSez +" sezioni";
				
		}
		else return "esiste gia' un documento con questo nome";
	}
	//SHARE
	public String share(String doc ,String  nameCollab ,String  nomeC)throws RemoteException{
		
		if(doc == null || nameCollab == null || nomeC == null)throw new NullPointerException();
		if(!UDbase.containsKey(nameCollab))return "il collaboratore selezionato non e' un utente registrato";
		Documento  d = UDbase.get(nomeC).getDocCreato(doc) ;
		if (d != null) { 
			if(!d.containsCollab(nameCollab)) {
				d.addCollab(nameCollab);
				UDbase.get(nameCollab).addDocCollaborati(d);
				UDbase.get(nameCollab).addInvite("invito a collaborare al documento: "+doc+ " da parte dell' utente: "+nomeC);
				return "documento "+doc+" condiviso con "+nameCollab+" con successo ";
			}
			else return "esiste gia' un collaboratore con questo nome";
		}
	   return " l'utente "+nomeC+" non ha un documento creato di nome "+doc;
	}
    //RESTITUISCE UN MESSAGGIO DI INVITO SE PRESENTE 	
	public String msgInvite(String name)throws RemoteException{
		
		int index = UDbase.get(name).haveInvite() ;
		if ( UDbase.get(name).haveInvite() == -1) return "";
	    String invite =  UDbase.get(name).getInvite(index);
	    UDbase.get(name).removeInvite(index);
	    return invite;
	}
	//SHOW
	public String show(String doc, String sez, String nome)throws IOException,RemoteException{
		
		if(doc == null || sez == null || nome == null)throw new NullPointerException();
		if(UDbase.get(nome).getDoc(doc) == null ) return "documento "+doc+" non trovato";
		if(UDbase.get(nome).getDoc(doc).getSez(Integer.parseInt(sez)) == null) {return "nel documento "+doc+" non e' presente la sezione "+sez;}
		String sendString;
		if(UDbase.get(nome).getDoc(doc).getSez(Integer.parseInt(sez)).getStato().contains("occupato")) { sendString = doc+"_"+sez+"_"+"inEditing:"; }
		else sendString = doc+"_"+sez+"_"+"versioneAggiornata:";
		
		FileChannel inChannel = FileChannel.open(Paths.get(doc+"/"+sez),StandardOpenOption.READ);
		ByteBuffer buf = ByteBuffer.allocate(140);
		boolean stop = false;
		
		while(!stop) {
			int bytesRead = inChannel.read(buf);
			if(bytesRead == -1) {stop = true;}
			buf.flip();
			while(buf.hasRemaining()) {sendString+=(char)buf.get();}
			buf.clear();
		}
		inChannel.close();
		return sendString;
		
	}
	//LIST
	public String list(String name)throws RemoteException{
		
		if(name == null)throw new NullPointerException();
		
		Hashtable<String,Documento> listdoc = UDbase.get(name).getList();
		Iterator<Documento> iterator = (Iterator<Documento>) listdoc.values().iterator();
		String sendmsg = "";
		Documento d;
		int i = 1;
		while(iterator.hasNext()) {
			sendmsg+="\n";
			d = iterator.next();
			sendmsg+=i+")."+d.getName()+":\n   Creatore: "+d.getCreatore()+"\n   Collaboratori: ";
			ArrayList<String> collab = d.getCollabList(); 
			for(int n = 0 ; n < collab.size();n++) {
				sendmsg+=collab.get(n)+",";
			}
			i++;
		}
		return sendmsg;
	}
	//EDIT
	public String edit(String doc, String sez, String nome)throws IOException,RemoteException{
		
		if(doc == null || sez == null || nome == null)throw new NullPointerException();
		if(UDbase.get(nome).getDoc(doc) == null ) { return "documento "+doc+" non trovato"; }
		if(UDbase.get(nome).getDoc(doc).getSez(Integer.parseInt(sez)) == null) {return "nel documento "+doc+" non e' presente la sezione "+sez;}
		if(UDbase.get(nome).getDoc(doc).getSez(Integer.parseInt(sez)).getStato().contains("occupato"))
			{return "sezione gia' in fase di editing da parte di un altro utente";}
		if(UDbase.get(nome).getEdit()) {return "stai gia' editando una sezione";}
		
		UDbase.get(nome).getDoc(doc).getSez(Integer.parseInt(sez)).ChangeStato(nome);
		
		UDbase.get(nome).changeEdit();
		
		String sendString =doc+"_"+sez+":";
		
		FileChannel inChannel = FileChannel.open(Paths.get(doc+"/"+sez),StandardOpenOption.READ);
		ByteBuffer buf = ByteBuffer.allocate(140);
		boolean stop = false;
		
		while(!stop) {
			int bytesRead = inChannel.read(buf);
			if(bytesRead == -1) {stop = true;}
			buf.flip();
			while(buf.hasRemaining()) {sendString+=(char)buf.get();}
			buf.clear();
		}
		inChannel.close();
		return sendString;
		
	}
	//END-EDIT
	public String end_edit(String doc, String sez, String nome, String contenutofile)throws IOException,RemoteException{
		
		if(doc == null || sez == null || nome == null)throw new NullPointerException();
		if(UDbase.get(nome).getDoc(doc) == null ) { return "documento "+doc+" non trovato"; }
		if(UDbase.get(nome).getDoc(doc).getSez(Integer.parseInt(sez)) == null) {return "nel documento "+doc+" non e' presente la sezione "+sez;}
		if(UDbase.get(nome).getDoc(doc).getSez(Integer.parseInt(sez)).getStato().compareTo("occupato"+nome) != 0) 
			{ return "non stai editando questa sezione";}
		
		FileChannel outChannel = FileChannel.open(Paths.get(doc+"/"+sez),StandardOpenOption.WRITE);
		ByteBuffer buf = ByteBuffer.wrap(contenutofile.getBytes());
		while(buf.hasRemaining()) {outChannel.write(buf);}
		
		UDbase.get(nome).getDoc(doc).getSez(Integer.parseInt(sez)).ChangeStato(nome);
		UDbase.get(nome).changeEdit();
		
		return "Sezione "+ sez + " del documento " + doc + " aggiornata con successo " ;
	}
	//RESTITUISCE L'IND MULTICAST SE ESISTE GIA' UN SERVER PER LA CHAT UDP DEL DOCUMENTO d E INCREMENTA IL NUMERO DI COLLEGATI ALL 'INDM,
	//"Server non creato" ALTRIMENTI
	public String exists (String d)throws RemoteException {
		
		if(Doc_IndMulti.get(d) != null) return  Doc_IndMulti.get(d).getIndM(); 
		
		else return "Server non creato";
	}
	//COLLEGA UN IND MULTICAST CON IL DOCUMENTO MANDATO E RESTITUISCE L'IND MULTICAST COLLEGATO,
	//"indirizzi multicast finiti" SE NON SONO PIU' DISPONIBILI IND MULTICAST
	public String putChat(String d)throws RemoteException{
			if(d == null)throw new NullPointerException();
			String indM = incI(i1,i2,i3,i4);
			indMulticast stato = new indMulticast(indM);
			Doc_IndMulti.put(d,stato);
			return indM;
	}	
	//DECREMENTA IL NUMERO DI COLLEGATI A UN DOCUMENTO
	public void decCollegati(String d)throws RemoteException {
		if(d == null)throw new NullPointerException();
		Doc_IndMulti.get(d).decCollegati();
	}
	//INCREMENTA IL NUMERO DI COLLEGATI A UN DOCUMENTO
	public void incCollegati(String d)throws RemoteException {
			if(d == null)throw new NullPointerException();
			Doc_IndMulti.get(d).incCollegati();
		}
	//RESTITUISCE IL NUMERO DI UTENTI COLLEGATI AD UN IND MULTICAST
	public String getCollegati(String d)throws RemoteException {
		if(d == null)throw new NullPointerException();
		return String.valueOf(Doc_IndMulti.get(d).getCollegati());
	}
	//ELIMINA UN ASSOCIAZIONE DOCUMENTO-IND_MULTICST
	public String removeAss(String d,String indM)throws RemoteException {
			if(d == null || indM == null)throw new NullPointerException();
			Doc_IndMulti.remove(d);
			return "Associazione rimossa";
		
	}
	
	public static void main(String args[]) throws AlreadyBoundException, IOException {
		
			try {
				ServerRMI server = new ServerRMI();
				RemoteInterface stub =(RemoteInterface) UnicastRemoteObject.exportObject(server, 0);
				LocateRegistry.createRegistry(9999);
				Registry r=LocateRegistry.getRegistry(9999);
				r.bind("UDbase-Service", stub);
				@SuppressWarnings("unused")
				ServerTCP servertcp = new ServerTCP();
				}
			catch(RemoteException e) {System.out.println("Communication error "+ e.toString());}
		
	}
	//metodo che si occupa di incrementare gli interi che formeranno gli ind multicast
    public String incI(int i1, int i2, int i3, int i4 ) {
		
		if     (i4 < 255) i4 = i4+1;
		else if(i3 < 255) i3 = i3+1;
		else if(i2 < 255) i2 = i2+1;
		else if(i1 < 239) i1 = i1+1;
		else return "indirizzi multicast finiti";
		return i1 + "." + i2 + "." + i3 + "." + i4;	
	}
		
	
	
	
		
	
}
