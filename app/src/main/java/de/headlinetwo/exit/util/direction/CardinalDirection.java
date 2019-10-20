package de.headlinetwo.exit.util.direction;

/**
 * Created by headlinetwo on 11.03.18.
 */

public enum CardinalDirection implements Direction {

    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0);

    private int addX;
    private int addY;

    /**
     * @param addX the x-axis share of this direction
     * @param addY the y-axis share of this direction
     */
    CardinalDirection(int addX, int addY) {
        this.addX = addX;
        this.addY = addY;
    }

    /**
     * @return {@code true} if this direction is horizontal, meaning that the {@link #addY} is equal
     * to zero, {@code false} otherwise
     */
    public boolean isHorizontal() {
        return addY == 0;
    }

    /**
     * @return {@code true} if this direction is vertical, meaning that the {@link #addX} is equal
     * to zero, {@code false} otherwise
     */
    public boolean isVertical() {
        return addX == 0;
    }

    @Override
    public CardinalDirection getOpposite() {
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