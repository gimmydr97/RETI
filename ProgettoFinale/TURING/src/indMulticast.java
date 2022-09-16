
public class indMulticast {
	String indM;
	int nCollegati;
	
	public indMulticast(String indM) {
		this.indM = indM;
		nCollegati = 0;
	}
	
	public String getIndM() {return indM;}
	public int getCollegati() {return nCollegati;}
	public void incCollegati() {nCollegati++;}
	public void decCollegati() {nCollegati--;}
}
