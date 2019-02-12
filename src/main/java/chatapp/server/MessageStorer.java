package chatapp.server;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class MessageStorer implements Runnable {
    private final BlockingQueue<Message> receivedMessages;
    private List<Message> messages;

    MessageStorer(BlockingQueue<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    @Override
    public void run() {
        try {
            Message msg;
            while (!Thread.currentThread().isInterrupted()) {
                msg = receivedMessages.take();
                messages.add(msg);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            final List<Message> remainingMsgs = new LinkedList<>();
            receivedMessages.drainTo(remainingMsgs);
            messages.addAll(remainingMsgs);
        }
    }
}
