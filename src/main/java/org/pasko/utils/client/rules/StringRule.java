/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pasko.utils.client.rules;

import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Pattern;
import org.pasko.utils.client.Message;
import org.pasko.utils.strings.StringComparator;

/**
 *
 * @author marcin
 */
public abstract class StringRule extends Rule{
    
    private LinkedList<String> list=null;
    private Pattern pattern=null;
    
    public StringRule contains(String word) {
        initList();
        this.list.add(word);
        return this;
    }
    
    public StringRule containsRegex(String regex) {
        this.pattern = Pattern.compile(regex);
        return this;
    }
    
    public StringRule containsOne(Collection<String> lisst) {
        initList();
        this.list.addAll(lisst);
        return this;
    }
    
    @Override
    public StringRule moveTo(String destiny) {
        return (StringRule) super.moveTo(destiny);
    }
    
    protected abstract String textToExamine(Message message);
    
    public boolean matches(Message message) {
        if (pattern!=null) {
            return StringComparator.comparare(this.textToExamine(message), pattern);
        } else if (list!=null) {
            for (String word : list) {
                boolean oneMatches = StringComparator.comparare(this.textToExamine(message), word);
                if (oneMatches) {
                    return true;
                }
            }
            return false;
        } else {
            throw new RuntimeException("Rule does not define element to compare against!");
        }
    }

    private void initList() {
        if (list == null) {
            list = new LinkedList<String>();
        }
    }
}
