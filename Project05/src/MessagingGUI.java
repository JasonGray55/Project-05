import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class MessagingGUI extends JFrame implements ActionListener {
    private JFrame frame;
    private static JTextArea textArea;
    private static JButton send;
    private static JPanel messagePanel;
    private static JTextField textField;
    private static JScrollPane scroller;
    DataOutputStream output;
    DataInputStream input;
    Scanner scanner = new Scanner(System.in);

    public MessagingGUI() {
        frame = new JFrame();
        messagePanel = new JPanel();
        textField = new JTextField(30);
        send = new JButton("Send");
        frame.setTitle("ChatNet");
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));

        frame.setVisible(true);
        frame.setSize(1280, 720);
        textArea = new JTextArea(30, 50);
        textArea.setBackground(Color.white);
        textArea.setLayout(new FlowLayout());
        scroller = new JScrollPane(textArea);
        getContentPane().add(scroller, BorderLayout.CENTER);
        frame.add(scroller);
        frame.add(send);
        frame.add(textField);
        send.addActionListener(this);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed (ActionEvent e) {
        if (e.getSource() == send) {
            new Thread(new Runnable() {
                public void run() {
                    do {
                        try {
                            String sendMsg = scanner.nextLine();
                            textArea.append(sendMsg + "\n");
                            output.writeUTF(sendMsg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } while(true);
                }
            }).start();
        }
    }

    public void getMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    try {
                        String msg = input.readUTF();
                        textArea.append(msg + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while(true);
            }
        }).start();
    }

    public void setOutput(DataOutputStream output) {
        this.output = output;
    }

    public void setInput(DataInputStream input) {
        this.input = input;
    }
}