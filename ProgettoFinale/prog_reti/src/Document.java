import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Document implements Serializable {

	private static final long serialVersionUID = 1L;

	int numSezioni;
	String creatore;
	ArrayList<String> collaboratori;
	Sezione sezioni[];
	//chat apposita

	public Document( int numSezioni, String creatore) {
		this.numSezioni=numSezioni;
		sezioni= new Sezione[this.numSezioni];
		for(int i = 0 ; i< this.numSezioni ;i++) {
			sezioni[i] = new Sezione("i");
		}
		this.creatore=creatore;
		collaboratori = new ArrayList<String>();
	}

	public File getSezione(int numSez) {
		return sezioni[numSez].getSez();
	}
}