import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.function.BiConsumer;

public class ClientConnection extends Server {

    private String userName;

    public void sendMessageToAll(String message, String clientName) {
        usersList.forEach((socket, client) -> {
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),
                        StandardCharsets.UTF_8));
                writer.write(clientName + ":" + message);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String name) throws IOException {
        this.userName = name;
    }

    public boolean containsUserName(String name) {
        boolean flag = false;
        for (ClientConnection client : usersList.values()) {
            if (client.getUserName().equals(name)) {
                flag = true;
            }
        }
        return flag;
    }

    public String startserverInfoMessage() {
        String info = "you connected to chat:\n"
                + "Write \"-Info\" to see options";
        return info;
    }

    public String infomsg() {
        String string = ("print: \"-Exit\" if you want to leave chat \n"
                + "\"UserID:your message\" for private message\n"
                + "\"-UsersList\" for see list of Users\n"
                + "\"-Story\" to see previous message");
        return string;
    }

    public void sendPrvtemsg(String message, String userName, String sender) throws IOException {
        usersList.forEach((socket, clientConnection) -> {
            if (clientConnection.getUserName().equals(userName)) {
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),
                            StandardCharsets.UTF_8));
                    writer.write("Private message " + sender + message.substring(message.lastIndexOf(":")));
                    writer.newLine();
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public String listofusers() {
        StringBuilder stringBuilder = new StringBuilder();
        usersList.forEach((socket, clientConnection) -> {
            stringBuilder.append(clientConnection.getUserName() + "\n");
        });
        return stringBuilder.toString();
    }

}