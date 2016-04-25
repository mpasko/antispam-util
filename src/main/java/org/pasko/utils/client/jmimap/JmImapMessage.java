/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pasko.utils.client.jmimap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import org.pasko.utils.client.Message;

/**
 *
 * @author marcin
 */
public class JmImapMessage extends Message {
    final javax.mail.Message imapMessage;
    
    public JmImapMessage(javax.mail.Message message) {
        this.imapMessage = message;
    }
    
    public void parse() throws MessagingException {
        this.from = addrToStr(imapMessage.getFrom());
        this.to = addrToStr(imapMessage.getRecipients(javax.mail.Message.RecipientType.TO));
        this.subject = imapMessage.getSubject();
        try {
            this.content = removeHtmlTags(obtainStringContent(imapMessage));
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void printMessageInfo() throws IOException {
        final String summary = new StringBuilder().append(" From: ").append(this.getFrom()).append(" To: ").append(this.getTo()).append(" Subject: ").append(this.getSubject()).toString();
        System.out.println(summary);
    }

    private String addrToStr(Address[] addresses) {
        if (addresses == null) {
            return "";
        }
        StringBuilder build = new StringBuilder();
        for (Address addr : addresses) {
            build.append(addr.toString()).append("; ");
        }
        return build.toString();
    }
    
    private String obtainStringContent(javax.mail.Message imapMessage) throws MessagingException, IOException{
        Object contentObject = imapMessage.getContent();
        if (imapMessage.isMimeType("text/plain") || contentObject instanceof String) {
            return contentObject.toString();
        } else if (imapMessage.isMimeType("multipart/*") || contentObject instanceof Multipart) {
            Multipart multipart = (Multipart) contentObject;
            OutputStream baos = new ByteArrayOutputStream();
            multipart.writeTo(baos);
            return baos.toString();
        }
        return "";
    }
    
    private String removeHtmlTags(String input) {
        return input.replaceAll("<[^>]>", "");
    }
}
