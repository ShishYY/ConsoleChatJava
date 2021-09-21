import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientThreadWrite implements Runnable {


    private final Socket socket;
    private final BufferedWriter writer;
    private final BufferedReader readerin;

    public ClientThreadWrite(Socket socket) throws IOException {
        this.socket = socket;
        this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        this.readerin = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        while (true) {
            String message;
            try {
                message = readerin.readLine();
                if (message.equals("/Exit")) {
                    writer.write("/Exit" + "\n");
                    break;
                } else {
                    writer.write(message);
                    writer.newLine();
                    writer.flush();
                }
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
