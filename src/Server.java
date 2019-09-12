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
    private final static int portNumber = 6969;
    //create a hashSet in order to hold the dictionary
    private static HashSet<String> dictionary = new HashSet<String>();

    /*
     Start the Connection
     */
    public void start() throws IOException {
        serverSocket = new ServerSocket(portNumber);
        System.out.println("Server has Started and is listening to port 6969");
        while (true) {
            client = serverSocket.accept();
           DataInputStream input = new DataInputStream(client.getInputStream());
           DataOutputStream output = new DataOutputStream(client.getOutputStream());
            int messageId = input.read();
            System.out.println(messageId);
            int len = input.readInt();
            System.out.println(len);
            byte[] bytes = input.readNBytes(len);
            String wordsToCheck = new String(bytes);
            System.out.println(wordsToCheck);
            //System.out.println(clientInput);
            String returnMessage;
            //While client response does not equal quit
            while (!wordsToCheck.equalsIgnoreCase("Quit")) {
                wordsToCheck = input.readUTF();
                try {
                  if(dictionary.contains(wordsToCheck)){
                      System.out.println(wordsToCheck + "Is in Dictionary!");
                  }
                } catch (IllegalArgumentException e) {
                    System.out.println("Thank You For Visiting!");
                    input.close();
                    output.close();
                    stop();
                }
            }

            //Close Input Streams

        }

    }
    //End The connection
    public void stop() throws IOException {
        client.close();
        serverSocket.close();
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Server IP Address: " + InetAddress.getLocalHost().getHostAddress());
        System.out.println("Port Number: " + portNumber);
        File file = new File("/usr/share/dict/words");
        Scanner dictFile = new Scanner(new FileReader(file));
        //Loop into dictFile and add to HashSet
        while(dictFile.hasNextLine()){
            String sValue = dictFile.nextLine();
            dictionary.add(sValue);
        }
        Server server = new Server();
        server.start();
    }
}

