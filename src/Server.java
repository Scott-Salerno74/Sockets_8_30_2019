import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;

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
    private final static int portNumber = 6969;
    //create a hashSet in order to hold the dictionary
    private static HashSet<String> dictionary;

    /*
     Start the Connection
     */
    public void start(int portNum) throws IOException {
        serverSocket = new ServerSocket(portNum);
        client = serverSocket.accept();
        output = new PrintWriter(client.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String clientInput = input.readLine();

    }
    //End The connection
    public void stop() throws IOException {
        input.close();
        output.close();
        client.close();
        serverSocket.close();
    }
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        Scanner dictFile = new Scanner(new FileReader("/usr/share/dict/words"));
        //Loop into dictFile and add to HashSet
        while(dictFile.hasNextLine()){
            String sValue = dictFile.nextLine();
            dictionary.add(sValue);
        }

        server.start(portNumber);
    }
}

