package seederclass;

import java.io.*;
import java.util.*;
import java.net.*;

public class SeederClass {

    public static void main(String[] args) throws Exception {
        int PORT = Integer.parseInt(args[0]);
        HashMap<String,String> fileok = new HashMap<>();
        for(int i = 1; i < args.length - 1; i += 2) {
            fileok.put(args[i], args[i+1]);
        }
        for(Map.Entry x : fileok.entrySet()) {
            System.out.println(x.getKey() + " " + x.getValue());
        }
        
        try (
            Socket s = new Socket("localhost", 55555);
            Scanner sc = new Scanner(s.getInputStream());
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            ) 
        {
            pw.println("seed");
            pw.flush();
            pw.println(Integer.toString(PORT));
            pw.flush();
            String uzenet = "";
            for(Map.Entry x : fileok.entrySet()) {
                uzenet += x.getKey() + " ";
            }
            pw.println(uzenet);
            pw.flush();
        }

        try (
                ServerSocket ss = new ServerSocket(PORT);
                Socket s = ss.accept();
                Scanner sc = new Scanner(s.getInputStream());
                PrintWriter pw = new PrintWriter(s.getOutputStream());
            ) 
        {
            String file = sc.nextLine();
            String filekuldesre = "";
            for(Map.Entry x : fileok.entrySet()) {
                if( x.getKey().equals(file) ) {
                    filekuldesre += (String) x.getValue();
                }
            }
            pw.println(filekuldesre);
            pw.flush();
            
        }
    }
    
}
