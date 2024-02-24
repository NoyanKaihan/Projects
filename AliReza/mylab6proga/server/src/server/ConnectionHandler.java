package server;

import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

public class ConnectionHandler {
    private DatagramChannel channel;
    private int port;
    private InetSocketAddress address;
    public ConnectionHandler(int port){
        this.port = port;
        initialize();
    }
    private void initialize (){
        try{
            address = new InetSocketAddress(port);
            channel = DatagramChannel.open();
            channel.bind(address);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public DatagramChannel getChannel(){
        return channel;
    }

}
