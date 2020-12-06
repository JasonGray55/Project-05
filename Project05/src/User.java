import java.util.ArrayList;

public class User {
    String name;
    String username;
    String password;
    int age;
    String bday;
    String gender;
    ArrayList<Conversation> texts;
    public User(String name, String username, String password, int age, String gender) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.texts = new ArrayList<>();
    }



    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void addConversation(User userOne, User userTwo) {
        this.texts.add(new Conversation(userOne, userTwo));
    }




    @Override
    public boolean equals(Object o){
        if (o instanceof User){
            return ((User) o).getUsername().equals(this.username);
        }
        return false;
    }

//    public void addFriend(String username) throws Exception{
//        for (int i=0; i< MessageServer.users.size(); i++){
//            User user = MessageServer.users.get(i);
//            if (user.getUsername().equals(username)){
//                this.texts.put(user, new ArrayList<>());
//                File file = new File(username + "&" + this.username);
//                if (!file.createNewFile()) {
//                    throw new Exception("Friend already added!");
//                }
//                return;
//            }
//        }
//        throw new Exception("User not found");
//    }

//    public void sendMessage(String username, String message) throws Exception{
//        for (int i=0; i< MessageServer.users.size(); i++) {
//            User user = MessageServer.users.get(i);
//            if (user.getUsername().equals(username)) {
//                ArrayList<Message> messages = this.texts.get(user);
//                messages.add(new Message(this, user, message));
//                this.texts.put(user, messages);
//                File one = new File(this.username + "&" + username);
//                File two = new File(username + "&" + this.username);
//                String fileName;
//                if (one.exists()){
//                    fileName=this.username + "&" + username;
//                }
//                else if (two.exists()){
//                    fileName=username + "&" + this.username ;
//                }
//                else  {
//                    File file = new File(username + "&" + this.username);
//                    file.createNewFile();
//                    fileName = this.username + "&" + username;
//                }
//                try{
//                    BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
//                    bw.write(this.username +", " + username + ", " + message +"\n");
//
//                }  catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return;
//            }
//        }
//        throw new Exception("User not found");
//    }


}
