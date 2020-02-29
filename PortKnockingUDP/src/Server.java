import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Server {
    private static List<DatagramSocket> sockets=new ArrayList<>();
      List<Integer> ports=new ArrayList<>();
    //static int [] a;

    public List<Integer> portnumbers (){

        Scanner scanner = new Scanner(System.in);
        System.out.println("provide the length of sequence : ");
        int length = scanner.nextInt();
        //a=new int[length];


        for(int i = 0; i < length; i++){
            System.out.println("enter the port number(s) , one by one ->");
            int port = scanner.nextInt();
            //store the sequence of port that user want into list
            //a[i]=port;
            ports.add(port);
        }

        HashSet<Integer> a = new HashSet(ports); //removing duplicate
        this.ports = new ArrayList(a);



        return ports;

    }

    private void service(List<Integer> ports1) throws IOException {

        for (int i = 0; i <ports1.size() ; i++) {
            if(ports1.get(i) > 0 && ports1.get(i) < 65535){
                try {
                    sockets.add(new DatagramSocket(ports1.get(i)));
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                System.out.println("Server listens on port: " + ports1.get(i));
            }else {
                System.out.println("out of range!");
            }
        }

        //DatagramSocket serverSocket = new DatagramSocket(9876);

        while(true) {
            byte[] receiveData = new byte[1024];
            //connection
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            for (int i = 0; i < sockets.size(); i++) {
                sockets.get(i).receive(receivePacket);
                int port = receivePacket.getPort();
                InetAddress IPAddress = receivePacket.getAddress();

                String req = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("CLIENT: " + receivePacket.getAddress() + "\n PORT :" + receivePacket.getPort());
                System.out.println("RECEIVED: " + req);

                if (port == receivePacket.getPort() && IPAddress.equals(receivePacket.getAddress())) {

                    new Thread(() ->  {
                    //new random ports
                    String newport ;
                        DatagramSocket newSocket=null;

                        int random = (int) ((Math.random() * 65535 - 49152 + 1) + 49152); //dynamic ports
                        newport = String.valueOf(random) + " ";

                        try {
                             newSocket = new DatagramSocket(random);
                        } catch (SocketException e) {
                            e.printStackTrace();
                        }
                        System.out.println("new randomly selected port : " + random);

                        //send randomly selected port number to the client
                    byte[] sendData = new byte[1024];
                    sendData = newport.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                        try {
                            newSocket.send(sendPacket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //sending name of file, length and content
                        File file = new File("C:\\Users\\aysen\\IdeaProjects\\PortKnockingUDP\\src\\text.txt");
                        String filename ="text.txt/" + file.length();
                        byte[] name =filename.getBytes();
                        byte[] content = new byte[(int) file.length()];
                        try {
                            FileInputStream inputStream=new FileInputStream(file);
                            inputStream.read(content);
                            inputStream.close();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        DatagramPacket nameResponse = new DatagramPacket(name, name.length, IPAddress, port);
                        DatagramPacket contentResponse = new DatagramPacket(content, content.length, IPAddress, port);
                        try {
                            newSocket.send(nameResponse);
                            newSocket.send(contentResponse);
                            //System.out.println(filename + "content :" +content.toString());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("FILE HAS SENT SUCCESFULLY!");


                    }).start();
                }else {
                    System.out.println("something is wrong!");
                    for (int j = 0; j <sockets.size() ; j++) {
                        sockets.get(j).close();

                    }

                }
            }
        }
    }

    public  void something() {
        List<Integer> portss=portnumbers();
        try {
           service(portss);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server= new Server();
        server.something();


    }
}
