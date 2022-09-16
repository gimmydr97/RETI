

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Documento {
	
	private String name;
	private String creatore;
	private int nSez;
	private Path pathdir;
	private ArrayList <Sezione> doc;
	private ArrayList<String> collab;
	
	public Documento(String name ,String nSez, String creatore) throws IOException {
		this.name = name;
		this.nSez =Integer.parseInt(nSez);
		this.creatore = creatore;
		this.collab = new ArrayList<String>();
		this.doc = new ArrayList<Sezione>(this.nSez);
		this.pathdir = Paths.get(name);
		Files.createDirectory(pathdir);
		for (int n = 1; n <= this.nSez ; n++) { doc.add(new Sezione(name,String.valueOf(n))); }
		
	}
	
	public String getName(){ return  new String(name); }

	public int  getNSez() { int n = nSez; return n; }
	
	public String getCreatore() { return new String(creatore); } 
	
	//restituisce una sezione nel caso sia presente nel documento
	public Sezione getSez(int n) { 
		if(n <= nSez )return doc.get(n-1);
		else return null;
		}
	
	public ArrayList<String> getCollabList(){ return collab; }
	
	public boolean containsCollab(String name) { return collab.contains(name); }
	
	public void addCollab(String name ) { collab.add(name); }
	

	

}
