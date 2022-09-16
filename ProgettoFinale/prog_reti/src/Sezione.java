import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Sezione {
	String numS;
    File sezione;
    Lock loocksezione;

    public Sezione (String numS){
    	this.numS =numS;
    	sezione = new File(this.numS);
    	loocksezione = new ReentrantLock();
    	}
    public File getSez() {
    	return sezione;
    }

}
