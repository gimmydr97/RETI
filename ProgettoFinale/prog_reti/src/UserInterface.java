import java.rmi.Remote;
import java.rmi.RemoteException;
public interface UserInterface extends Remote  {

   String register(String nome, String password)throws RemoteException;
}
