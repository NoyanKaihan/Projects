package server;

import commands.SaveAbstractCommand;
import connection.Request;
import connection.Response;
import managers.CollectionManager;
import managers.CommandManager;
import managers.FileManager;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;
import java.util.Map;

public class DatagramManager {
    private final int port;
    private CollectionManager collectionManager;
    private FileManager fileManager;
    private CommandManager commandManager;
    private DatagramSocket ds;

    public DatagramManager(int port, CollectionManager collectionManager, FileManager fileManager, CommandManager commandManager) {
        this.port = port;
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
        this.commandManager = commandManager;
    }

    private void manage() {
        try {

            RequestReceiver requestReceiver = new RequestReceiver(ds);
            Map.Entry<SocketAddress, Request> pair = requestReceiver.receive();
            SocketAddress address = pair.getKey();
            Request request = pair.getValue();

            RequestHandler handler = new RequestHandler(request, commandManager);
            Response response = handler.handle();

            ResponseSender sender = new ResponseSender(ds, response, address);
            sender.send();
            SaveAbstractCommand saveAbstractCommand = new SaveAbstractCommand(fileManager, collectionManager);
            System.out.println(saveAbstractCommand.execute(""));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred while I/O.\n" + e.getMessage());
        }
    }

    public void runServer() {
        try {
            ds = new DatagramSocket(port);
        }catch (SocketException e){
            System.out.println("Error occurred while I/O.\n"+e.getMessage());
        }
        while (true) {
            manage();
        }
    }
}
