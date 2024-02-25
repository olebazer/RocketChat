package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket server;

    Server(int port) throws IOException {
        server = new ServerSocket(port);
    }

    public void serve() throws IOException {
        while (true) {
            Socket client = server.accept();
            ClientHandler handler = new ClientHandler(client);
            Thread clientThread = new Thread(handler);
            clientThread.start();
        }
    }
}
