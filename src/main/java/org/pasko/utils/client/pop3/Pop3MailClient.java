/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pasko.utils.client.pop3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;
import org.pasko.utils.client.MailClient;
import org.pasko.utils.client.Message;
import org.pasko.utils.client.exceptions.MessageHeaderError;

/**
 *
 * @author marcin
 */
public class Pop3MailClient extends MailClient{
    private POP3Client pop3;

    public void connect() {
        pop3 = new POP3Client();
        pop3.setDefaultPort(config.port);
        pop3.setDefaultTimeout(60000);
        pop3.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
        try {
            pop3.connect(config.server);
        } catch (IOException e) {
            System.err.println("Could not connect to server.");
            e.printStackTrace();
            return;
        }
    }

    public List<Message> retrieveMessages() {
        List<Message> messageList = new ArrayList<Message>();
        try {
            if (!pop3.login(config.username, config.password)) {
                System.err.println("Could not login to server.  Check password.");
                pop3.disconnect();
                return null;
            }
            POP3MessageInfo[] messages = pop3.listMessages();
            if (messages == null) {
                System.err.println("Could not retrieve message list.");
                pop3.disconnect();
                return null;
            } else if (messages.length == 0) {
                System.out.println("No messages");
                pop3.logout();
                pop3.disconnect();
                return messageList;
            }
            for (POP3MessageInfo msginfo : messages) {
                try {
                    messageList.add(retrieveMessage(pop3, msginfo));
                } catch (MessageHeaderError err) {
                    System.err.println("Could not retrieve message header.");
                    pop3.disconnect();
                    return messageList;
                }
            }
            pop3.logout();
            pop3.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            return messageList;
        }
        return messageList;
    }

    public void deleteMessage(Pop3Message message) {
        try {
            pop3.deleteMessage(message.getMessageId());
        } catch (IOException ex) {
            System.err.println("Could not delete message.");
            ex.printStackTrace();
        }
    }

    private Message retrieveMessage(POP3Client pop3, POP3MessageInfo msginfo) throws IOException, MessageHeaderError {
        Pop3Message message = new Pop3Message(pop3, msginfo);
        message.parse();
        message.printMessageInfo();
        return message;
    }

    @Override
    public void move(Message message, String destination) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
