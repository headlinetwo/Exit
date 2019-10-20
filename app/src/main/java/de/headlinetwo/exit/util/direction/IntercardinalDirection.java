package de.headlinetwo.exit.util.direction;

/**
 * Created by headlinetwo on 11.03.18.
 */

public enum IntercardinalDirection implements Direction {

    NORTH_EAST(1, -1),
    SOUTH_EAST(1, 1),
    SOUTH_WEST(-1, 1),
    NORTH_WEST(-1, -1);

    private int addX;
    private int addY;

    /**
     * @param addX the x-axis share of this direction
     * @param addY the y-axis share of this direction
     */
    IntercardinalDirection(int addX, int addY) {
        this.addX = addX;
        this.addY = addY;
    }

    @Override
    public IntercardinalDirection getOpposite() {
        return values()[(ordinal() + 2) % (values().length)];
    }

    @Override
    public int getAddX() {
        return addX;
    }

    @Override
    public int getAddY() {
        return addY;
    }
}
