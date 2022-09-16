import java.io.Serializable;
import java.util.*;

public class contoCorrente implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nomeC;
	private Collection<movimento> listaM;
	
	public contoCorrente(String n) {
		nomeC = n;
		listaM = new ArrayList<movimento>();
	}
	
	public void addMovimento(movimento m) {
		listaM.add(m);
	}
	
	public  ArrayList<movimento> getMovList(){
		
		 return (ArrayList<movimento>)listaM;
		 	
	}
	public int getLim() {
		int tmp = listaM.size();
		return tmp;
	}
	public String getName() {
		return nomeC;
	}
	
	
	
}
