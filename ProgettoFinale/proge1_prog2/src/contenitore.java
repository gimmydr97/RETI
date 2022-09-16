import java.util.ArrayList;

public class contenitore<E> {
	//FA: <passw,[listaD.get(0)......listaD.get(n)]>
	// dove n =listaD.size() 
	
	//IR: passw != null && listaD!=null
	//	  for all i. 0 <= i < listaD.size() ==> listaD.get(i) != null
	//	  for all i,j. 0 <= i < j < listaD.size() ==> listaD.get(i) != listaD.get(j)
	
	ArrayList<dato<E>> listaD;
	String passw;
	
	public contenitore(String passw) {
		this.passw = passw;
		this.listaD = new ArrayList<dato<E>>();
	}
	
	public String getPassw() {
		return passw;
	}
	
	public ArrayList<dato<E>> getListD(){
		return listaD;
	}
	
}
