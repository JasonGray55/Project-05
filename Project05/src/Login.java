import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;


public class Login extends  JFrame {
    ArrayList<User> users;
    JFrame frame;
    JButton loginButton;
    JPanel loginPanel;
    JTextField user;
    JTextField pass;
    JLabel username;
    JLabel password;
    JButton newUser;

    JPanel newUserPanel;
    JTextField newUsername;
    JTextField newPassword;
    JTextField age;
    JComboBox<String> gender;
    JTextField usersFirstName;
    JTextField usersLastName;
    JLabel usernameLabel;
    JLabel passwordLabel;
    JLabel ageLabel;
    JLabel genderLabel;
    JLabel firstNameLabel;
    JLabel lastNameLabel;
    JButton addUser;
    JButton goBack;
    HashMap<String, String> usernamesPasswords;



    public Login() {
        super("ChatNet");
        users = new ArrayList<>();
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
        frame = new JFrame("Message Simulator");
        loginButton = new JButton("Login");
        loginPanel = new JPanel();
        user = new JTextField(15);
        pass = new JPasswordField(15);
        newUser = new JButton("New User?");
        username = new JLabel("User: ");
        password = new JLabel("Password: ");

        loginPanel.setSize(300, 200);
        loginPanel.setLocation(500, 200);
        loginPanel.setLayout(null);
        frame.setSize(400,300);
        user.setBounds(90, 30, 150, 20);
        pass.setBounds(90, 65, 150, 20);
        loginButton.setBounds(110, 100, 100, 20);
        newUser.setBounds(110, 135, 100, 20);
        username.setBounds(20, 28, 80, 20);
        password.setBounds(20, 63, 80, 20);

        loginPanel.add(loginButton);
        loginPanel.add(user);
        loginPanel.add(pass);
        loginPanel.add(newUser);
        loginPanel.add(username);
        loginPanel.add(password);
        frame.add(loginPanel);

        frame.setVisible(true);
        loginPanel.setVisible(true);


        newUserPanel = new JPanel();
        newUserPanel.setSize(600,400);
        newUsername = new JTextField(15);
        newPassword = new JTextField(15);
        age = new JTextField(15);
        String[] genders = {"Male", "Female", "Deline to State"};
        gender = new JComboBox<String>(genders);
        usersFirstName = new JTextField(15);
        usersLastName = new JTextField(15);
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        ageLabel = new JLabel("Age:");
        genderLabel = new JLabel("Gender:");
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        addUser = new JButton("Add User");
        goBack = new JButton("Go Back");


        newUserPanel.setSize(600, 400);
        newUserPanel.setLocation(500, 200);
        newUserPanel.setLayout(null);
        usernameLabel.setBounds(70,30,150,20);
        passwordLabel.setBounds(70,60,150,20);
        ageLabel.setBounds(70,90,150,20);
        genderLabel.setBounds(70,120,150,20);
        firstNameLabel.setBounds(70,150,150,20);
        lastNameLabel.setBounds(70,180,150,20);
        newUsername.setBounds(150,30,150,20);
        newPassword.setBounds(150,60,150,20);
        age.setBounds(150,90,150,20);
        gender.setBounds(150,120,150,20);
        usersFirstName.setBounds(150,150,150,20);
        usersLastName.setBounds(150,180,150,20);
        addUser.setBounds(70, 210, 100, 20);
        goBack.setBounds(200, 210, 100, 20);


        newUserPanel.add(newUsername);
        newUserPanel.add(newPassword);
        newUserPanel.add(age);
        newUserPanel.add(gender);
        newUserPanel.add(usersFirstName);
        newUserPanel.add(usersLastName);
        newUserPanel.add(usernameLabel);
        newUserPanel.add(passwordLabel);
        newUserPanel.add(ageLabel);
        newUserPanel.add(genderLabel);
        newUserPanel.add(firstNameLabel);
        newUserPanel.add(lastNameLabel);
        newUserPanel.add(addUser);
        newUserPanel.add(goBack);
        frame.add(newUserPanel);
        newUserPanel.setVisible(false);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String puname = user.getText();
                String ppaswd = pass.getText();

                if (puname.equals("") || ppaswd.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please insert Username and Password");
                } else {

                    int userIndex = checkUsername(users, puname);
                    if (userIndex == -1) {
                        JOptionPane.showMessageDialog(null, "No accounts with that username");
                        user.setText("");
                        pass.setText("");
                        user.requestFocus();
                    } else {
                        if (users.get(userIndex).getPassword().equals(ppaswd)) {
                            ChatServer.loginDone++;
                            new ChatClient(users.get(userIndex));
                            frame.setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "Wrong Password");
                            user.setText("");
                            pass.setText("");
                            user.requestFocus();
                        }
                    }
                }
            }


        });

        newUser.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                loginPanel.setVisible(false);
                newUserPanel.setVisible(true);
                goBack.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        loginPanel.setVisible(true);
                        newUserPanel.setVisible(false);
                    }
                });
                addUser.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String username = newUsername.getText();
                        boolean exists = false;
                        for (int i = 0; i < users.size(); i++) {
                            if (users.get(i).getUsername().equals(username)) {
                                exists = true;
                                loginPanel.setVisible(true);
                                newUserPanel.setVisible(false);
                                JOptionPane.showMessageDialog(null, "That username already exists");
                            }
                        }
                        if(!exists) {
                            String password = newPassword.getText();
                            int ageOfUser = Integer.parseInt(age.getText().trim());
                            String genderOfUser = String.valueOf(gender.getSelectedItem());
                            String name = usersFirstName.getText().trim() + " " + usersLastName.getText().trim();
                            User newUser = new User(name, username, password, ageOfUser, genderOfUser);
                            users.add(newUser);
                            storeUsernamePassword(newUser);
                            ChatServer.loginDone++;
                            new ChatClient(newUser);
                            frame.setVisible(false);
                        }
                    }
                });
            }
        });


    }

    public int checkUsername(ArrayList<User> usernameArray, String puname) {
        int userIndex = -1;
        for (int i = 0; i < usernameArray.size(); i++) {
            if (usernameArray.get(i).getUsername().equals(puname)) {
                userIndex = i;
                break;
            }
        }
        if (userIndex == -1) {
            return -1;
        }
        return userIndex;
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


}
