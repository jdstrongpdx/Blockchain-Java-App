package blockchain;

import java.util.List;

/**
 * Represents a unique message/transaction.
 */
public class Message {
    static long counter = 1000;
    long messageId;
    User user;
    String text;

    public Message(User user, String text) {
        this.messageId = counter++;
        this.user = user;
        this.text = text;
    }

    public User getUser() {
        return this.user;
    }
    public String getText() {
        return this.text;
    }

    @Override
    public String toString() {
        return "Message: " + '\n' +
                "User: " + user.getName() + '\n' +
                "Message Id: " + messageId + '\n' +
                "Message: " + text + '\n' +
                "Signature: " + user.getSignature() + '\n';
    }
}
