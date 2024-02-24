package server;

import connection.Request;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.Entry;

public class RequestReceiver {
    private static final int BATCH = 1024;

    private DatagramSocket ds;
    private final Map<SocketAddress, byte[]> clientData;

    public RequestReceiver(DatagramSocket ds) {
        this.ds = ds;
        this.clientData = new HashMap<>();

    }

    public Map.Entry<SocketAddress, Request> receive() throws IOException {


        byte[] batch = new byte[BATCH];
        DatagramPacket dp = new DatagramPacket(batch, batch.length);
        while (true) {
//            try {
                ds.receive(dp);
                SocketAddress address = dp.getSocketAddress();
                byte[] value = clientData.getOrDefault(address, new byte[]{});
                ByteArrayOutputStream data = new ByteArrayOutputStream();
                data.write(value);
                data.write(Arrays.copyOf(batch, BATCH - 1));
                clientData.put(address, data.toByteArray());
                if (dp.getData()[BATCH - 1] == (byte) 1) {
                    return receiveRequest(Map.entry(address, clientData.remove(address)));
                }
//            }catch (IOException exception){
//                System.err.println("Error occurred while I/O.\n"+ exception.getMessage());
//            }

        }
    }

    private Entry<SocketAddress, Request> receiveRequest(Map.Entry<SocketAddress, byte[]> pair) {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(pair.getValue());
        try {
            ObjectInputStream objectStream = new ObjectInputStream(byteStream);
            var request = Map.entry(pair.getKey(), (Request) objectStream.readObject());

            System.out.printf("Request [%s] received from \"%s\"\n", request.getValue().getClass().getSimpleName(), request.getKey());
            return request;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Invalid request format!");
        }
        return null;
    }


}
