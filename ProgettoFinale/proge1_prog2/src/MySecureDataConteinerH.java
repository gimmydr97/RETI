import java.util.Iterator;
import java.util.HashMap;

public class MySecureDataConteinerH<E> implements  SecureDataContainer<E> {
			//FA:  Id --> contenitore
			// dove  n = listaUtenti.size() e 0 <= a < n
			//IR: listaUtenti != null 
			//	  for all i. 0 <= i < listaUtenti.size() => Id_i != null 
			//    for all j,i.0 <= i < j < listaUtenti.size() => Id_i != Id_j
	
	private HashMap<String,contenitore<E>> listaUtenti;
	
	
	public MySecureDataConteinerH() throws IllegalArgumentException{
		listaUtenti = new HashMap<String,contenitore<E>>();
	}
	public void createUser(String Id, String passw) {
		if(Id == null || passw == null) {throw new NullPointerException(); }
		String nid = Id;
		if(!listaUtenti.containsKey(nid)){
			listaUtenti.put(nid,new contenitore<E>(passw));
		}
		else throw new IllegalArgumentException();
	}
	
	
	public int getSize(String Owner, String passw)throws UtenteNonTrovatoException{
		if(Owner == null || passw == null) {throw new NullPointerException(); }
		String nid = Owner;
		
		if(listaUtenti.containsKey(nid)) {
			int tmp =0 ;
			int nd = listaUtenti.get(nid).getListD().size();
			
			for(int i=0 ; i < nd ; i++) {
				int nc = listaUtenti.get(nid).getListD().get(i).getNC();
				tmp += nc;
			}
			return tmp;
		}
		
		else throw new UtenteNonTrovatoException() ;
	}
	
	public boolean put(String Owner, String passw, E data)throws UtenteNonTrovatoException, IllegalArgumentException {
		if(Owner == null || passw == null || data == null) {throw new NullPointerException(); }
		String nid = Owner;
		if(listaUtenti.containsKey(nid)) {
			dato<E> d = new dato<E>(data);
			if(!listaUtenti.get(nid).getListD().contains(d)) {
				 listaUtenti.get(nid).getListD().add(0,d);
				 return true;
			}else throw new  IllegalArgumentException();
		}
		else throw new UtenteNonTrovatoException();
	}
	
	public E get(String Owner, String passw, E data)throws DatoNonTrovatoException, UtenteNonTrovatoException{
		if(Owner == null || passw == null || data == null) {throw new NullPointerException(); }
		String nid = Owner;
		//si controlla se l'utente e' presente nella lista 
		//e se data fa parte dei suoi dati 
		//in questo caso viene restituita una copia del valore del dato
		if(listaUtenti.containsKey(nid)) {
			dato<E> d = new dato<E>(data);
			int index = listaUtenti.get(nid).getListD().indexOf(d);
			if( index != -1 ) { return listaUtenti.get(nid).getListD().get(index).getD();}
			//se il dato non fa parte dei dati dell'utente lancio DatoNonTrovatoException
			else throw new DatoNonTrovatoException();
		}
		//se l'utente non e' presente nella collezione lancio UtenteNonTrovatoException
		else throw new UtenteNonTrovatoException();
	}
	
	public E remove(String Owner, String passw, E data)throws DatoNonTrovatoException,UtenteNonTrovatoException{
		if(Owner == null || passw == null || data == null) {throw new NullPointerException(); }
		String nid = Owner;
		if(listaUtenti.containsKey(nid)) {
			dato<E> d = new dato<E>(data);
			int index = listaUtenti.get(nid).getListD().indexOf(d);
			if( index!=-1 ) {
				if(listaUtenti.get(nid).getListD().get(index).getNC() > 1) {
					listaUtenti.get(nid).getListD().get(index).decNC();
					return data;
				}
				if(listaUtenti.get(nid).getListD().get(index).getNC() == 1) {
					listaUtenti.get(nid).getListD().remove(index);
					return data;
				}
			}	
		    throw new DatoNonTrovatoException();
		}
		throw new UtenteNonTrovatoException();
	}

	public void copy(String Owner, String passw, E data)throws DatoNonTrovatoException,UtenteNonTrovatoException{
		if(Owner == null || passw == null || data == null) {throw new NullPointerException(); }
		String nid = Owner;
		if(listaUtenti.containsKey(nid)) {
			dato<E> d = new dato<E>(data);
			int index = listaUtenti.get(nid).getListD().indexOf(d);
			if(index !=-1)
				listaUtenti.get(nid).getListD().get(index).incNC();
			else throw new DatoNonTrovatoException();
		}
		else throw new UtenteNonTrovatoException();
	}
		
	public void share(String Owner, String passw,String Other, E data)throws DatoNonTrovatoException,UtenteNonTrovatoException,
																		     UtenteNonAmmessoException {
		if(Owner == null || passw == null || Other == null || data == null) {throw new NullPointerException(); }
		String nid = Owner;
		if(listaUtenti.containsKey(nid)){
			dato<E> d = new dato<E>(data);
			int index = listaUtenti.get(nid).getListD().indexOf(d);
			if(index !=-1) {
				if(listaUtenti.get(nid).getListD().get(index).getListaU().contains(Other)) {
			   	 	if(listaUtenti.containsKey(Other)) {listaUtenti.get(Other).getListD().add(d);}
			   	 	else {throw new UtenteNonTrovatoException();}
				} else throw new UtenteNonAmmessoException();
			}else throw new DatoNonTrovatoException();
			
		}else throw new UtenteNonTrovatoException();
	}
//metodo che inserisce un utente nella lista utenti di un dato	
	public void putUAmmesso(String Owner,String passw, String Other,E data)throws DatoNonTrovatoException,UtenteNonTrovatoException {
		if(Owner == null || passw == null || Other == null) {throw new NullPointerException(); }
		String nid = Owner;
		if(listaUtenti.containsKey(nid)){
			dato<E> d = new dato<E>(data);
			int index = listaUtenti.get(nid).getListD().indexOf(d);
			if(index !=-1) {
				listaUtenti.get(nid).getListD().get(index).putU(Other);
			}
			else throw new DatoNonTrovatoException();
		}else throw new UtenteNonTrovatoException();
	} 
	
   
	public Iterator<E> getIterator(String Owner, String passw)throws UtenteNonTrovatoException{
		if(Owner == null || passw == null) {throw new NullPointerException();}
		String nid =Owner;
		if(listaUtenti.containsKey(nid)) {
			IteratorDati<E> it =   new IteratorDati<E>( listaUtenti.get(nid).getListD());
			return it.iterator();
		}
		else throw new UtenteNonTrovatoException();

	}
}  
  


