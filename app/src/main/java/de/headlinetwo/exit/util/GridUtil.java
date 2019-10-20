package de.headlinetwo.exit.util;

public class GridUtil {

    /**
     * Checks whether or not {@code a} and {@code b} are connected by a path with length 1 in south, east, west or north direction
     *
     * @param a the first point
     * @param b the second point
     * @return whether {@code a} and {@code b} are connected by a path with length 1 in south, east, west or north direction
     */
    public static boolean isConnectedCardinal(Point a, Point b) {
        int diffX = Math.abs(a.getX() - b.getX());
        int diffY = Math.abs(a.getY() - b.getY());

        return (diffX == 0 && diffY == 1) || (diffX == 1 && diffY == 0);
    }
}