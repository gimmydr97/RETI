import java.util.ArrayList;
import java.util.Hashtable;

class UtenteData {
	private String Name;
	private String password;
	private Hashtable<String,Documento> listaDocCreati ;
	private Hashtable<String,Documento> listaDocCollaborati;
	private ArrayList<String>inviti;
	private boolean edit;
	
	UtenteData(String Name,String password){
		this.Name=Name;
		this.password = password;
		this.edit = false;//booleano che indica se un utente è in fase di editing o meno
		this.listaDocCreati = new Hashtable<String,Documento>();
		this.listaDocCollaborati = new Hashtable<String,Documento>();
		this.inviti = new ArrayList<String>();
	}
	//restituisce il nome dell'utente
	public String getName() { return new String(Name); }
	//restituisce la password dell'utente
	public String getPassword() { return new String(password); }
	//restituisce la lista di documenti creati + collaborati
	public Hashtable<String,Documento> getList(){ 
		
		Hashtable<String,Documento> listaDoc = new Hashtable<String,Documento>(); 
		listaDoc.putAll(listaDocCreati);
		listaDoc.putAll(listaDocCollaborati);
		return listaDoc;
		
	}
	//restituisce il documento creato di nome nameDoc
	public Documento getDocCreato(String nameDoc) {

		if(nameDoc == null )throw new NullPointerException();
		if(listaDocCreati.containsKey(nameDoc)) return listaDocCreati.get(nameDoc);
		return null;
	}
	//restituisce il documento collaborato di nome nameDoc
	public Documento getDocCollaborato(String nameDoc) {

		if(nameDoc == null )throw new NullPointerException();
		if(listaDocCollaborati.containsKey(nameDoc)) return listaDocCollaborati.get(nameDoc);
		return null;
	}
	//restituisce il documento di nome nameDoc
	public Documento getDoc(String nameDoc) {

		if(nameDoc == null )throw new NullPointerException();
		if(listaDocCreati.containsKey(nameDoc)) return listaDocCreati.get(nameDoc);
		if(listaDocCollaborati.containsKey(nameDoc)) return listaDocCollaborati.get(nameDoc);
		return null;
	}
	//aggiunge un documento alla lista di documenti creati  e restituisce 1 se va tutto a buo fine 0 altrimenti 
	public void addDocCreati(Documento doc) { listaDocCreati.put(doc.getName(),doc); }
	//aggiunge un documento alla lista di documenti collaborati e restituisce 1 se va tutto a buo fine 0 altrimenti 
	public int addDocCollaborati(Documento doc) {
		
		if(doc == null)throw new NullPointerException();
		if(!listaDocCollaborati.containsKey(doc.getName())) { listaDocCollaborati.put(doc.getName(),doc); return 1;}
	    return 0;
	}
	//aggiunge un invito alla lista di inviti di quest'utente
    public void addInvite(String invito) {
    	if(invito == null)throw new NullPointerException();
    	inviti.add(invito);
    }
	//restituisce l'invito all'indice index
    public String getInvite(int index) { return inviti.get(index);}
    //restituisce l'indice dell'ultimo invito aggiunto se sono presenti inviti, 0 altrimenti
    public int haveInvite() {
		if(!inviti.isEmpty()) return inviti.size()-1;
		else return -1;
	}
    //elimina un invito dalla lista di inviti
    public void removeInvite(int index) {
    	inviti.remove(index);
    }
    //restituisce il numero di documenti di quest'utente
    public int getND() {return listaDocCreati.size();}
    //restituisce il valore di edit
    public boolean getEdit() {return edit;}
    //cambia lo stato di edit 
    public void changeEdit() {
    	if(edit) edit = false;
    	else edit = true;
    }
}
