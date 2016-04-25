/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pasko.utils.client.rules;

import java.util.Locale;
import org.pasko.utils.client.Message;

/**
 *
 * @author marcin
 */
public class SentTo extends StringRule{

    @Override
    protected String textToExamine(Message message) {
        return message.getTo();
    }
    
}
