package de.headlinetwo.exit.util;

import de.headlinetwo.exit.util.direction.CardinalDirection;

/**
 * Created by headlinetwo on 29.12.17.
 */

public class Rectangle {

    private float left; //the left side of this rectangle
    private float top; //the top side of this rectangle
    private float right; //the right side of this rectangle
    private float bottom; //the bottom side of this rectangle

    /**
     * @param left value of the rectangles left side
     * @param top value of the rectangles top side
     * @param right value of the rectangles right side
     * @param bottom value of the rectangles bottom side
     */
    public Rectangle(float left, float top, float right, float bottom) {
        set(left, top, right, bottom);
    }

    /**
     * @param direction the side of which to get the rectangles value from
     * @return the value associated with the given direction of this rectangle
     */
    public float get(CardinalDirection direction) {
        if (direction == CardinalDirection.WEST) return left;
        else if (direction == CardinalDirection.NORTH) return top;
        else if (direction == CardinalDirection.EAST) return right;
        else if (direction == CardinalDirection.SOUTH) return bottom;
        return 0;
    }

    /**
     * Updates the respective sides values of this rectangle.
     * The left side has to be smaller (or equal) than the right side
     * The top bottom side has to be smaller (or equal) than the top side
     *
     * @param left new value of the rectangles left side
     * @param top new value of the rectangles top side
     * @param right new value of the rectangles right side
     * @param bottom new value of the rectangles bottom side
     */
    public void set(float left, float top, float right, float bottom) {
        this.left = left < right ? left : right; //left is always smaller than right
        this.top = top < bottom ? top : bottom; //top is always smaller than bottom
        this.right = right > left ? right : left; //right is always greater than left
        this.bottom = bottom > top ? bottom : top; //bottom is always greater than top

        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public void set(CardinalDirection side, float value) {
        if (side == CardinalDirection.WEST) left = value;
        else if (side == CardinalDirection.NORTH) top = value;
        else if (side == CardinalDirection.EAST) right = value;
        else if (side == CardinalDirection.SOUTH) bottom = value;
    }

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public float getRight() {
        return right;
    }

    public float getBottom() {
        return bottom;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rectangle rectangle = (Rectangle) o;

        if (Float.compare(rectangle.left, left) != 0) return false;
        if (Float.compare(rectangle.top, top) != 0) return false;
        if (Float.compare(rectangle.right, right) != 0) return false;
        return Float.compare(rectangle.bottom, bottom) == 0;
    }

    @Override
    public int hashCode() {
        int result = (left != +0.0f ? Float.floatToIntBits(left) : 0);
        result = 31 * result + (top != +0.0f ? Float.floatToIntBits(top) : 0);
        result = 31 * result + (right != +0.0f ? Float.floatToIntBits(right) : 0);
        result = 31 * result + (bottom != +0.0f ? Float.floatToIntBits(bottom) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "left=" + left +
                ", top=" + top +
                ", right=" + right +
                ", bottom=" + bottom +
                '}';
    }
}