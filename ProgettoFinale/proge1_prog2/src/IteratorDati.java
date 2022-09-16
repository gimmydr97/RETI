import java.util.ArrayList;
import java.util.Iterator;

public class IteratorDati<E> implements Iterable<E>{
	//FA: [listaD_0,.....listaD_k-1]
	// dove k =  sommatoria (listaD.get(i).getNC())
	// dove i appartiene a [0 , listaD.size()]
	//IR: listaD!= null
	// for all i. 0 <= i < k ==> listaD.get(i)!= null
	 private ArrayList<dato<E>> listaD;
	 
	public IteratorDati(ArrayList<dato<E>>listaD){
		this.listaD = listaD;
	}
	public Iterator<E> iterator(){
			return (Iterator<E>)  new DatGen<E>();
	}
	
	 @SuppressWarnings("hiding")
	private class DatGen<E> implements Iterator<E>{
		  
		   private int  i; //non aumenta per le copie
		   private int j;//utilizzato per restituire le copie
		   DatGen(){ 
			   i=0;
			   j=1;
		   }
		   
		   public boolean hasNext() {
			  
			 if( i < listaD.size()) {
				 return true ;
			 }
			 else return false;
		 }
		  
		 @SuppressWarnings("unchecked")
		public E next() {
			
			int nctemp = listaD.get(i).getNC();
			if(j < nctemp) {
				j++;
				return (E) listaD.get(i).getD();
			}
			else {
				i++;
				j = 1;
				return (E) listaD.get(i-1).getD();
			}

			
			 
		 }
		 
		 public void remove() {throw new UnsupportedOperationException();}
		   
	  } 
}
