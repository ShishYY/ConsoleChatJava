import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class ServerClientThread extends Server implements Runnable, AutoCloseable {


    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final ClientConnection client;
    private final Parser parser;

    public ServerClientThread(Socket socket) throws IOException {
        this.parser = new Parser();
        this.client = new ClientConnection();
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
    }

    @Override
    public void run() {
        boolean flag = true;
        try {
            write("Please Enter your name");
            String username = in.readLine();
            while (client.containsUserName(username)) {
                write("This name is already"
                        + " taken please Enter another name");
                username = in.readLine();
            }
            client.setUserName(username);
            usersList.put(socket, client);
            write(client.startserverInfoMessage());
            while (flag) {
                String message = in.readLine();
                switch (parser.parse(message)) {
                    case PRIVATEMSG:
                        client.sendPrvtemsg(message, parser.userforprivate(message), client.getUserName());
                        break;
                    case INFOMSG:
                        write(client.infomsg());
                        break;
                    case PUBLICMSG:
                        client.sendMessageToAll(message, client.getUserName());
                        story.addStory(client.getUserName() + ":" + message);
                        break;
                    case LISTOFUSERS:
                        write(client.listofusers());
                        break;
                    case STORY:
                        story.printStory(out);
                        break;
                    case EXIT:
                        client.sendMessageToAll("Client: " + client.getUserName() + " leave chat", "InfoMSG");
                        close();
                        flag = false;
                        break;
                    default:
                        break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            client.sendMessageToAll("disconnected", client.getUserName());
            try {
                close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void write(String message) throws IOException {
        out.write(message);
        out.newLine();
        out.flush();
    }

    @Override
    public void close() throws Exception {
        in.close();
        out.close();
        socket.close();
        usersList.remove(socket);
    }
}

