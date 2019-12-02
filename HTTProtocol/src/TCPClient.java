import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class TCPClient {
    public static void main(String args[]) {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String address = "www.pja.edu.pl";
        int port = 80;
        try {
            socket = new Socket(address, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
            out.println("GET / HTTP/1.1");
            out.println("Host: www.pja.edu.pl");
            out.println();

            String line;
            while((line = in.readLine()) != null)
            {
                System.out.println(line);

            }
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

    }
}