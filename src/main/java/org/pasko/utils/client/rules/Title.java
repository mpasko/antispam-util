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
public class Title extends StringRule{

    @Override
    protected String textToExamine(Message message) {
        return message.getSubject();
    }
    
}
