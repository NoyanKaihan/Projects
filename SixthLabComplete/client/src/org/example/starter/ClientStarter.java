package org.example.starter;

import org.example.client.Client;

public abstract class ClientStarter {
    private final static String PORT = System.getenv("port");
    private static Client client;
    private ClientStarter(){}
    public static void start(){
        int port = Integer.parseInt(PORT);
        client = new Client("localhost",port);
        client.run();
    }
}
