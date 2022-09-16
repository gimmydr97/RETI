

public class SessionData {
	
	private String[] sessione ;
	private int i;
   
	SessionData() {
		
		sessione = new String[5];
		i=0;
	}
	
	public boolean exsistsILibero() {
		
		if(i == 5)
			 return false ;
		else return true;
	}
	
	public void put(String SpeakerName) {
		
		sessione[i] = SpeakerName;
		i ++;
	}
	
}
