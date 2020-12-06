import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MessageServer extends Thread {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4242);
        Socket socket = serverSocket.accept();
        new Login();
        // HERE USER HAS CONNECTED SO WE WILL NOW SEND THEM THE LOGIN AND CREATE ACCOUNT GUIS.
        // VINNY HAS LOGIN AND CREATE ACCOUNT GUIS.
        // I THINK THAT THREADS ARE CREATED AFTER USER PRESSES LOGIN.


    }

}
