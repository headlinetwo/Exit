package de.headlinetwo.exit.util;

import android.content.res.Resources;

/**
 * Created by headlinetwo on 24.10.17.
 */
public class MathUtil {

    /**
     * Returns the closes value that is within the specified range, both min and max are included.
     *
     * @param value the value that needs to be checked
     * @param min lower limit
     * @param max upper limit
     * @return {@code min} if {@code value} < {@code min}, {@code max} if {@code value} > {@code max}, otherwise {@code value}
     */
    public static int range(int value, int min, int max) {
        if (value < min) return min;
        else if (value > max) return max;

        return value;
    }

    /**
     * Converts density-independent pixels (dp or dip) to pixel (px, the actual pixels on the screen)
     *
     * @param dp the density-independent pixels
     * @return the pixel
     */
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}