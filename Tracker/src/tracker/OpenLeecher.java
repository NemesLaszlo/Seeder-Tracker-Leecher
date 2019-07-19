package tracker;

import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class OpenLeecher {
    
    public static void main(String[] args) {
        try {
            int port = 1234;
            String name = "Best tracker";
            Registry registry = LocateRegistry.getRegistry(port);
            IOpenTracker comp = (IOpenTracker) registry.lookup(name);
            
            if( args.length == 0 ) {
                ArrayList<String> result = (ArrayList<String>) comp.fetchFileList();
                for( String x : result ) {
                    System.out.println(x.toString());
                } 
            } else {
                String fileName = args[0]; //k1
                int vartPort = 0;

                try (
                    Socket s = new Socket("localhost", 55555);
                    Scanner sc = new Scanner(s.getInputStream());
                    PrintWriter pw = new PrintWriter(s.getOutputStream());
                    ) 
                {
                    pw.println("leech");
                    pw.flush();
                    pw.println(fileName);
                    pw.flush();
                    String valaszTracker = sc.nextLine();
                    System.out.println("TrackerValasza: " + valaszTracker);
                    vartPort = Integer.parseInt(valaszTracker); 
                }

                try (
                    Socket s = new Socket("localhost", vartPort);
                    Scanner sc = new Scanner(s.getInputStream());
                    PrintWriter pw = new PrintWriter(s.getOutputStream());
                    ) 
                {
                    pw.println(fileName);
                    pw.flush();
                    String seederValasza = sc.nextLine();
                    System.out.println("SeederValasza a file: " + seederValasza);

                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
