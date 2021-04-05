import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordServerImpl extends UnicastRemoteObject implements WordServer,Runnable{

    private List<WordGetter> getters = new ArrayList<>();

    private String words[]={"victory","fail","fantastic","defeat","confident","loser","strength",
    "strong","winner","good","excellent","very good","nonsense","idiot","well done","nice","awesome",
    "extraordinary","great","terrific"};
    private int index;
    private int n=words.length;
    private String word;

    
    protected WordServerImpl() throws RemoteException{
        word="victory";
        index=0;
    }

    public void run(){
        Random nRandom = new Random();
        for(int i=0;i<100;)
        {
            
            int duration = nRandom.nextInt() % 10000 + 1000;
            if (duration < 0)
            {
                duration = duration * -1;
            }
            try
            {
                Thread.sleep(duration);
            }
            catch (InterruptedException aInE)
            {
                System.out.println(aInE.getMessage());
            }

            int num = nRandom.nextInt();

            index=Math.abs(num)%n;
            word=words[index];
            notifySendWord(word);
        }
    }

    private void notifySendWord(String sword)
    {
        for (WordGetter getter : getters)
        {
            try
            {
                getter.putWord(sword);
            }
            catch (RemoteException aInE)
            {
                getters.remove(getter);
                if(getters.isEmpty()){
                    System.exit(0);
                }
            }
        }
    }

    public void addWordGetter(WordGetter wordGetter) throws RemoteException
    {
        getters.add(wordGetter);
    }

    public void removeWordGetter(WordGetter wordGetter) throws RemoteException
    {
        getters.remove(wordGetter);
        if(getters.isEmpty()){
            System.exit(0);
        }
    }

    public static void main(String[] args)
    {
        try
        {
            WordServerImpl lServer = new WordServerImpl();
            // Binding the remote object (stub) in the registry
            Registry reg = LocateRegistry.createRegistry(1099);
            String url = "rmi://localhost:1099/Word";
            System.out.println(url);
            Naming.rebind(url, lServer);

            Thread lThread = new Thread(lServer);
            lThread.start();
        }
        catch (Exception aInE)
        {
            System.out.println("Remote error- " + aInE);
        }
    }
}