import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MessageGUI extends JFrame implements ActionListener {
    JFrame frame;
    JPanel messagePanel;
    JTextField conversationTextField;
    JButton getConversationTextField;
    JTextField removeConversationTextField;
    JButton removeButton;
    ArrayList<User> users;
    ArrayList<String> buttonNames;
    ArrayList<JButton> buttons;
    int numberOfButtons;
    int counter = 0;
    int yCoordCounter = 60;
    User currentUser;
    boolean trueFalse;

    public MessageGUI(User user) {
        super("ChatNet");
        trueFalse = false;
        currentUser = user;
        users = new ArrayList<>();
        buttonNames = new ArrayList<>();
        frame = new JFrame("Message Simulator");
        messagePanel = new JPanel();
        conversationTextField = new JTextField(15);
        getConversationTextField = new JButton("Add Conversation");
        removeConversationTextField = new JTextField(15);
        removeButton = new JButton("Remove Conversation");
        numberOfButtons = 0;
        buttons = new ArrayList<>();

        try {
            File file = new File("userPass.txt");
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);
            String usernamePasswordData = bfr.readLine();
            while (usernamePasswordData != null) {
                String[] data = usernamePasswordData.split(",");
                User readUser = new User(data[2], data[0], data[1], Integer.parseInt(data[3]), data[4]);
                users.add(readUser);
                usernamePasswordData = bfr.readLine();
            }
        } catch (IOException d) {
            d.printStackTrace();
        }

        readInButtons(currentUser);

        messagePanel.setSize(300, 200);
        messagePanel.setLocation(500, 200);
        messagePanel.setLayout(null);
        frame.setSize(600, 400);
        conversationTextField.setBounds(90, 30, 150, 20);
        getConversationTextField.setBounds(250, 30, 200, 20);
        removeConversationTextField.setBounds(90, 330, 150, 20);
        removeButton.setBounds(250, 330, 200,20);



        messagePanel.add(conversationTextField);
        messagePanel.add(getConversationTextField);
        messagePanel.add(removeConversationTextField);
        messagePanel.add(removeButton);
        frame.add(messagePanel);
        frame.setVisible(true);
        messagePanel.setVisible(true);
        getConversationTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // This action listener takes a minute for the button to pop up. Be patient when pressing it.
                trueFalse = false;
                for (int i = 0; i < users.size(); i++) {
                    for (int j = 0; j < buttonNames.size(); j++) {
                        if (trueFalse == false) {
                            if (j == buttonNames.size() - 1) {
                                break;
                            }
                            if (buttonNames.get(j).equalsIgnoreCase(conversationTextField.getText())) {
                                JOptionPane.showMessageDialog(null, "Error. You cannot have another conversation with the same person.",
                                        "Multiple Conversation Error", JOptionPane.ERROR_MESSAGE);
                                trueFalse = true;
                                break;
                            }
                        }
                    }

                    if (yCoordCounter == 300) { // Max convo limit is 5, we can change if we want too lol just did it to keep us sane.
                        JOptionPane.showMessageDialog(null, "Error. You cannot have anymore conversations.",
                                "Conversation Limit Reached", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    if(conversationTextField.getText().equalsIgnoreCase(currentUser.getUsername())) { // Cannot have a conversation with yourself.
                        JOptionPane.showMessageDialog(null, "Error. Cannot start conversation with yourself.",
                                "Cannot have conversation with yourself.", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    if (users.get(i).getUsername().equalsIgnoreCase(conversationTextField.getText())) { // Adds button to click to go to conversation with that user.
                        JButton conversation = new JButton(conversationTextField.getText());
                        buttonNames.add(conversationTextField.getText());
                        numberOfButtons += 1;
                        buttons.add(conversation);
                        conversation.setBounds(90, yCoordCounter, 150, 20);
                        messagePanel.add(conversation);
                        yCoordCounter += 30;
                        break;
                    } else if (i == users.size() - 1) { // User not found if loops through the whole thing.
                        JOptionPane.showMessageDialog(null, "Error. User not Found.",
                                "User not Found Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < buttonNames.size(); i++) {
                    if (buttonNames.get(i).equalsIgnoreCase(removeConversationTextField.getText())) {
                        messagePanel.remove(buttons.get(i));
                        System.out.println(Arrays.toString(buttonNames.toArray()));
                        buttonNames.remove(i);
                        buttons.remove(i);
                        numberOfButtons -= 1;
                        System.out.println(Arrays.toString(buttonNames.toArray()));
                        messagePanel.revalidate();
                        messagePanel.repaint();
                    }
                }
            }
        });

    }

    public void readInButtons(User user) {
        try {

            File file = new File(user.getUsername() + "TextFile.txt");
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);
            String readData = bfr.readLine();
            while (readData != null) {
                String[] data = readData.split(",");
                JButton conversation = new JButton(data[1]);
                buttonNames.add(data[1]);
                numberOfButtons += 1;
                buttons.add(conversation);
                conversation.setBounds(90, yCoordCounter, 150, 20);
                messagePanel.add(conversation);
                yCoordCounter += 30;
                readData = bfr.readLine();
            }
            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new MessagingGUI();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void storeUsernamePassword(User newUser) {
        try{
            File file = new File("userPass.txt");
            FileWriter filewrite = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(filewrite);
            pw.println();
            pw.print(newUser.getUsername() + "," + newUser.getPassword() + "," + newUser.getName()
                    + "," + newUser.getAge() + "," + newUser.getGender());
            pw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Glitch in the system");
        }

    }

//    public void addActionListeners() {
//        for (int i = 0; i < buttons.size(); i++) {
//            buttons.get(i).addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//
//                }
//            });
//        }
//    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int indx = buttons.indexOf( e.getSource() );
        if ( indx >= 0 ) {

        }
    }
}