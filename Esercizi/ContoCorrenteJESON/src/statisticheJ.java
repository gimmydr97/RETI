
public class statisticheJ {
	private int nBon, nAcc, nBol, nF24, nPB;
	private Object BonLock,ALock,BolLock,FLock,PBLock;
	
	public statisticheJ () {
		BonLock= new Object();
		ALock=	new Object();
		BolLock= new Object();
		FLock= new Object();
		PBLock= new Object();
		
	}
	
	public void setBon() {
		synchronized (BonLock) {nBon++;}
	}
	public void setAcc() {
		synchronized (ALock) {nAcc++;}
	}
	public void setBol() {
		synchronized (BolLock) {nBol++;}
	}
	public void setF() 	 {
		synchronized (FLock) {nF24++;}
	}
	public void setPB() {
		synchronized (PBLock) {nPB++;}
	}
	public int getBon() {
		return nBon;
	}
	public int getAcc() {
		return nAcc;
	}
	public int getBol() {
		return nBol;
	}
	public int getF()   {
		return nF24;
	}
	public int getPB()  {
		return nPB;
	}

}
