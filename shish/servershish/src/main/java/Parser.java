import java.io.IOException;
import java.net.Socket;

public class Parser extends ClientConnection {

    public Option parse(String message) {
        String messagein = message.toLowerCase();
        if (messagein.contains(":")) {
            return Option.PRIVATEMSG;
        } else if (messagein.equals("-info")) {
            return Option.INFOMSG;
        } else if (messagein.equals("-userslist")) {
            return Option.LISTOFUSERS;
        } else if (messagein.equals("-exit")) {
            return Option.EXIT;
        } else if (message.equals("-story")) {
            return Option.STORY;
        }
        return Option.PUBLICMSG;
    }

    public String userforprivate(String msg) {
        System.out.println(msg.substring(0, msg.lastIndexOf(":")));
        return msg.substring(0, msg.lastIndexOf(":"));
    }
}
