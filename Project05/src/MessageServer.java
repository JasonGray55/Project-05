import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MessageServer extends Thread {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(59001);
        Socket socket = serverSocket.accept();
        DataOutputStream serverOutput = new DataOutputStream(socket.getOutputStream());
        DataInputStream serverInput = new DataInputStream(socket.getInputStream());
        new Login();
        // HERE USER HAS CONNECTED SO WE WILL NOW SEND THEM THE LOGIN AND CREATE ACCOUNT GUIS.
        // VINNY HAS LOGIN AND CREATE ACCOUNT GUIS.
        // I THINK THAT THREADS ARE CREATED AFTER USER PRESSES LOGIN.
    }
}
