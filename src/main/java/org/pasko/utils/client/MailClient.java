/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pasko.utils.client;

import java.util.List;

/**
 *
 * @author marcin
 */
public abstract class MailClient {
    protected ClientConfig config;
    public void setConfig(ClientConfig config){
        this.config = config;
    }
    public abstract void connect();
    public abstract List<Message> retrieveMessages();
    public abstract void move(Message message, String destination);
    public abstract void flush();
}
