package de.headlinetwo.exit.util.direction;

import de.headlinetwo.exit.util.MathUtil;

/**
 * Created by headlinetwo on 11.03.18.
 */

public class DirectionUtil {

    public static final Direction[] VALUES; //all available directions

    static {
        VALUES = new Direction[CardinalDirection.values().length + IntercardinalDirection.values().length];

        int index = 0;
        for (CardinalDirection direction : CardinalDirection.values()) VALUES[index++] = direction;
        for (IntercardinalDirection direction : IntercardinalDirection.values()) VALUES[index++] = direction;
    }

    /**
     * Calculates the direction required to take if you start at (fromX, fromY) and need to move
     * towards (toX, toY)
     *
     * @param fromX the start x-coordinate
     * @param fromY the start y-coordinate
     * @param toX the target x-coordinate
     * @param toY the target y-coordinate
     * @return the direction between (fromX, fromY) and (toX, toY), or {@çode null} if no
     * appropriate direction has been found
     */
    public static Direction getDirection(int fromX, int fromY, int toX, int toY) {
        int xOffset = MathUtil.range(toX - fromX, -1, 1);
        int yOffset = MathUtil.range(toY - fromY, -1, 1);

        for (Direction direction : VALUES) {
            if (direction.getAddX() == xOffset && direction.getAddY() == yOffset) return direction;
        }
        return null;
    }
}