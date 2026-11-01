package threadPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Threadd extends Thread{
    Socket socket;
    int clientNum;

    public Threadd (Socket socket,int clientNum){
        this.socket=socket;
        this.clientNum=clientNum;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("Welcome! You are client n°" + clientNum);

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("[Client n°" + clientNum + "] " + line);
                if (line.equalsIgnoreCase("exit")) break;
                out.println("Server received: " + line);
            }
        } catch (IOException e) {
            System.err.println("Client n°" + clientNum + " error: " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
            System.out.println("Client n°" + clientNum + " disconnected");
        }
    }



}
