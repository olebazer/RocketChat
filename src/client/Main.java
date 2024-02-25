package client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 4545);
        
        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
