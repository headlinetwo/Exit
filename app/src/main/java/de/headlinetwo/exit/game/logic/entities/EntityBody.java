package de.headlinetwo.exit.game.logic.entities;

import java.util.ArrayList;
import java.util.Arrays;

import de.headlinetwo.exit.util.Point;

/**
 * Created by headlinetwo on 18.01.18.
 */

public class EntityBody extends ArrayList<Point> {

    private static final int NO_TAIL_MOVEMENT = -1;
    private static final int MINIMUM_ENTITY_LENGTH = 2;

    private int preMoveTailX = NO_TAIL_MOVEMENT; //the last x-coordinate of the tail before this entity was moved in any given direction. (required for appending a block at the tail after this entity has been moved)
    private int preMoveTailY = NO_TAIL_MOVEMENT; //the last y-coordinate of the tail before this entity was moved in any give direction.

    public EntityBody(Point... initialBody) {
        super(Arrays.asList(initialBody));
    }

    public void move(int targetX, int targetY) {
        if (isEmpty()) return; //cant move without a body

        preMoveTailX = getTail().getX();
        preMoveTailY = getTail().getY();

        if (size() > 1) { //entity consists of blocks other than the head block
            for (int index = size() - 1; index > 0; index--) {
                Point toMove = get(index);
                Point target = get(index - 1);

                toMove.setX(target.getX());
                toMove.setY(target.getY());
            }
        }

        Point head = get(getHeadIndex()); //the head of this entity
        head.setX(targetX);
        head.setY(targetY);
    }

    /**
     * Appends one point to the back of this entity. The newly appended point is placed at the
     * coordinates that were previously occupied by the tail of the entity before it was moved
     * around. if the entity has not been moved around before no point will be appended and this
     * method returns null instead of the appended point.
     * @return the appended point or {@code null} if no point was appended.
     */
    public Point appendPoint() {
        if (preMoveTailX == NO_TAIL_MOVEMENT || preMoveTailY == NO_TAIL_MOVEMENT) return null;

        Point point = new Point(preMoveTailX, preMoveTailY);
        add(point);
        return point;
    }

    /**
     * Removes the tail of this entity. All entities are required to consist of at least one head
     * and one tail point. Therefor this method does not remove any points as soon as the the entity
     * is too short.
     *
     * @return {@code null} if no point was removed or the removed point if the tail of the entity
     * has been removed
     */
    public Point removeLastPoint() {
        if (size() <= MINIMUM_ENTITY_LENGTH) return null; //entities must consist of head and one tail block.

        Point tail = getTail(); //current tail
        remove(size() - 1); //remove the current tail block
        return tail;
    }

    public int getHeadIndex() {
        return 0;
    }

    public int getTailIndex() {
        return size() - 1;
    }

    public Point getHead() {
        return get(getHeadIndex());
    }

    public Point getTail() {
        return get(getTailIndex());
    }

    /**
     * @return the x-coordinate of the tail point before this entity has been moved
     * in any given direction or {@link #NO_TAIL_MOVEMENT} in case this entity has never been
     * moved around
     */
    public int getPreMoveTailX() {
        return preMoveTailX;
    }

    /**
     * @return the y-coordinate of the tail point before this entity has been moved
     * in any given direction or {@link #NO_TAIL_MOVEMENT} n case this entity has never been
     * moved around
     */
    public int getPreMoveTailY() {
        return preMoveTailY;
    }
}