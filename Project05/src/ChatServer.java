import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class ChatServer {

    private static Set<String> names = new HashSet<>();

    private static Set<PrintWriter> writers = new HashSet<>();

    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running...");
        var pool = Executors.newFixedThreadPool(500);
        try (var listener = new ServerSocket(59001)) {
            pool.execute(new Handler(listener.accept()));
        }

    }

    private static class Handler implements Runnable {
        private String name;
        private Socket socket;
        private Scanner in;
        private PrintWriter out;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            new Login();
//            try {
//                in = new Scanner(socket.getInputStream());
//                out = new PrintWriter(socket.getOutputStream(), true);
//
//
//                while (true) {
//                    out.println("SUBMITNAME");
//                    name = in.nextLine();
//                    if (name == null) {
//                        return;
//                    }
//                    synchronized (names) {
//                        if (!name.isBlank() && !names.contains(name)) {
//                            names.add(name);
//                            break;
//                        }
//                    }
//                }
//
//                out.println("NAMEACCEPTED " + name);
//                for (PrintWriter writer : writers) {
//                    writer.println("MESSAGE " + name + " has joined");
//                }
//                writers.add(out);
//
//                while (true) {
//                    String input = in.nextLine();
//                    if (input.toLowerCase().startsWith("/quit")) {
//                        return;
//                    }
//                    for (PrintWriter writer : writers) {
//                        writer.println("MESSAGE " + name + ": " + input);
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println(e);
//            } finally {
//                if (out != null) {
//                    writers.remove(out);
//                }
//                if (name != null) {
//                    System.out.println(name + " is leaving");
//                    names.remove(name);
//                    for (PrintWriter writer : writers) {
//                        writer.println("MESSAGE " + name + " has left");
//                    }
//                }
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                }
//            }
        }

    }
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
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
        return users;
    }
}