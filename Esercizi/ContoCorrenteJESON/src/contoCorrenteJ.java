import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class contoCorrenteJ{

	private String nomeC;
	private ArrayList<movimentoJ> listaM;
	
	public contoCorrenteJ(String n) {
		nomeC = n;
		listaM = new ArrayList<movimentoJ>();
	}
	
	public void addMovimento(movimentoJ m) {
		listaM.add(m);
	}
	
	public  ArrayList<movimentoJ> getMovList(){
		
		 return (ArrayList<movimentoJ>)listaM;
		 	
	}
	public int getLim() {
		int tmp = listaM.size();
		return tmp;
	}
	public String getName() {
		return nomeC;
	}
	public JSONObject getJSON() {
		
		JSONObject ccObj = new JSONObject();
		JSONArray listOfMov = new JSONArray(); 
	
		
		for(int c = 0 ; c< this.getLim();c++) {
			listOfMov.add(listaM.get(c).getCausal());
			
			}
		ccObj.put("nome", nomeC);
		ccObj.put("causali", listOfMov);
		return ccObj;
	}
}
