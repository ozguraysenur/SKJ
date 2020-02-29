import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Client {
      static List<Integer> portsss =new ArrayList<>();
    public  void something2() throws IOException {
       // Server server=new Server();
       //portsss= server.ports;
        String adressIP = "127.0.0.1";
        InetAddress IPAddress = InetAddress.getByName(adressIP);



        //DatagramPacket query = new DatagramPacket(queryBuff, queryBuff.length, address, port);

        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(10000);

        byte[] queryBuff = "knock knock!".getBytes();
        //int [] a ={55555,44444};
        //System.out.println(portsss.get(1));

        for (int i = 0; i < portsss.size(); i++) {
            socket.send(new DatagramPacket(queryBuff,queryBuff.length,IPAddress,portsss.get(i)));
            System.out.println("on port : " +portsss.get(i));

        }
        byte[] buff=null;
        for (int i = 0; i < portsss.size(); i++) {

         buff = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        socket.receive(packet);
        String resp = new String(packet.getData(),0, packet.getLength());
        System.out.println("FROM SERVER (random port): " + resp);
        }
       // Integer fn = Integer.parseInt(resp);


        DatagramPacket filename = new DatagramPacket(buff, buff.length);
        socket.receive(filename);
        String str2 = new String(filename.getData(), 0, filename.getLength()).trim();
        String[] split = str2.split("/");
        System.out.println("file name and size : " + split[0] + " " + split[1]);


        int size = Integer.parseInt(split[1]);
        buff = new byte[size];
        DatagramPacket content = new DatagramPacket(buff, buff.length);
        socket.receive(content);
        String resp2 = new String(content.getData(),0, content.getLength());
        System.out.println( "content :" +resp2);



        File file = new File(split[0]);
        Files.write(Paths.get(split[0]), content.getData());
        //System.out.println(filedata.getData().toString());
        socket.close();

        System.out.println("FILES SAVED SUCCESFULLY");

    }

    public static void main(String[] args) {
        Client client =new Client();
        Server server  =new Server();
        System.out.println("ENTER THE SAME PORT NUMBERS !");
        portsss=server.portnumbers();
        try {

           client.something2();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
