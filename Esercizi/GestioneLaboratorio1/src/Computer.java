import java.util.concurrent.locks.*;

public class Computer {
	private int num;
	private boolean occupato;
	
	public Computer(int id) {
		this.num = id;
		occupato = false;
		
	}
	
	public void utilizza(String user,Condition c, Lock l) {
		
		l.lock();
		occupato = true;
		System.out.println(user + "sta usando il computer " +num);
		try {
			Thread.sleep((long)Math.random()*100);
		}catch(InterruptedException e) {
			e.printStackTrace();
			}
		System.out.println(user + " ha finito");
		occupato = false;
		c.signalAll();
		l.unlock();
		
	}
	
	public boolean isFree() {return !occupato;}
	
	public int getNumber() {return this.num;}
	
	public void occupa(boolean b) {this.occupato = b;}
	
}
