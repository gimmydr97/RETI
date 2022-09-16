import java.util.ArrayList;

public class dato<E> {
	//AF:<dat , n ,[listaU.get(0),......,listaU.get(k-1)] >
	// dove k = ListaU().size()
	 //IR : dat != null && n != null && listaU!= null
	 //	for all i. 0 <= i < listaU.size() ==> listaU.get(i) != null
	 // for all i,j. 0 <= i < j < listaU.size ==> listaU.get(i)!= listaU.get(j) 
	
	private E dat ;
	private int n;
	private ArrayList<String> listaU;
	
	public dato(E dat) {
		this.dat = dat;
		n = 1;
		listaU = new ArrayList<String>();
	}
	public E getD() {
		return this.dat;
	}
	public int getNC() {
		return n;
	}
	public void decNC() {
		n =n-1;
	}
	public void incNC() {
		n =n+1;
	}
	public ArrayList<String> getListaU(){
		return listaU;
	}
	public void putU(String ide) {
		if(ide !=null) {
			listaU.add(ide);
		}
	}
	@Override
	public boolean equals(Object e) {
		if(e instanceof dato) {
		@SuppressWarnings("unchecked")
		dato<E> d = (dato<E>) e;
		if(d.getD() == dat) {return true;}
		else return false;
		}
		else return false;
	}
}
