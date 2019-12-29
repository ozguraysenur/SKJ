import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;

public class UDPServer {
    public static void main(String[] args) throws Exception{
        //simdi clinettan gelen datayi aliyrouz
        System.out.println("server started..");

        DatagramSocket ds =new DatagramSocket(9999); //  burda port number belirtmeliyiz
        //cunku clinettan gelen data bu porta gitmeli

        // accepting data
        byte[] bb =new byte[1024];
        DatagramPacket dp =new DatagramPacket(bb,bb.length);
        ds.receive(dp); //suan dp icinde clienttan gelen data var koyduk.
        String str =new String(dp.getData());
        String echo ="echo : " + str;



        //sending data to client
        byte[] gonder =echo.getBytes(); //or str.getbytes
        InetAddress ia =InetAddress.getLocalHost();
        DatagramPacket dp1 =new DatagramPacket(gonder,gonder.length,ia,dp.getPort());
        ds.send(dp1);





        //receiving de sadece byte array  yeter ama sending de ip adres ve port lazim


    }
}
