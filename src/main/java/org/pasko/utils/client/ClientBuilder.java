/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pasko.utils.client;

import org.pasko.utils.client.pop3.Pop3MailClient;
import org.pasko.utils.client.jmimap.JMImapClient;

/**
 *
 * @author marcin
 */
public class ClientBuilder {
    
    private ClientConfig prototype = new ClientConfig();
    
    public static ClientBuilder createClient(){
        return new ClientBuilder();
    }
    private MailClient engine;
    
    public ClientBuilder forUser(String username){
        prototype.setUsername(username);
        return this;
    }
    
    public ClientBuilder connectingWith(String server){
        prototype.setServer(server);
        return this;
    }
    
    public ClientBuilder atPort(int port){
        prototype.setPort(port);
        return this;
    }
    
    public ClientBuilder usingPassword(String pass){
        prototype.setPassword(pass);
        return this;
    }
    
    public ClientBuilder usingProtocol(String proto){
        prototype.setProtocol(proto);
        return this;
    }
    
    public ClientBuilder byPop3(){
        engine = new Pop3MailClient();
        engine.setConfig(prototype);
        prototype.setEngine(engine);
        return this;
    }
    
    public ClientBuilder byImap(){
        engine = new JMImapClient();
        engine.setConfig(prototype);
        prototype.setEngine(engine);
        return this;
    }
    
    public MailClient done(){
        return engine;
    }
}
