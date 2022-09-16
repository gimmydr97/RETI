import java.nio.file.*;
import java.io.IOException;

public class Sezione {
	
	private Path sez;
	private String stato;
	
	public Sezione(String nameDoc ,String n) throws IOException {
		sez= Paths.get(nameDoc+"/"+n);
		Files.createFile(sez);
		stato = "libero" ;
	}
	
	public String getStato() {return stato;}
	
	public Path getPathSez() {return sez;}
	
	public void ChangeStato(String nome) {
		if(stato.compareTo("libero") == 0) { stato = "occupato" + nome; }
		else stato = "libero";
	}
}

