import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientTreadRead implements Runnable {

    private final Socket socket;
    private final BufferedReader readfromserver;

    public ClientTreadRead(Socket socket) throws IOException {
        readfromserver = new BufferedReader(new InputStreamReader(
                socket.getInputStream(), StandardCharsets.UTF_8));
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String console = readfromserver.readLine();
                if (console != null) {
                    System.out.println(console);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
