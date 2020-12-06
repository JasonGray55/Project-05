import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MessageGUI extends JFrame {
    private Socket socket;
    private ServerSocket serverSocket;
    private JTextField userInput;
    private JTextArea chatWindow;
    private ObjectOutputStream oos;
    private ObjectInputStream input;

    public MessageGUI(User user) {
        super("ChatNet");
        userInput = new JTextField();
        userInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(e.getActionCommand());
                userInput.setText("");
            }
        });

        add(userInput, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow));
        setSize(300, 150);
        setVisible(true);
    }

    private void sendMessage(String message) {
        try {
            oos.writeObject("Server: " + message);
            oos.flush();
            showMessage("Server: ");
        } catch (IOException e) {
            chatWindow.append("Error, MESSAGES UNABLE TO BE SENT");
        }
    }

    private void showMessage(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                chatWindow.append(text);
            }
        });
    }

    private void chatting() throws IOException {
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //showMessage("Now connecting with: " );
        //Line above is supposed to say who you are connecting with

        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.flush();
        input = new ObjectInputStream(socket.getInputStream());
        showMessage("Connection established");

        String textInput = "You are now connected";
        sendMessage(textInput);
        try {
            do {
                try {
                    textInput = (String) input.readObject();
                    showMessage(textInput);
                } catch (ClassNotFoundException e) {
                    showMessage("Cannot determine what user has sent...");
                }
            } while (!textInput.equals("End Connection"));
        } catch (IOException e) {
            showMessage("Connection could not be established");
        }
    }


    private void closeChatWindow() {
        showMessage("\n shutting down communication, good-bye");
        try {
            oos.close();
            input.close();
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void runChatNet() throws IOException{
        serverSocket = new ServerSocket(59001, 100);
        boolean run = true;
        while(run) {
            try {
                chatting();
            } catch (EOFException e) {
                showMessage("\n Connection has terminated");
            } finally {
                closeChatWindow();
            }
        }
    }
}
