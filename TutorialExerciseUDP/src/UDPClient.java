import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class  UDPClient {
    public static void main(String[] args) throws Exception{
        DatagramSocket ds = new DatagramSocket(); //socket hangi serverla connection kurcagini bilmiyor
        //bunu paket bilicek.
        //its accept data in byte array format(paket)
        int i =5;
        String mesagge= "Hiii!";
        byte[] b =mesagge.getBytes();
        //to make ip adres
        InetAddress ia =InetAddress.getLocalHost(); //locsl host
        System.out.println(mesagge);

        //sending data to server
        DatagramPacket packet =new DatagramPacket(b,b.length,ia,9999);
        //1->data which you want to send
        //2->data lenght
        //3->ip adres
        //4->port number

        //sending data
        ds.send(packet); //sending data(packet) to the socket

        // after proccesing yani serverdan donunce accept ediyoruz
        byte[] gelecek = new byte[1024]; //bos suanda data yok
        DatagramPacket gelen = new DatagramPacket(gelecek,gelecek.length);

        //receiving data
        ds.receive(gelen); //gelen suanda data yi aldi

        String str =new String(gelen.getData()); //biseye koydu
        //System.out.println("receive : " + str);
        System.out.println(str);

    }
}
