import java.net.*;
import java.io.*;
import java.util.HashSet;
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
                //Client Handler Launches thread
                ClientHandler ch = new ClientHandler(client, input, output,dictionary);

                ch.start();

            } catch (Exception e) {
                client.close();
                e.printStackTrace();
            }
        }

    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private DataOutputStream output;
        private DataInputStream input;
        private static HashSet<String> dictionary = new HashSet<String>();
        private String wordsToCheck;

        public ClientHandler(Socket socket, DataInputStream inputStream, DataOutputStream outputStream,HashSet<String> dictionary) {
            this.socket = socket;
            this.input = inputStream;
            this.output = outputStream;
            this.dictionary = dictionary;

        }

        @Override
        public void run() {
            while (true) {
                try {
                    int messageId = input.read();
                    //System.out.println(messageId);
                    int len = input.readInt();
                    //System.out.println(len);
                    byte[] bytes = input.readNBytes(len);
                    wordsToCheck = new String(bytes);
                    //System.out.println(wordsToCheck);
                    //System.out.println(clientInput);

                    //While client response does not equal quit
                    if(messageId == 0 ) {
                        if (dictionary.contains(wordsToCheck)) {
                            System.out.println("correct");
                            output.writeByte(2);
                            output.writeInt(7);
                            output.writeBytes("correct");
                        } else {
                            System.out.println("misspelled");
                            output.writeByte(2);
                            output.writeInt(10);
                            output.writeBytes("misspelled");
                        }
                    }
                    //handle Quit Message
                    if (wordsToCheck.equalsIgnoreCase("Quit")) {
                        System.out.println("Socket is Closing. Thank You!");
                        input.close();
                        output.close();
                        socket.close();
                        break;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }
}
