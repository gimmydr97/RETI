import java.io.IOException;
import javax.swing.JOptionPane;

public class Invito implements Runnable{
	HelpClient h;
	String utente;
	String msg;
	boolean terminated = false;//booleano utile alla terminazione del thread
	public Invito(HelpClient h, String utente) {
		this.h=h;
		this.utente= utente;
	}
	public void run(){
		
		while(!terminated) {
			
			try {
				if(!terminated) {
					msg = h.connWithServer("msginvite"+"1"+utente+"2");//chiedo se è presente un nuovo invito
					if(msg.compareTo("")!=0) JOptionPane.showMessageDialog(null,msg);//stampo il messaggio di invito
					Thread.sleep(10000);
				}
			} 
			catch (IOException e) {e.printStackTrace();}
			catch (InterruptedException e) { terminated = true;}
				
			
		}
		
	}
}
