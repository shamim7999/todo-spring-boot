package org.dsi.todospringboot.helper;

public class Shorter {
    public static String makeShortTheSentence(String sentence, int threshold) {
        if(sentence.length() <= threshold)
            return sentence;
        return sentence.substring(0, threshold) + " ...";
    }
}
