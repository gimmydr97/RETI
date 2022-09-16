import java.util.Iterator;

public class Main {
	public static void  main(String[] args) {
		
		//per controllare entrambe le implementazioni sostituire MySecureDataConteinerTM<String>
		//con MySecureDataConteinerH<String>
		MySecureDataConteinerH<String> contenitore = new MySecureDataConteinerH<String>() ;
		String name = "carlo";
		String passw = "abcd";
		String data = "casa";
		String data1 = "ha";
		String data2 = "a";
		
		//createUser
		try {	
				contenitore.createUser(name,passw);
				contenitore.createUser("antonino.sant", "1234");
				contenitore.createUser(name,passw);
				
		}catch(IllegalArgumentException e) {System.out.println("questo utente e' stato gia' creato ");}
		
		//put1
				try {
					
					
					if(contenitore.put(name,passw, data)) {System.out.println("Il dato : ' "+ data + " ' e' stato inserito con successo ");}
					contenitore.put(name,passw, data);
					
				}catch(IllegalArgumentException e) {System.out.println("questo dato e' stato gia inserito");}
				 catch(UtenteNonTrovatoException e1) {}
		//put2		
				try {
					
					
					if(contenitore.put(name,passw,data1)) {System.out.println("Il dato : ' "+ data1 + " ' e' stato inserito con successo ");}
					if(contenitore.put(name,passw,data2)) {System.out.println("Il dato : ' "+ data2 + " ' e' stato inserito con successo ");}
					contenitore.put("alessandro","lafarciola", "casa");
					
				}catch(UtenteNonTrovatoException e) {System.out.println("questo utente non e' stato trovato nella struttura");}
				 
		//getSize
		
		try {	
				
				System.out.println("il numero di dati dell' utente : " + name + " e': " + contenitore.getSize(name,passw));
				System.out.println(contenitore.getSize("carl.leo","abcd"));
				
		}catch(UtenteNonTrovatoException e) {System.out.println("questo utente non e' stato trovato nella struttura");}
		
		//get1
		
		try {
			System.out.println("il dato : ' " + contenitore.get(name, passw, data) + " ' e' stato trovato nella struttura relativa all'utente inserito");
			contenitore.get("alessandro","lafarciola", data);
			
		}catch(DatoNonTrovatoException e) {}
	     catch(UtenteNonTrovatoException e1) {System.out.println("questo utente non e' stato trovato nella struttura");}
		 
		//get2
		
		try {
		
			contenitore.get(name,passw, "corsa");
			
		}catch(DatoNonTrovatoException e) {System.out.println("il dato cercato non e' stato trovato nella struttura relativa all'utente inserito");}
	     catch(UtenteNonTrovatoException e1) {}
		
		//copy1
		
		try {
					
			contenitore.copy(name, passw, data);
			contenitore.copy("alessandro","lafarciola", data);
		
		}catch(DatoNonTrovatoException e) {}
	     catch(UtenteNonTrovatoException e1) {System.out.println("questo utente non e' stato trovato nella struttura");}
				
		//copy2
				
		try {
			contenitore.copy(name, passw, data1);
			contenitore.copy(name, passw, data2);
			contenitore.copy(name, passw, "corsa");
					
		}catch(DatoNonTrovatoException e) {System.out.println("il dato non puo' essere copiato perche' non e' stato trovato nella struttura");}
	     catch(UtenteNonTrovatoException e1) {}
		
		//remove1
		
		try {
			
			System.out.println(" e' stato rimosso il dato : ' " + contenitore.remove(name,passw, data) + " '" );
			System.out.println(" e' stato rimosso il dato : ' " + contenitore.remove(name,passw, data) + " '" );
			contenitore.remove(name,passw, data);
			
		}catch(DatoNonTrovatoException e) {System.out.println("il dato che si tenta di rimuovere non e' stato trovato nella struttura relativa all'utente inserito");}
	     catch(UtenteNonTrovatoException e1) {}
		
		//remove2
		
		try {
			
			contenitore.remove("alessandro","lafarciola", data);
			
		}catch(DatoNonTrovatoException e) {}
	     catch(UtenteNonTrovatoException e1) {System.out.println("questo utente non e' stato trovato nella struttura");}
		
		//share1
		
		try {
			contenitore.putUAmmesso(name, passw,"antonino.sant", data1);
			contenitore.share(name, passw, "antonino.sant", data1);
			contenitore.share(name, passw, "alessandro", data1);
			
		}catch(DatoNonTrovatoException e) {}
	     catch(UtenteNonTrovatoException e1) {}
		 catch(UtenteNonAmmessoException e2) {System.out.println("l'utente con cui si cerca di condividere il dato non e' ammesso");}
		
		//share2
		
		try {
			contenitore.share(name, passw, "antonino.sant", "corsa");
			
		}catch(DatoNonTrovatoException e) {System.out.println("Il dato da condividere non e' stato trovato");}
	     catch(UtenteNonTrovatoException e1) {}
		 catch(UtenteNonAmmessoException e2) {}
		
		//share3
		
		try {
			contenitore.share("alessandro", "lafarciola", "antonino.sant", "corsa");
			
		}catch(DatoNonTrovatoException e) {}
	     catch(UtenteNonTrovatoException e1) {System.out.println("l'utente cercato non e' stato trovato nella struttura");}
		 catch(UtenteNonAmmessoException e2) {}
		
		//share 4
		
		try {
			contenitore.putUAmmesso(name, passw,"alessandro", data1);
			contenitore.share(name, passw, "alessandro", data1);
			
		}catch(DatoNonTrovatoException e) {}
	     catch(UtenteNonTrovatoException e1) {System.out.println("l'utente con cui si cerca di condividere il dato non e' stato trovato nella struttura");}
		 catch(UtenteNonAmmessoException e2) {}
		
		//getIterator1
		
		try {
			 Iterator<String> it1 =contenitore.getIterator(name, passw);
			 System.out.println("la lista dei dati relativa all' utente ' " + name + " ' e' :");
			 while(it1.hasNext()) {
				 System.out.println(it1.next());
			 }
			 @SuppressWarnings("unused")
			Iterator<String> it2 =contenitore.getIterator("alessandro", "lafarciola");
			
		}catch(UtenteNonTrovatoException e) {System.out.println("l'utente cercato non e' stato trovato nella struttura ");}
		
		//getIterator2
		
		try {
			 Iterator<String> it =contenitore.getIterator(name, passw);
			 it.remove();
			
		}catch(UtenteNonTrovatoException e) {}
		 catch(UnsupportedOperationException e) {System.out.println("l' operazione di remove non e' supportata");}
		
	}
}
