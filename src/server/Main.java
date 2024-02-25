package server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Server server = new Server(4545);
            server.serve();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
