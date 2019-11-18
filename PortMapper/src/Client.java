import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {



    static Socket socket ;
    PrintWriter out ;
    BufferedReader in ;
    Service service;


    String ipp ;
    String line ="";
    int pport;
    String entry;

    public synchronized  String clieent(String ip, String port ,String input) {


        ipp=ip;
        pport = Integer.parseInt(port);


        try {
            socket = new Socket(ip, pport);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader((socket.getInputStream())));
           // System.out.println(" >> " );
           // entry = in.readLine();

        }
        catch (UnknownHostException e) {
            System.out.println("Unknown host");
            System.exit(-1);
        }
        catch  (IOException e) {
            System.out.println("No I/O");
            System.exit(-1);
        }

        try {
            out.println(input);
            String line = in.readLine();
        }
        catch (IOException e) {
            System.out.println("Error during communication");
            System.exit(-1);
        }


        try {
            socket.close();
        }
        catch (IOException e) {
            System.out.println("Cannot close the socket");
            System.exit(-1);
        }
        return line;

    }






}
