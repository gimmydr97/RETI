
public class Calculator implements Runnable {
	private double number = 0.0f;//il valore che voglio costruire
	private double accuracy;//l'accuratezza che sara' quella data in input
	private int i=1;//serve per l'if 
	private int j=1;//il denominatore della frazione 
	private int k =1;
	public Calculator(double accuracy) {//il costruttore che riceve come parametro solo l'accuratezza
		this.accuracy = accuracy;
	}  
	public void  run() {//il metodo run da implementare dato che si implementa runnable
		while((Thread.interrupted() == false) && (Math.abs(number - Math.PI)) >= accuracy) {//testo se il thread che ho creato ha 														ricevuto una richiesta di 														interruzione attraverso il metodo 														interrupted che restituira' true 														nel caso in cui ha appunto ricevuto 														un interruzione. testo poi se la 														differenza tra il valore da me 														costruito e il pigreco e' < di 													        accuracy 
			if(i%2 == 1)				//if che serve ad alternare i valori con + e quelli con -
				number = number + ((double)4/j);
			else 
				number = number - ((double)4/j);
			j=j+2;
			i=i+1;
			
			System.out.println("il valore con" +k+" del pigreco e': "+number);
			k++;
		}
	System.out.println("il valore del pigreco e': "+number);
	System.out.println(k);
	}	
}
