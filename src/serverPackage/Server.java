package serverPackage;
import threadPackage.Threadd;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {

    public static void main(String[] args) {
        int port = 1234;
        int clientCount = 0;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                clientCount++;
                System.out.println("Client n°" + clientCount + " connected from "
                        + socket.getRemoteSocketAddress());
                new Threadd(socket, clientCount).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }



}
