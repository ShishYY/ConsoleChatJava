import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class Server {

    public static final int PORT = 6060;
    public static volatile Map<Socket, ClientConnection> usersList = new HashMap<>();
    public static MessageStory story;


    public static void main(String[] args) throws IOException {
        Server server = new Server();
        story = new MessageStory();
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started");
        server.startServer(serverSocket);

    }

    public void startServer(ServerSocket serverSocket) throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            ServerClientThread clientThread = new ServerClientThread(socket);
            Thread thread = new Thread(clientThread);
            thread.setDaemon(true);
            thread.start();
            System.out.println("New Connection:" + socket.getLocalSocketAddress().toString());
        }
    }

}

