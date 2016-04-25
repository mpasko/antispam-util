/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pasko.utils.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;
import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;
import org.pasko.utils.client.exceptions.MessageHeaderError;

/**
 *
 * @author marcin
 */
public class Message {
    protected String to;
    protected String from,subject,content;
    
    public Message(){
    }

    public String getTo(){
        return to;
    }
    
    public String getFrom(){
        return from;
    }
    
    public String getSubject(){
        return subject;
    }

    public String getContent() {
        return content;
    }
    
    
}
