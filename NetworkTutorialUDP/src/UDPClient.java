import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPClient {

    public static void main(String[] args) throws IOException {
        InetAddress address = InetAddress.getLocalHost();
        int port = 12345;

        String mesaj= " hiii !";

        byte[] queryBuff = mesaj.getBytes();
        DatagramPacket query = new DatagramPacket(queryBuff, queryBuff.length, address, port);

        DatagramSocket socket = new DatagramSocket();

        socket.send(query);

        System.out.println(mesaj);

        byte[] buff = new byte[UDP.MAX_DATAGRAM_SIZE];
        DatagramPacket packet = new DatagramPacket(buff, buff.length);

        socket.receive(packet);

        String str = new String(packet.getData(), 0, packet.getLength());

        System.out.println(str);

        socket.close();
    }

}