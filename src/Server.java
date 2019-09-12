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
    private String wordsToCheck;

    /*
     Start the Connection
     */
    public void start() throws IOException {
        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server has Started and is listening to port 6969");
            client = serverSocket.accept();

        }
        catch (IOException e){
            System.out.println(e);
        }

        while (true) {
            DataInputStream input = new DataInputStream(client.getInputStream());
            DataOutputStream output = new DataOutputStream(client.getOutputStream());
            int messageId = input.read();
            System.out.println(messageId);
            int len = input.readInt();
            System.out.println(len);
            byte[] bytes = input.readNBytes(len);
             wordsToCheck = new String(bytes);
            System.out.println(wordsToCheck);
            //System.out.println(clientInput);
            //While client response does not equal quit
            while (!wordsToCheck.equalsIgnoreCase("Quit") && messageId != -1) {
                if (dictionary.contains(wordsToCheck)) {
                    System.out.println(wordsToCheck + " Is Spelled Correctly!");
                    output.writeByte(2);
                    output.writeInt(len);
                    output.writeBytes(wordsToCheck + " Is  Spelled Correctly!");
                } else {
                    System.out.println(wordsToCheck + " Is Misspelled!");
                    output.writeByte(2);
                    output.writeInt(len);
                    output.writeBytes(wordsToCheck + " Is Misspelled!");
                }
                //Get a New Word To Check
                messageId = input.read();
                System.out.println(messageId);
                len = input.readInt();
                System.out.println(len);
                bytes = input.readNBytes(len);
                wordsToCheck = new String(bytes);
                System.out.println(wordsToCheck);
            }
                try {
                    System.out.println("Thank You For Visiting!");
                    input.close();
                    output.close();
                    stop();
                } catch (IllegalArgumentException e) {
                    System.out.println(e);

                }



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

