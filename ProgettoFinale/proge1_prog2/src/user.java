
public class user{
	private	String ide;
	private	String passw;
	
	public user(String ide, String passw ) {
		this.ide = ide;
		this.passw = passw;
	}
	
	public String getId() {
		 String tmp = this.ide;
		return tmp;
	}
	
	public String getPassw() {
		String tmp = this.passw;
		return tmp;
	}
	
	@Override
	public boolean  equals(Object u) {
		if(u instanceof user) {
			user obj = (user) u;
			if(ide == obj.getId() )
				return true ;
			else return false;
		}
		else return false;
	}
	@Override
	public int hashCode() {
		return this.ide.hashCode();
	}
}
