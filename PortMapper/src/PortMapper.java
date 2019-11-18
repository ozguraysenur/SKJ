import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class PortMapper extends Thread {
    private Socket socket;
    String [] data;
    Client client;

    public HashMap<String, String[]> services = new HashMap<>();

    public PortMapper(Socket socket) {
        super();
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String line= in.readLine();
            data =line.split(" ");
            String command =data[0];
            String name = data[1];
            String[] ipandport = {data[2], data[3]};


            while ((line = in.readLine()) != null && !line.isEmpty()) {

                if (command.equals("REGISTER")) {

                    services.put(name, ipandport);
                    if(services.containsKey(name)){
                        out.println("Service IP port registered as name");
                    }else{
                        out.println("ERROR");
                    }

                } else if (command.equals("GET")){

                out.println(services.get(name)); //key

                } else if (command.equals("CALL"))  {

                    String ip = services.get(name)[0];
                    String port = services.get(name)[1];
                    String input = data[1];

                    out.println(client.clieent(ip,port,input));
                }
            }


        } catch (IOException e1) {
            // do nothing
        }

        try {
            socket.close();
        } catch (IOException e) {
            // do nothing
        }
    }
}
