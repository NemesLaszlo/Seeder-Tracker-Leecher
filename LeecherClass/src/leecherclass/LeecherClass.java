package leecherclass;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class LeecherClass {

    public static void main(String[] args) throws IOException {
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
    
}
