package server;

import connection.Response;

import java.io.ByteArrayOutputStream;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ResponseSender {
    private SocketAddress address;
    private static final int BATCH = 1024;
    private final DatagramSocket datagramSocket;
    private Response response;

    public ResponseSender(DatagramSocket datagramSocket, Response response, SocketAddress address) throws SocketException {
        this.datagramSocket = datagramSocket;
        this.response = response;
        this.address = address;
    }

    public void send() {
        if (address == null) throw new RuntimeException("no client address found");
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(response);
            byte[] data = byteStream.toByteArray();
            int n = (int) Math.ceil((double) data.length / (BATCH - 1));

            for (int i = 0; i < n; i++) {
                byte[] batch = new byte[BATCH];
                System.arraycopy(data, i * (BATCH - 1), batch, 0, Math.min(data.length - i * (BATCH - 1), BATCH - 1));
                batch[BATCH - 1] = (byte) ((i + 1 == n) ? 1 : 0);
                DatagramPacket dp = new DatagramPacket(batch, batch.length, address);
                datagramSocket.send(dp);
                System.out.printf("Batch %s/%s has been sent\n", i + 1, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//            ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream(4096);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream1);
//            objectOutputStream.writeObject(response);
//            byte[] bytes = response.getMessage().getBytes();
//            int l = (bytes.length / 4096) + 1;
////            System.out.println("l = " + new byte[]{(byte) l});
//            System.out.println("before response");
//            byte[] bytes1 = {(byte) l};
//            ByteBuffer buf5 = ByteBuffer.wrap(bytes1);
//            while (buf5.hasRemaining()){
//                System.out.println(buf5.get());
//            }
//            buf5.flip();
//            channel.send(buf5, address);
//            System.out.println("after response");
//            for (int i = 0; i < l; i++) {
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
//                try {
//                    byteArrayOutputStream.write(bytes, i * 4096, (i*4096)+4096);
//                    channel.send(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()), address);
//                } catch (IndexOutOfBoundsException e) {
//                }
//            }
//            //objectOutputStream.writeObject(response);
//            System.out.println("send from " + address.getHostName() + " to -> " + address);
//            //System.out.println(response);
//            ByteBuffer wrapper = ByteBuffer.wrap(byteArrayOutputStream1.toByteArray());
//
//            channel.send(wrapper, address);


    }
}
