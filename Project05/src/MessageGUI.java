import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class MessageGUI extends JFrame {
    JFrame frame;
    JPanel messagePanel;
    JTextField conversationTextField;
    JButton getConversationTextField;
    ArrayList<User> users;
    int counter = 0;
    int yCoordCounter = 60;
    User currentUser;

    public MessageGUI(User user) {
        super("ChatNet");
        currentUser = user;
        users = new ArrayList<>();
        frame = new JFrame("Message Simulator");
        messagePanel = new JPanel();
        conversationTextField = new JTextField(15);
        getConversationTextField = new JButton("Find User");
        try {
            File file = new File("Project05/userPass.txt");
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

        messagePanel.setSize(300, 200);
        messagePanel.setLocation(500, 200);
        messagePanel.setLayout(null);
        frame.setSize(600, 400);
        conversationTextField.setBounds(90, 30, 150, 20);
        getConversationTextField.setBounds(250, 30, 100, 20);


        messagePanel.add(conversationTextField);
        messagePanel.add(getConversationTextField);
        frame.add(messagePanel);
        frame.setVisible(true);
        messagePanel.setVisible(true);
        getConversationTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // This action listener takes a minute for the button to pop up. Be patient when pressing it.
                for (int i = 0; i < users.size(); i++) {
                    // Need to add check to see if you already have convo with that person. Didnt have time to implement before work. Will do it when I get back.


                    if (yCoordCounter == 240) { // Max convo limit is 5, we can change if we want too lol just did it to keep us sane.
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
                        conversation.setBounds(90, yCoordCounter, 150, 20);
                        messagePanel.add(conversation);
                        yCoordCounter += 30;
                        currentUser.addConversation(currentUser, users.get(i));
                        users.get(i).addConversation(users.get(i), currentUser);
                        break;
                    } else if (i == users.size() - 1) { // User not found if loops through the whole thing.
                        JOptionPane.showMessageDialog(null, "Error. User not Found.",
                                "User not Found Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }


    public void storeUsernamePassword(User newUser) {
        try{
            File file = new File("Project05/userPass.txt");
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

}

