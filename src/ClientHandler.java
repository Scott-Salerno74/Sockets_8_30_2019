import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private Socket socket;
    public ClientHandler(Socket socket, DataInputStream inputStream, DataOutputStream outputStream){
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;

    }
    @Override
    public void run(){

    }
    }

