import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * Authors: Scott Salerno
 * Assignment#1
 * Dr. Yarnall
 * 9/13/2019
 */
public class Client {
    private Socket clientSocket;
    private ServerSocket server;
    private DataInputStream input;
    private DataOutputStream output;
    //Use Port Number 6969
    private final static int portNumber = 6969;

    //Constructor of our client socket
    private Client(String ipAdd, int portNum) throws IOException {
        try {
            clientSocket = new Socket(ipAdd, portNumber);
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());
            System.out.println("Connection Successful");


        } catch (IOException e) {
            System.out.print(e);
        }

        int intInput;

            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter a word to check it's spelling: ");
            System.out.println("Enter Quit to Exit Program");
            String inputScan = sc.next();
            int len = inputScan.length();
            while (!inputScan.equalsIgnoreCase("Quit")) {
                len = inputScan.length();
                output.writeByte(0);
                output.writeInt(len);
                output.writeBytes(inputScan);
                int messageId = input.read();
                int serverLen = input.readInt();
                byte[] responseBytes = input.readNBytes(serverLen);
                String response = new String(responseBytes);
                System.out.println("Server Says: " + response);
                System.out.println("Enter Another Word:");
                inputScan = sc.next();
            }
                try {
                    output.writeByte(1);
                    output.writeInt(len);
                    output.writeBytes(inputScan);
                    input.close();
                    output.close();
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.print(e);

                }
            }



    public static void main(String[] args) throws IOException {
        Scanner scanIn = new Scanner(System.in);
        //Have User Enter the IP Address based on other team
        System.out.println("Enter the Ip Address of the Server:");
        String ipAdd = scanIn.nextLine();
        Client client = new Client(ipAdd, portNumber);


    }


}
