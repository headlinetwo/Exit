package de.headlinetwo.exit.util.direction;

/**
 * Created by headlinetwo on 11.03.18.
 */

public interface Direction {

    /**
     * @return the direction facing in the opposite direction of the current direction
     */
    Direction getOpposite();

    /**
     * @return the x-axis share of this direction. with absolute value 1
     */
    int getAddX();

    /**
     * @return the y-axis share of this direction. with absolute value 1
     */
    int getAddY();
}