package tracker;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface IOpenTracker extends Remote {
    
     public Collection<String> fetchFileList() throws RemoteException;
  
}
