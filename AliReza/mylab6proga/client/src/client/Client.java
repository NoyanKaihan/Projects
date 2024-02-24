package client;

import clientDataManager.ConsoleInputManager;
import connection.Request;
import connection.Response;
import scriptHandler.ScriptHandler;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

public class Client {
    private static final int BATCH = 1024;
    private int port;
    private SocketAddress socketAddress;
    private DatagramChannel channel;
    private ConsoleInputManager inputDataManager;

    public Client(int port) {
        this.port = port;
        initialize();
        inputDataManager = new ConsoleInputManager();
    }

    private void initialize() {
        try {
            channel = DatagramChannel.open();
            channel.socket().setSoTimeout(3000);
            socketAddress = new InetSocketAddress(InetAddress.getByName("localhost"), port);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    private void send(Request request) {
        try {

            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            ObjectOutputStream objOutput2 = new ObjectOutputStream(byteArrayOutputStream2);
            objOutput2.writeObject(request);
            byte[] data = byteArrayOutputStream2.toByteArray();
            int n = (int) Math.ceil((double) data.length / (BATCH - 1));
            for (int i = 0; i < n; i++) {
                byte[] batch = new byte[BATCH];
                System.arraycopy(data, i * (BATCH - 1), batch, 0, Math.min(data.length - i * (BATCH - 1), BATCH - 1));

                batch[BATCH - 1] = (byte) ((i + 1 == n) ? 1 : 0);
                channel.send(ByteBuffer.wrap(batch), socketAddress);

//            logger.info("Batch {}/{} has been sent", i + 1, n);
            }
//            System.out.println("request = " + request);
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
//            ObjectOutputStream objOutput = new ObjectOutputStream(byteArrayOutputStream);
//            objOutput.writeObject(request);
//            DatagramPacket requestPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(), socketAddress);
//            //System.out.println("before sending");
//            socket.send(requestPacket);
            //System.out.println("after sending");
            byteArrayOutputStream2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printResponse(byte[] data) {
        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
            ObjectInputStream objectStream = new ObjectInputStream(byteStream);
            Response response = (Response) objectStream.readObject();
            System.out.println(response.getMessage() + "\n\n");
        } catch (Exception exception) {
            System.err.print("\rInvalid response format from server");
            System.err.println();
        }
    }

    private void receive() {
        ByteBuffer buffer = ByteBuffer.allocate(BATCH);
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        SocketAddress address;
        while (true) {
            try {
                address = channel.receive(buffer);
                data.write(Arrays.copyOf(buffer.array(), BATCH - 1));
                if (buffer.array()[BATCH - 1] == (byte) 1) {
                    printResponse(data.toByteArray());
                    break;
                }
                address = null;
                buffer.clear();
            } catch (IOException exception) {
                System.err.println("trying to reconnect to server :");
                for (int j = 15; j > 0; j = j - 2) {
                    System.err.print("\r" + "\uD83D\uDFE5".repeat(j - 1));
                    receive();
                    break;
                }
                System.err.print("\rServer is unavailable".toUpperCase());
            }
        }
    }

//
//
//        socket.setSoTimeout(1000);
//    byte[] b = new byte[1];
//    ByteBuffer buf2 = ByteBuffer.allocate(1);
//    DatagramPacket receivePacket2 = new DatagramPacket(buf2.array(), buf2.array().length);
//        socket.receive(receivePacket2);
//    ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(buf2.array());
//    String a = "";
//    int c;
//        while((c =byteArrayInputStream2.read())!=-1)
//
//    {
//        a += (char) c;
//    }
//        System.out.println(a);
//
//
//        System.out.println("before receiving");
//        new
//
//    DatagramSocket().
//
//    receive(new DatagramPacket(buf2.array(), 1));
//        System.out.println("after receiving");
//    ByteArrayInputStream byteArrayInputStream1 = new ByteArrayInputStream(b);
//    int count = byteArrayInputStream1.read();
//        System.out.println("count = "+count);
//        for(
//    int i = 0;
//    i<count;i++)
//
//    {
//        ByteBuffer buf = ByteBuffer.allocate(4096);
//        DatagramPacket receivePacket = new DatagramPacket(buf.array(), buf.array().length);
//        //System.out.println("before receiving");
//        try {
//            socket.receive(receivePacket);
//        } catch (SocketTimeoutException exception) {
//            System.err.println("trying to reconnect to server :");
//            for (int j = 15; j > 0; j = j - 2) {
//                System.err.print("\r" + "\uD83D\uDFE5".repeat(j - 1));
//                try {
//                    socket.receive(receivePacket);
//                    break;
//                } catch (SocketTimeoutException exception1) {
//
//                }
//            }
//            System.err.print("\rServer is unavailable".toUpperCase());
//        }
//        //
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf.array());
//        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//        Response response = (Response) objectInputStream.readObject();
//        System.out.println(response.getMessage() + "\n");
//    }
//}catch(
//        Exception exception)
//
//        {
//        System.out.println("something went wrong!");
//        }
//
//        }

    public void run() {
        while (true) {
            System.out.print("Enter a command (command \"help\" to get help) >> ");
            Request request = inputDataManager.readCommand();
            if (request.getCommand().equals("exit")) break;
            if (request.getCommand().equals("execute_script")) {
                ScriptHandler sh = new ScriptHandler(request.getArgument());
                request = sh.handle(request);
            }
            send(request);
            receive();
        }
    }

}
