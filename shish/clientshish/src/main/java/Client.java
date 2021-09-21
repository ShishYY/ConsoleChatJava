import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class Client {

    private final BufferedReader readerfromconsole;
    private final Socket socket;

    public Client(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.readerfromconsole = new BufferedReader(new InputStreamReader(System.in));

    }


    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 6060);
        client.welcomeMessage();
        client.startClient(client.socket);

    }

    public void startClient(Socket socket) throws IOException {
        ClientThreadWrite clientThreadWrite = new ClientThreadWrite(socket);
        Thread writer = new Thread(clientThreadWrite);
        writer.start();
        ClientTreadRead clientTreadRead = new ClientTreadRead(socket);
        Thread read = new Thread(clientTreadRead);
        read.start();
    }

    public void welcomeMessage() {
        System.out.println("------------------------------------------");
        System.out.println("--------- Welcome to Console_Chat --------");
        System.out.println("------------------------------------------");
    }

}