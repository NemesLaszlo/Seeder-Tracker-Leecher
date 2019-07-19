package tracker;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class OpenTracker extends Tracker{

    public static void main(String[] args) {
        try {
                Accepter server = new Accepter();
                server.start();
                
                System.out.println("RMI is loading");
                int PORT = 1234;
                try {
                        java.rmi.registry.LocateRegistry.createRegistry(PORT);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }

                try {

                    String name = "Best tracker";
                    IOpenTracker engine = new OpentTrackerImp(server.seeders);
                    IOpenTracker stub =
                            (IOpenTracker) UnicastRemoteObject.exportObject(engine, 0);
                    Registry registry = LocateRegistry.getRegistry(PORT);
                    registry.rebind(name, stub);
                    System.out.println("RMI bound");
                } catch (Exception e) {
                    System.err.println("RMI exception:");
                    e.printStackTrace();
                }
        } catch (Exception ex) {
                ex.printStackTrace();
        } 
    }
}
