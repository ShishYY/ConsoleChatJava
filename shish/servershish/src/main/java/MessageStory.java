import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;


public class MessageStory {

    public volatile LinkedList<String> inputMessage = new LinkedList<String>();

    public void addStory(String message) {
        if (inputMessage.size() >= 10) {
            inputMessage.removeFirst();
        }
        inputMessage.add(message);
    }

    public void printStory(BufferedWriter writer) throws IOException {
        if (inputMessage.isEmpty()) {
            writer.write("No story you first member");
            writer.newLine();
            writer.flush();
        }
        try {
            writer.write("History of messages: \n");
            for (String string : inputMessage) {
                writer.write(string);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.flush();
    }
}



