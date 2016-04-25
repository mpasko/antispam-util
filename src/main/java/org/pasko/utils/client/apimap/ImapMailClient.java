/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pasko.utils.client.apimap;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.imap.IMAPClient;
import org.apache.commons.net.imap.IMAPSClient;
import org.pasko.utils.client.MailClient;
import org.pasko.utils.client.Message;

/**
 *
 * @author marcin
 */
public class ImapMailClient extends MailClient {
    
    private IMAPClient imap;

    @Override
    public void connect() {
        imap = new IMAPSClient(config.protocol, true);
        imap.setDefaultPort(config.port);
        imap.setDefaultTimeout(60000);
//        imap.addProtocolCommandListener(new PrintCommandListener(System.out, true));
        try {
            imap.connect(config.server);
        } catch (IOException ex) {
            Logger.getLogger(ImapMailClient.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    @Override
    public List<Message> retrieveMessages() {
        List<Message> messageList = new ArrayList<Message>();
        try
        {
            if (!imap.login(config.username, config.password))
            {
                System.err.println("Could not login to server. Check password.");
                imap.disconnect();
                System.exit(3);
            }

            imap.setSoTimeout(6000);

            imap.capability();

            imap.select("inbox");

            imap.examine("inbox");
            
            System.out.println(imap.getReplyStrings());
            
            imap.status("inbox", new String[]{"MESSAGES"});
            
            System.out.println(imap.getReplyStrings());

            imap.logout();
            imap.disconnect();
            return messageList;
        }
        catch (IOException e)
        {
            System.out.println(imap.getReplyString());
            e.printStackTrace();
            System.exit(10);
            return null;
        }
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
