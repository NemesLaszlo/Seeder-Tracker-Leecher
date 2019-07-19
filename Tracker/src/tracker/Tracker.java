package tracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Tracker {

        public static void main(String[] args) {
            try {
                Accepter server = new Accepter();
                server.start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
    }
}

class Accepter extends Thread {

    public ServerSocket serverSocket;
    public ArrayList<Seeder> clients;
    public HashMap<String,Integer> seeders;
    
    public Accepter() throws IOException {
        this.serverSocket = new ServerSocket(55555);
        this.clients = new ArrayList<>();
        this.seeders = new HashMap<>();
    }

    @Override
    public void run() {
        System.out.println("Starting accepter");
        while (true) {
            try {
                Socket s = serverSocket.accept();
                Seeder client = new Seeder(s,clients ,seeders);
                client.start();
                synchronized (clients) {
                    clients.add(client);
                }
                System.out.println(s.getRemoteSocketAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

class Seeder extends Thread {

    private Socket s;
    private ArrayList<Seeder> clients;
    private HashMap<String,Integer> seeders;
    private Scanner in;
    private PrintWriter out;

    public Seeder(Socket socket, ArrayList<Seeder> clients, HashMap<String,Integer> seeders) throws IOException {
        this.s = socket;
        this.clients = clients;
        this.seeders = seeders;
        this.in = new Scanner(socket.getInputStream());
        this.out = new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (in.hasNextLine()) {
                String[] cmd = in.nextLine().split(" ");
                    switch(cmd[0]) {
                        case "leech":
                            leech();
                            break;
                        case "seed":
                            seed();
                            break;
                        default:
                            System.out.println("Unknown command");
                            break;
                    }
                
            }
        System.out.println("Client disconnected");
        try {
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void close() throws Exception {
        out.close();
        in.close();
        s.close();
    }
    
    public void leech() {
        String input = in.nextLine();
        Integer result = lookupPeerPort(input);
        out.println(Integer.toString(result) );
        out.flush();
        System.out.println("leech OK");
        
    }
    
    public void seed() {
        String port = in.nextLine();
        int portInt = Integer.parseInt(port);
        Integer portForMap = (Integer) portInt;
        String[] input = in.nextLine().split(" ");
        for(int i = 0; i < input.length; ++i) {
            synchronized(seeders) {
                seeders.put(input[i], portForMap);
            }
        }
        synchronized(seeders) {
            System.out.println("Seeders merete: " + seeders.size());
                for(Map.Entry x : seeders.entrySet()) {
                    System.out.println(x.getKey() + " " + x.getValue());
                }
        }
    }
    
    public void storeFileId(String fileId, Integer peerPort) {
        seeders.put(fileId,peerPort);
    }
    
    public Integer lookupPeerPort(String fileId) {
        HashMap<String,Integer> buffer;
        Integer result = null;
        synchronized(seeders) {
            buffer = seeders;
        }
        for( Map.Entry x : buffer.entrySet() ) {
            if( x.getKey().equals(fileId) ) {
                result = (Integer) x.getValue();
            }
        }
        return result;     
    }
    
}
   
