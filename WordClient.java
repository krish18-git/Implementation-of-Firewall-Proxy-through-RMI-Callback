import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class WordClient extends UnicastRemoteObject implements WordGetter
{
    Firewall fw;
    int count;
    protected WordClient() throws RemoteException
    {
        count=0;
        fw= new Firewall();
    }

    public static void main(String[] args)
    {
        try
        {
            //Lookup for the service
            String url = "rmi://localhost:1099/Word";
            
            Remote lRemote = Naming.lookup(url);
            WordServer lRemoteServer = (WordServer) lRemote;

            WordClient lWordClient = new WordClient();
            lRemoteServer.addWordGetter(lWordClient);
        }
        catch (Exception aInE)
        {
            System.out.println(aInE);
        }
    }

    public void putWord(String word) throws RemoteException
    {
        word=fw.checkWord(word);
        count+=1;
        if(count==15){
            System.exit(0);
        }
        System.out.println("New Word : " + word);
    }
}
