/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pasko.utils.client.rules;

import org.pasko.utils.client.Message;

/**
 *
 * @author marcin
 */
public abstract class Rule {
    private String destiny;
    public abstract boolean matches(Message message);

    public Rule moveTo(String destiny){
        this.destiny = destiny;
        return this;
    }
    
    public String getDestination() {
        return destiny;
    }
}
