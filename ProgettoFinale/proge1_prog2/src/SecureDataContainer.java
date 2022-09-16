import java.util.Iterator;

public interface SecureDataContainer<E>{
	//Overview:tipo modificabile che rappresenta un contenitore di oggetti di tipo E utilizzato 
	//			per la memorizzazione e  la condivisione sicura di dati 			
	
	//Typical Element:  Id --> <passw_i,(<dato_i , n_copie,[id_0,...,id_j,...,id_k-1]>|0 <= i < n, 0 <= j < k)>
	
	//					dove dato_i e' il dato di tipo E , n_copie e' il nulero di copie del dato e 
	//					la terza componente della tripla e' un insieme di utenti autorizzati
	//					e passw_i e' la password relativa all'utente user
	
	// Crea l’identità un nuovo utente della collezione
	public void createUser(String Id, String passw)throws IllegalArgumentException;
	
	//REQUIRES:Id!=null && passw!=null && non esiste in this un User con Id € this
	//THROWS: se Id == null || passw == null lancia NullPointerException (eccezione presente in java unchecked )
	//		  se esiste User con Id € this lancia IllegalArgumentException (eccezione presente in java unchecked )
	//MODIFIES: this
	//EFFECTS: aggiunge un Usere con user:<Id,passw>  e con insieme dei dati vuoto in this
	
	//Restituisce il numero degli elementi di un utente presenti nella collezione
	public int getSize(String Owner, String passw)throws UtenteNonTrovatoException;
	
	//REQUIRES: Owner!=null && passw!=null && user:<Owner,passw> € this
	//THROWS: se Owner == null || passw === null lancia NullPointerException (eccezione presente in java unchecked )
	//		  se Owner !€ this lancia UtenteNonTrovatoException (eccezione non presente in java chacked)
	//EFFECTS: restituisce il numero di dati presenti (con copie) in this associati a Owner
	
	//Inserisce il valore del dato nella collezione
	//se vengono rispettati i controlli di identita'
	public boolean put(String Owner, String passw, E data)throws UtenteNonTrovatoException, IllegalArgumentException;
	
	//REQUIRES: Owner!=null && passw!=null && data != null &&  Owner € this && data !€ ai dati di Owner
	//THROWS: se Owner == null || passw === null || data == null lancia NullPointerException(eccezione presente in java unchecked )
	//		  se Owner !€ this lancia UtenteNonTrovatoException	(eccezione non presente in java chacked)
	//	      se data € ai dati di Owner lancia IllegalArgumentException(eccezione prevista in java unchacked)
	//MODIFIES: this
	//EFFECTS: inserisce data nell'insieme dei dati di Owner
	//		   e restituisce true
	
	//Ottiene una copia del valore del dato nella collezione
	//se vengono rispettati i controlli di identita'
	public E get(String Owner, String passw, E data)throws DatoNonTrovatoException,UtenteNonTrovatoException;
	
	//REQUIRES:Owner!=null && passw!=null && data != null &&  Owner € this && data appartiene ai dati di Owner
	//THROWS: se Owner == null || passw === null || data == null lancia NullPointerException(eccezione presente in java unchecked )
	//		  se Owner !€ this lancia UtenteNonTrovatoException	(eccezione non presente in java chacked)
	//		  se data non e' presente nei dati di Owner lancia DatoNonTrovatoException(eccezione non presente in java chacked)
	//EFFECTS: restituisce data se presente in this correlato a Owner
	
	
	//Rimuove il dato nella collezione
	//se vengono rispettati i controlli di identita'
	public E remove(String Owner, String passw, E data)throws DatoNonTrovatoException,UtenteNonTrovatoException;
	
	//REQUIRES:Owner!=null && passw!=null && data != null &&  Owner € this && data appartiene ai dati di Owner
	//THROWS: se Owner == null || passw === null || data == null lancia NullPointerException(eccezione presente in java unchecked )
	//		  se Owner !€ this lancia UtenteNonTrovatoException	(eccezione non presente in java chacked)
	//		  se data non e' presente nei dati di Owner lancia DatoNonTrovatoException(eccezione non presente in java chacked)
	//MODIFIES: this
	//EFFECTS: rimuove una copia di data dai dati di Owner
	//		   se e' presente una sola copia di data elimina tutta la tripla associata al dato data 
	//		   restituisce il dato eliminato se l'eliminazione e' andata a buon fine 
	
	//Crea una copia del dato nella collezione 
	//se vengono rispettati i controlli di identita'
	public void copy(String Owner, String passw, E data)throws DatoNonTrovatoException,UtenteNonTrovatoException;
	
	//REQUIRES:Owner!=null && passw!=null && data != null &&  Owner € this && data appartiene ai dati di Owner
	//THROWS: se Owner == null || passw === null || data == null lancia NullPointerException(eccezione presente in java unchecked )
	//		  se Owner !€ this lancia UtenteNonTrovatoException	(eccezione non presente in java chacked)
	//		  se data non e' presente nei dati di Owner lancia DatoNonTrovatoException(eccezione non presente in java checked)
	//MODIFIES: this
	//EFFECTS : incrementa il numero di copie associate al dato data appartenente all'Owner
	
	//Condivide il dato nella collezione con un altro utente 
	//se vengono rispettati i controlli di identita'
	public void share(String Owner, String passw,String Other, E data)throws DatoNonTrovatoException,UtenteNonTrovatoException,UtenteNonAmmessoException;
	
	//REQUIRES:  Owner!=null && passw!=null && data != null && Other!=null && Owner € this && Other € this && Other € alla lista degli utenti autorizzati che possono accedere al dato
	//			 && data appartiene ai dati di user && user1 € lista di utenti che possono accedere a data
	//THROWS: se Owner == null || passw === null || data == null || Other == null lancia NullPointerException(eccezione presente in java unchecked )
	//		  se Owner !€ this || Other !€ this lancia UtenteNonTrovatoException(eccezione non presente in java chacked)
	//		  se data non e' presente nei dati di Owner lancia DatoNonTrovatoException(eccezione non presente in java chacked)
	//		  se Other !€ alla lista degli utenti autorizzati che possono accedere al dato lancia UtenteNonAmmessoException (eccezione non presente in java checked)
	//MODIFIES: this
	//EFFECTS: aggiunge data associato a Owner ai dati associati a Other
	
	//restituisce un iteratore (senza remove)  che genera tutti i dati 
	//dell'utente in ordine arbitrario
	//se vengono rispettati i controlli di identita'
	public Iterator<E> getIterator(String Owner, String passw)throws UtenteNonTrovatoException;
	
	//REQUIRES:this non deve essere modificato finche' il generatore e' in uso && Owner € this && Owner != null && passw != null
	// se Owner == null || passw == null lancia NullPointerException(eccezione presente in java unchecked)
	// se Owner !€ this lancia UtenteNonTrovatoException(eccezione non presente in java checked)
	//EFFECTS: ritorna un iteratore che produrrà tutti i dati di un utenete comprese le copie in ordine arbitrario
	
}
