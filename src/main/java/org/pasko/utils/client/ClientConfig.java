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
public class ClientConfig {
    public String username;
    public String password;
    public String server;
    public String protocol;
    public int port;
    private MailClient engine;

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param server the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }
    
    public void setPort(int port){
        this.port = port;
    }
    
    public void setEngine(MailClient client){
        this.engine = client;
    }
    
    public void connect(){
        this.engine.connect();
    }
    
    public List<Message> retrieveMessages(){
        return this.engine.retrieveMessages();
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
