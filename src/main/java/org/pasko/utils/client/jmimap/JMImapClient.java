/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pasko.utils.client.jmimap;

import org.pasko.utils.client.jmimap.JmImapMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import org.pasko.utils.client.MailClient;
import org.pasko.utils.client.Message;

/**
 *
 * @author marcin
 */
public class JMImapClient extends MailClient {

    private Store store;
    private Folder inbox;
    private HashMap<String,LinkedList<javax.mail.Message>> transitionMap;
    
    @Override
    public void connect() {
        Properties props = (Properties) System.getProperties().clone();
        props.put("mail.imap.auth", true);
        props.put("mail.imap.ssl.enable", true);
        props.put("mail.imap.starttls.enable", true);
        // Get a Session object
        Session session = Session.getInstance(props, null);
        //session.setDebug(true);
        transitionMap = new HashMap<String,LinkedList<javax.mail.Message>> ();
        try {
            store = session.getStore("imap");
            store.connect(config.server, config.port, config.username, config.password);
            
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(JMImapClient.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } catch (MessagingException ex) {
            Logger.getLogger(JMImapClient.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    @Override
    public List<Message> retrieveMessages() {
        List<Message> messageList = new ArrayList<Message>();
        try {
            inbox = store.getFolder("INBOX");
            inbox.open(javax.mail.Folder.READ_WRITE);
            javax.mail.Message[] messages = inbox.getMessages();
            for (javax.mail.Message message : messages){
                JmImapMessage msg = new JmImapMessage(message);
                msg.parse();
                msg.printMessageInfo();
                messageList.add(msg);
            }
            return messageList;
        } catch (MessagingException ex) {
            Logger.getLogger(JMImapClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(JMImapClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public void move(Message message, String destination) {
        javax.mail.Message imapMsg = ((JmImapMessage)message).imapMessage;
        LinkedList<javax.mail.Message> messList = transitionMap.get(destination);
        if (messList==null){
            messList = new LinkedList<javax.mail.Message>();
            transitionMap.put(destination, messList);
        }
        messList.add(imapMsg);
    }
    
    @Override
    public void flush(){
        try {
            for (String destiny : transitionMap.keySet()){
                moveGroup(transitionMap.get(destiny), destiny);
            }
            //int deleted = inbox.expunge().length;
            inbox.close(true);
            store.close();
        } catch (MessagingException ex) {
            Logger.getLogger(JMImapClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void moveGroup(LinkedList<javax.mail.Message> messList, String destination) {
        try {
            Folder dst = store.getFolder(destination);
            dst.open(javax.mail.Folder.READ_WRITE);
            javax.mail.Message[] messArray = messList.toArray(new javax.mail.Message[messList.size()]);
            inbox.copyMessages(messArray, dst);
            inbox.setFlags(messArray, new Flags(Flags.Flag.DELETED), true);
            dst.close(false);
        } catch (MessagingException ex) {
            Logger.getLogger(JMImapClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
