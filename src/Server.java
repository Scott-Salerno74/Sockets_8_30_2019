import java.net.*;
import java.io.*;

/**
 * Server side Socket program
 * 9/13/19
 * Author: Scott Salerno
 * Dr. Yarnall
 */
public class Server {
    private ServerSocket serverSocket;
    private Socket client;
    private PrintWriter output;
    private BufferedReader input;

    /*
     Start the Connection
     */
    public void start(int portNum) throws IOException {
        serverSocket = new ServerSocket(portNum);
        client = serverSocket.accept();
        output = new PrintWriter(client.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String hello = input.readLine();
        if ("hello server".equals(hello)) {
            output.println("Hello Client");
        } else {
            output.println("Input Unrecognized");
        }
    }

    //End The connection
    public void stop() throws IOException {
        input.close();
        output.close();
        client.close();
        serverSocket.close();
    }
    public static void  main  (String[] args) throws IOException {
        Server server = new Server();
        server.start(8888);
    }
}

