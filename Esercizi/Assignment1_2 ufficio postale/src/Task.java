
public class Task implements Runnable {
	private int name;
	
	public Task(int name){//costruttore del task
				this.name=name;
	}
	
	public void run(){

		try {
			 long Duration=(long)(Math.random()*1000);//duration prende un valore casuale
			 System.out.printf("%s: Persona %d: tempo trascorso allo sportello: %d secondi\n",Thread.currentThread().getName(),name,Duration);
			 Thread.sleep(Duration);//utilizzo una sleep lunga duration per far si che ogni task differente impieghi un tempo diverso per eseguire la run
		}
		catch(InterruptedException e) {e.printStackTrace();}
	}
	
	
	public int getName() {return name;}//metodo che ritorna il nome del task
}
