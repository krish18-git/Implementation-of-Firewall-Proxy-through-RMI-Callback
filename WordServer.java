import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WordServer extends Remote{
    void addWordGetter(WordGetter addWordGetter) throws RemoteException;
    void removeWordGetter(WordGetter addWordGetter) throws RemoteException;
}