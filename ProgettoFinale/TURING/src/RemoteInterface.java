
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;



public interface RemoteInterface extends Remote{
	
	String register (String name,String password) throws RemoteException;
	int getNumber() throws RemoteException;
	String login(String name , String password)throws RemoteException;
	String create(String name , String nSez,String nomeC )throws RemoteException, IOException;
	String share(String doc ,String  nameCollab ,String  nomeC)throws RemoteException;
	String msgInvite(String name)throws RemoteException;
	String show(String doc, String sez, String nome)throws RemoteException, IOException;
    String list(String name)throws RemoteException;
    String edit(String doc, String sez, String nome)throws RemoteException, IOException;
    String end_edit(String doc, String sez, String nome, String contenutofile)throws RemoteException, IOException;
	String exists(String d) throws RemoteException;
    String putChat(String d) throws RemoteException;
	String removeAss(String d,String indM)throws RemoteException;
	String getCollegati(String namedoc)throws RemoteException;
	void   decCollegati(String namedoc)throws RemoteException;
	void incCollegati(String namedoc)throws RemoteException;
}
