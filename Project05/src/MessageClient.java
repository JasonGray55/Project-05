

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;


public class MessageClient {
    public static void main(String[] args) throws IOException {
        JOptionPane.showMessageDialog(null, "Welcome to message simulator! Connecting Now!",
                "Message Simulator", JOptionPane.INFORMATION_MESSAGE);
        Socket socket = new Socket("localhost", 59001);
        // LOGIN STARTS HERE. ONCE CLIENT CONNECTS THE SERVER SENDS THE LOG IN GUI
    }
}
