//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.net.Socket;
//import java.util.HashSet;
//
//public class ClientHandler extends Thread {
//    private DataOutputStream outputStream;
//    private DataInputStream inputStream;
//    private Socket socket;
//    private static HashSet<String> dictionary = new HashSet<String>();
//    private String wordsToCheck;
//
//    public ClientHandler(Socket socket, DataInputStream inputStream, DataOutputStream outputStream){
//        this.socket = socket;
//        this.inputStream = inputStream;
//        this.outputStream = outputStream;
//
//    }
//    @Override
//    public void run(){
//        while (true) {
//            DataInputStream input = new DataInputStream(client.getInputStream());
//            DataOutputStream output = new DataOutputStream(client.getOutputStream());
//            int messageId = input.read();
//            System.out.println(messageId);
//            int len = input.readInt();
//            System.out.println(len);
//            byte[] bytes = input.readNBytes(len);
//            wordsToCheck = new String(bytes);
//            System.out.println(wordsToCheck);
//            //System.out.println(clientInput);
//            //While client response does not equal quit
//            while (!wordsToCheck.equalsIgnoreCase("Quit") && messageId != -1) {
//                if (dictionary.contains(wordsToCheck)) {
//                    System.out.println(wordsToCheck + " Is Spelled Correctly!");
//                    output.writeByte(2);
//                    output.writeInt(len);
//                    output.writeBytes(wordsToCheck + " Is  Spelled Correctly!");
//                } else {
//                    System.out.println(wordsToCheck + " Is Misspelled!");
//                    output.writeByte(2);
//                    output.writeInt(len);
//                    output.writeBytes(wordsToCheck + " Is Misspelled!");
//                }
//                //Get a New Word To Check
//                messageId = input.read();
//                System.out.println(messageId);
//                len = input.readInt();
//                System.out.println(len);
//                bytes = input.readNBytes(len);
//                wordsToCheck = new String(bytes);
//                System.out.println(wordsToCheck);
//            }
//            try {
//                System.out.println("Thank You For Visiting!");
//                input.close();
//                output.close();
//                stop();
//            } catch (IllegalArgumentException e) {
//                System.out.println(e);
//
//            }
//
//    }
//    }

