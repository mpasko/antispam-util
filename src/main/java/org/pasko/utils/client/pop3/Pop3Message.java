/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pasko.utils.client.pop3;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;
import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;
import org.pasko.utils.client.Message;
import org.pasko.utils.client.exceptions.MessageHeaderError;

/**
 *
 * @author marcin
 */
public class Pop3Message extends Message{
    private POP3MessageInfo msginfo;
    private final POP3Client pop3;

    public Pop3Message(POP3Client pop3, POP3MessageInfo msginfo){
        this.msginfo = msginfo;
        this.pop3 = pop3;
    }
    
    public void parse() throws IOException, MessageHeaderError {
        BufferedReader reader = (BufferedReader) pop3.retrieveMessageTop(msginfo.number, 0);
        if (reader == null) {
            throw new MessageHeaderError();
        }
        String line;
        while ((line = reader.readLine()) != null) {
            String lower = line.toLowerCase(Locale.getDefault());
            if (lower.startsWith("from: ")) {
                from = line.substring(6).trim();
            } else if (lower.startsWith("subject: ")) {
                subject = line.substring(9).trim();
            } else if (lower.startsWith("to: ")) {
                to = line.substring(4).trim();
            }
        }
    }

    void printMessageInfo() throws IOException {
        final String summary = new StringBuilder().append(Integer.toString(msginfo.number)).append(" From: ").append(this.getFrom()).append(" To: ").append(this.getTo()).append(" Subject: ").append(this.getSubject()).toString();
        System.out.println(summary);
    }

    int getMessageId() {
        return this.msginfo.number;
    }
    
}
