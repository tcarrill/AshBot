package org.moonlab.ash;

/**
 * Created by thomas on 9/12/14.
 */
public class Utility {
    public static String buildStringFromArray(String[] words, int startIndex) {
        StringBuilder builder = new StringBuilder();

        for (int i = startIndex; i < words.length; i++) {
            builder.append(words[i]);
            if (i + 1 < words.length) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }
}
