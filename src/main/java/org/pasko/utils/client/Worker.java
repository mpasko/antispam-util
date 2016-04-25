/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pasko.utils.client;

import java.util.LinkedList;
import java.util.List;
import org.pasko.utils.client.rules.Rule;

/**
 *
 * @author marcin
 */
public class Worker {
    public List<Rule> rules = new LinkedList<Rule>();
    
    public void addRule(Rule rule){
        this.rules.add(rule);
    }
    
    public void doJob(MailClient client){
        List<Message> messages = client.retrieveMessages();
        for (Message msg : messages) {
            for (Rule rule : rules) {
                if (rule.matches(msg)) {
                    client.move(msg, rule.getDestination());
                }
            }
        }
    }
}
