import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * Authors: Scott Salerno
 * Assignment#1
 * Dr. Yarnall
 * 9/13/2019
 */
public class ClientSocket {
    private Socket clientSocket;
    private DataInputStream input;
    private DataOutputStream output;

    //Constructor of our client socket
    private ClientSocket(String ipAdd, int portNum){
        try {
            clientSocket = new Socket(ipAdd,portNum);
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());
            System.out.println("Connection Successful");



        } catch (IOException e) {
            System.out.print(e);
        }

        int intInput;

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please Enter An Integer to Send To Server: ");
            intInput = sc.nextInt();
            output.writeInt(intInput);
            //int foobar = input.read();
            //System.out.println("Server sent back: " + foobar);
        } catch (IOException e) {
            System.out.print(e);
        }
        try {
            input.close();
            output.close();
            clientSocket.close();
        } catch (IOException e){
            System.out.print(e);

        }


    }

    public static void main(String[] args) throws IOException {
        Scanner scanIn = new Scanner(System.in);
        //Have User Enter the IP Address based on other team
        System.out.println("Enter the Ip Address of the Server:");
        String ipAdd = scanIn.nextLine();
        ClientSocket client = new ClientSocket(ipAdd, 6666);


    }

    static class Handle extends Thread{
        private int portNum;
        public Handle(int portNum){
            portNum = this.portNum;
        }
        @Override
        public void run(){
            //Handle the Connection

        }
    }
}
