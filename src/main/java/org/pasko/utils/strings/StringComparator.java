/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pasko.utils.strings;

import org.pasko.utils.strings.LanguageUtils;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author marcin
 */
public class StringComparator {
    public static boolean comparare(String item, String word) {
        final String to = LanguageUtils.normalize(item);
        final String from = LanguageUtils.normalize(word);
        return to.contains(from);
    }
    
    public static boolean comparare(String item, Pattern regex) {
        final String to = LanguageUtils.normalize(item);
        Matcher m = regex.matcher(to);
        return m.find();
    }
}
