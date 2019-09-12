import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
public class ServerThreaded {
    //private ServerSocket serverSocket;
    private Socket client;
    private final static int portNumber = 6969;
    //create a hashSet in order to hold the dictionary
    private static HashSet<String> dictionary = new HashSet<String>();
    private String wordsToCheck;

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
        ServerSocket server = new ServerSocket(6969);

        while (true) {
            //Create a null socket to add to a
            Socket client = null;
            try {
                client = server.accept();
                System.out.println("Client Has Connected: " + client);
                DataInputStream input = new DataInputStream(client.getInputStream());
                DataOutputStream output = new DataOutputStream(client.getOutputStream());
                ClientHandler ch = new ClientHandler(client, input, output);

                ch.start();

            } catch (Exception e) {
                client.close();
                e.printStackTrace();
            }
        }

    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private DataOutputStream outputStream;
        private DataInputStream inputStream;
        private static HashSet<String> dictionary = new HashSet<String>();
        private String wordsToCheck;

        public ClientHandler(Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {
            this.socket = socket;
            this.inputStream = inputStream;
            this.outputStream = outputStream;

        }

        @Override
        public void run() {
            while (true) {
                try {

                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
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
                        try {
                            System.out.println("Thank You For Visiting!");
                            input.close();
                            output.close();
                            socket.close();
                        } catch (IllegalArgumentException e) {
                            System.out.println(e);

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }
}
