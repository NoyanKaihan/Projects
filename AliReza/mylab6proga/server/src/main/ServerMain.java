package main;

import json.CollectionDeserialize;
import managers.CollectionManager;
import managers.CommandManager;
import managers.FileManager;
import server.DatagramManager;


public class ServerMain {
    private static final String FILE = System.getenv("file");
    public static void main(String[] args) {
        int port = 1965;
        FileManager fileManager = new FileManager(FILE);
        CollectionManager collectionManager = new CollectionManager();
        CollectionDeserialize collectionDeserialize = new CollectionDeserialize(collectionManager);
        System.out.println(collectionDeserialize.deserialize(fileManager.read())?"Collection successfully deserialized )":"unable to deserialize collection");
        CommandManager commandManager = new CommandManager(collectionManager);
        DatagramManager manager = new DatagramManager(port,collectionManager,fileManager,commandManager);
        System.out.println("SERVER IS RUNNING >>>\n");
        manager.runServer();

    }
}




