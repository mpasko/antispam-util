/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pasko.utils.strings;

import java.util.Locale;

/**
 *
 * @author marcin
 */
public class LanguageUtils {
    public static String removeNationalLetters(String input){
        String output = input.toLowerCase();
        output=normalizeHardSpace(output);
        output=getRidNormal(output);
        output=getRidUtf8Small(output);
        output=getRidUtf8Big(output);
        output=getRidIsoSmall(output);
        output=getRidIsoBig(output);
        return output;
    }
    
    public static String normalize(String input) {
        return removeNationalLetters(input).toLowerCase(Locale.getDefault());
    }
    
    public static String proprocessRegex(String regex) {
        String output = regex.toLowerCase(Locale.getDefault());
        output=normalize(output);
        output=output.replace("\\s", "(\\s|_)");
        return output;
    }

    private static String getRidNormal(String output) {
        output=output.replace("ę", "e");
        output=output.replace("ą", "a");
        output=output.replace("ó", "o");
        output=output.replace("ł", "l");
        output=output.replace("ń", "n");
        output=output.replace("ś", "s");
        output=output.replace("ć", "c");
        output=output.replace("ź", "z");
        output=output.replace("ż", "z");
        return output;
    }

    private static String getRidIsoSmall(String output) {
        output=output.replace("=ea", "e");
        output=output.replace("=b1", "a");
        output=output.replace("=f3", "o");
        output=output.replace("=b3", "l");
        output=output.replace("=f1", "n");
        output=output.replace("=b6", "s");
        output=output.replace("=e6", "c");
        output=output.replace("=bf", "z");
        output=output.replace("=bc", "z");
        return output;
    }
    
    private static String getRidIsoBig(String output) {
        output=output.replace("=ca", "e");
        output=output.replace("=a1", "a");
        output=output.replace("=d3", "o");
        output=output.replace("=a3", "l");
        output=output.replace("=d1", "n");
        output=output.replace("=a6", "s");
        output=output.replace("=c6", "c");
        output=output.replace("=af", "z");
        output=output.replace("=ac", "z");
        return output;
    }

    private static String getRidUtf8Small(String output) {
        output=output.replace("=c4=99", "e");
        output=output.replace("=c4=85", "a");
        output=output.replace("=c3=b3", "o");
        output=output.replace("=c5=82", "l");
        output=output.replace("=c5=84", "n");
        output=output.replace("=c5=9b", "s");
        output=output.replace("=c4=87", "c");
        output=output.replace("=c5=bc", "z");
        output=output.replace("=c5=ba", "z");
        return output;
    }
    
    private static String getRidUtf8Big(String output) {
        output=output.replace("=c4=98", "e");
        output=output.replace("=c4=84", "a");
        output=output.replace("=c3=93", "o");
        output=output.replace("=c5=81", "l");
        output=output.replace("=c5=83", "n");
        output=output.replace("=c5=9a", "s");
        output=output.replace("=c4=86", "c");
        output=output.replace("=c5=b9", "z");
        output=output.replace("=c5=bb", "z");
        return output;
    }

    private static String normalizeHardSpace(String output) {
        output=output.replace("=20", "_");
        output=output.replace(" ", "_");
        return output;
    }
}
