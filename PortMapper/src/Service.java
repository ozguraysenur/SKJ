import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Service {
    public static void main(String[] args) {


            ServerSocket server = null;
            Socket client = null;


            try {
                server = new ServerSocket(55555);
            }
            catch (IOException e) {
                System.out.println("Could not listen");
                System.exit(-1);
            }
            System.out.println("Server listens on port: " + server.getLocalPort());


            while(true) {
                try {
                    client = server.accept();
                }
                catch (IOException e) {
                    System.out.println("Accept failed");
                    System.exit(-1);
                }

                (new PortMapper(client)).start();
            }

        }


    }

