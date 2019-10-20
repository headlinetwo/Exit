package de.headlinetwo.exit.util;

/**
 * Created by headlinetwo on 15.01.18.
 */

public class LevelProgressUtil {

    /**
     * Calculates the current progress a user has done on a certain level. (For informative purposes)
     *
     * @param usedSwipes the number of swipes used to solve the level by the user
     * @param minSwipes the absolute minimum of swipes required to solve the level
     * @return the users progress ranging from 0 to 1.0, where 1.0 equals 100% perfectly solved levels
     */
    public static float getProgress(int usedSwipes, int minSwipes) {
        if (usedSwipes <= minSwipes) return 1;
        else {
            int distanceToUltimateGoal = usedSwipes - minSwipes;
            return 1 - (float) distanceToUltimateGoal / usedSwipes;
        }
    }
}