import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class UDPServer {
    private DatagramSocket server;

    public UDPServer() throws SocketException  {
        initializeServer();
    }

    private void initializeServer() throws SocketException {
        server = new DatagramSocket(12345);
        System.out.println("Server listens on: " + server.getLocalPort());
    }

    private void service() throws IOException {
        byte[] buff = new byte[UDP.MAX_DATAGRAM_SIZE];
        final DatagramPacket datagram = new DatagramPacket(buff, buff.length);

        server.receive(datagram); //blocking galiba

        new Thread(() ->  {
            //int n = Integer.parseInt(new String(datagram.getData(), 0, datagram.getLength()));
            String str =new String(datagram.getData());
            String echo = "echo : " + str;
            //System.out.println("I've got " + n);
           // int nFact = factorial(n);
            byte[] respBuff = echo.getBytes();
            int clientPort = datagram.getPort();
            InetAddress clientAddress = datagram.getAddress();
            DatagramPacket resp = new DatagramPacket(respBuff, respBuff.length, clientAddress, clientPort);
            try {
                server.send(resp);
                //System.out.println("I've sent " + nFact);
            } catch (IOException e) {
                // do nothing
            }
        }).start();
    } //thread icinde ve dongude olunca client bitiyor fakat serveer calismaya yani client beklemye devam ediyor

    public void listen() {
        while(true) {
            try {
                service();
            } catch (IOException e) {
                // do nothing
            }
        }
    }

    public static void main(String[] args) {
        try {
            new UDPServer().listen();
        } catch (SocketException e) {
            System.out.println("Could not set up the server");
        }
    }
    static int factorial(int n){
        if (n == 0)
            return 1;
        else
            return(n * factorial(n-1));
    }

}