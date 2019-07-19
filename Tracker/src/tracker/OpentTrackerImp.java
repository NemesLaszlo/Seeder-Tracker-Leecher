package tracker;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OpentTrackerImp implements IOpenTracker{

   private HashMap<String,Integer> adatok;

    public OpentTrackerImp(HashMap<String, Integer> adatok) {
        this.adatok = adatok;
    }
   
    @Override
    public Collection<String> fetchFileList() throws RemoteException {
        ArrayList<String> fileNames = new ArrayList<>();
        synchronized(adatok) {
            for( Map.Entry x : adatok.entrySet() ) {
                fileNames.add( (String) x.getKey());
            }
        }
        return fileNames;
    }
    
}
