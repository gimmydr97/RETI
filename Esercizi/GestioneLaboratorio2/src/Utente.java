
public abstract class Utente implements Runnable {
	protected Tutor tutor;
	protected int numberOfAccesses;
	@Override
	public void run() {
		sendAccessRequest();
	}
	public abstract void sendAccessRequest();

}
