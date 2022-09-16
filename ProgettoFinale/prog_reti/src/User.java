import java.io.File;
import java.io.Serializable;

import java.util.Hashtable;

public class User implements Serializable {


	private static final long serialVersionUID = 1L;

	private String nome;
	private String password;
	private Hashtable<String,Document> createdDocuments;//forse meglio mettere solo i nomi perch� se faccio list carico un bordello di file
	private Hashtable<String,Document> collabDocuments;
	String stato; //può essere started logged edited

	public User(String nome, String password) {
		this.nome = nome;
		this.password = password;
		this.createdDocuments = new Hashtable<String,Document>();
		this.collabDocuments = new Hashtable<String,Document>();
		this.stato = null;
	}
	
	public String getName(){
		return nome;
	}
	
	public String getPassword(){
		return password;
	}

	public void addCreatedDoc(String nomeDoc ,Document documento){
		createdDocuments.put(nomeDoc ,documento);
	}
	
	public void addCollabDoc(String nomeDoc ,Document documento) {
		collabDocuments.put(nomeDoc ,documento);
	}
	public Document getDoc(String nomeDoc) {
		if(createdDocuments.containsKey(nomeDoc))
			return createdDocuments.get(nomeDoc);
		else return collabDocuments.get(nomeDoc);
	}
	
	public File getSez(String nomeDoc, int numSez) {
		if(createdDocuments.containsKey(nomeDoc))
			return createdDocuments.get(nomeDoc).getSezione(numSez);
		else return collabDocuments.get(nomeDoc).getSezione(numSez);
	}
	public void logout(){ stato = "started";}
	
	public void closeProgram(){}

	public void share(String nomeDoc, String nomeUser){
		//solo chi ha creato può invitare altre persone
	}

	/*public Document show(String nomeDoc){}*/

	/*public Sezione show(String nomeDoc, int numSezione){}*/

	public void list(){
		//sia creati che in collaborazione
	}

	public void edit(String nomeDoc,int numSezione){}

	public void endEdit(){
		//si salvano le modifiche apportate al documento
	}

	public void send(String msg){}

	/*public String read(String msg){}*/

	

	
	
}