import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
public class ServerThreaded {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(6969);

        while(true){
            //Create a null socket to add to a
            Socket client = null;
            try{
                client = server.accept();
                System.out.println("Client Has Connected: "+ client);
                DataInputStream input = new DataInputStream(client.getInputStream());
                DataOutputStream output = new DataOutputStream(client.getOutputStream());
                ClientHandler ch = new ClientHandler(client,input,output);

                ch.start();

            }
            catch (Exception e){
                client.close();
                e.printStackTrace();
            }
        }

    }
}
