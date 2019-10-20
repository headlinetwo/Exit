package de.headlinetwo.exit.util;

import android.graphics.Paint;

import java.util.ArrayList;

public class LevelHintTextBoxTextSplitter {

    /**
     * Splits a single line string text into multiple string lines
     *
     * @param input the text to split into multiple lines
     * @param width the width of the text box in pixel
     * @param textPaint the paint used to display the text
     * @return a string array with all lines to display within the level hint box
     */
    public static String[] split(String input, int width, Paint textPaint) {
        ArrayList<String> output = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder(); //reassemble the words

        for (String word : input.split(" ")) { //split after each word
            stringBuilder.append(word + " ");

            if (textPaint.measureText(stringBuilder.toString()) > width) {
                stringBuilder.delete(stringBuilder.length() - word.length() - 1, stringBuilder.length());
                output.add(stringBuilder.toString());

                stringBuilder.delete(0, stringBuilder.length());
                stringBuilder.append(word + " ");
            }
        }

        if (stringBuilder.length() > 0) output.add(stringBuilder.toString());

        return output.toArray(new String[output.size()]);
    }
}