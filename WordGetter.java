import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WordGetter extends Remote{
    void putWord(String word) throws RemoteException;
}