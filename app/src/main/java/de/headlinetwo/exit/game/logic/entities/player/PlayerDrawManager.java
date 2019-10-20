package de.headlinetwo.exit.game.logic.entities.player;

import android.graphics.Paint;

import de.headlinetwo.exit.game.GameConstants;
import de.headlinetwo.exit.game.logic.entities.EntityBody;
import de.headlinetwo.exit.game.logic.entities.player.drawmodel.playerbodypartmovement.AbstractPlayerBodyPartMovement;
import de.headlinetwo.exit.game.logic.entities.player.drawmodel.playerbodypartmovement.PlayerTailMoveForward;
import de.headlinetwo.exit.game.logic.entities.player.drawmodel.playerbodypartmovement.PlayerTailSuckIntoPortal;
import de.headlinetwo.exit.util.GridUtil;
import de.headlinetwo.exit.util.Point;
import de.headlinetwo.exit.util.Rectangle;
import de.headlinetwo.exit.util.direction.CardinalDirection;
import de.headlinetwo.exit.util.direction.DirectionUtil;

public class PlayerDrawManager {

    private Paint headPaint;
    private Paint bodyPaint;

    public PlayerDrawManager(PlayerType playerType) {
        headPaint = new Paint();
        headPaint.setColor(playerType.getHeadColor());

        bodyPaint = new Paint();
        bodyPaint.setColor(playerType.getBodyColor());
    }

    /**
     * Calculates the type of tail movement that the player-snake performs as soon as the
     * player-snake moves its head.
     * @param player the player-snake that is going to move its head
     * @return the player body part movement describing the type of movement that the tail of this
     * player-snake is going to perform on the nex head movement.
     */
    public AbstractPlayerBodyPartMovement getNextTailMovement(Player player) {
        //this check assumes that no portal blocks are placed right next to each other!
        if (GridUtil.isConnectedCardinal(player.getBody().getTail(), player.getBody().get(player.getBody().getTailIndex() - 1))) {
            return new PlayerTailMoveForward(player);
        }
        else { //this kind of movement is currently only possible via portal blocks
            return new PlayerTailSuckIntoPortal(player);
        }
    }

    /**
     * @param gridX the x-coordinate of the player-snakes head
     * @param gridY the y-coordinate ot the player-snakes head
     * @return the rectangle outlining the player-snakes head
     */
    public Rectangle getHeadRectangle(int gridX, int gridY) {
        return new Rectangle(
                gridX + GameConstants.FIVE_PERCENT,
                gridY + GameConstants.FIVE_PERCENT,
                gridX + GameConstants.NINETY_FIVE_PERCENT,
                gridY + GameConstants.NINETY_FIVE_PERCENT
        );
    }

    /**
     * Calculates a scaled rectangle outlining the player-snakes head
     *
     * @param gridX the x-coordinate of the player-snakes head
     * @param gridY the y-coordinate of the player-snakes head
     * @param scale the scale (=1 for no scale, =0.5 for half, 2=double) factor
     * @return he rectangle outlining the player-snakes head
     */
    public Rectangle getHeadRectangle(int gridX, int gridY, float scale) {
        return new Rectangle(
                gridX + 0.5f - (GameConstants.FORTY_FIVE_PERCENT * scale),
                gridY + 0.5f - (GameConstants.FORTY_FIVE_PERCENT * scale),
                gridX + 0.5f + (GameConstants.FORTY_FIVE_PERCENT * scale),
                gridY + + 0.5f + (GameConstants.FORTY_FIVE_PERCENT * scale)
        );
    }

    /**
     * Updates the coordinates of a player-snakes head rectangle according to the given values
     * @param gridX the new x-coordinate of the player-snakes head
     * @param gridY the new y-coordinate of the player-snakes head
     * @param rectangle the updated rectangle outlining the player-snakes head
     */
    public void updateHeadRectangle(int gridX, int gridY, Rectangle rectangle) {
        rectangle.set(
                gridX + GameConstants.FIVE_PERCENT,
                gridY + GameConstants.FIVE_PERCENT,
                gridX + GameConstants.NINETY_FIVE_PERCENT,
                gridY + GameConstants.NINETY_FIVE_PERCENT
        );
    }

    /**
     * @param body the body of the player-snake
     * @return all the rectangles that indicate the outlining of the player-snakes body
     * (except head and tail)
     */
    public Rectangle[] getBodyRectangles(EntityBody body) {
        if (body.size() <= 1) return new Rectangle[0]; //no body, only one head block

        Rectangle[] rectangles = new Rectangle[body.size() - 2]; //no head and no tail
        for (int index = body.size() - 2; index >= 1; index--) {
            Point currentPoint = body.get(index);
            Point nextPoint = body.get(index - 1);

            //this check assumes that no portal blocks are placed right next to each other!
            if (GridUtil.isConnectedCardinal(currentPoint, nextPoint)) {
                CardinalDirection direction = (CardinalDirection) DirectionUtil.getDirection(currentPoint.getX(), currentPoint.getY(), nextPoint.getX(), nextPoint.getY());
                rectangles[index - 1] = getRectangle(currentPoint.getX(), currentPoint.getY(), direction);
            }
            else { //e.g. portals -> no direct connection between the blocks
                rectangles[index -1] = getSquare(currentPoint.getX(), currentPoint.getY());
            }
        }

        return rectangles;
    }

    /**
     *
     * @param body the body of the player-snake
     * @return the rectangle outlining the player-snakes tail
     */
    public Rectangle getTailRectangle(EntityBody body) {
        int gridX = body.getTail().getX();
        int gridY = body.getTail().getY();

        //this check assumes that no portal blocks are placed right next to each other!
        if (GridUtil.isConnectedCardinal(body.getTail(), body.get(body.getTailIndex() - 1))) {
            CardinalDirection direction = (CardinalDirection) DirectionUtil.getDirection(gridX, gridY, body.get(body.getTailIndex() - 1).getX(), body.get(body.getTailIndex() - 1).getY());

            return getRectangle(gridX, gridY, direction);
        }
        else return getSquare(gridX, gridY);
    }

    /**
     * A rectangle with a ten percent gap on the sides orthogonal to the given direction
     * @param gridX the x-coordinate of this rectangle
     * @param gridY the y-coordinate of this rectangle
     * @param direction the direction this rectangle is facing
     * @return the calculated rectangle with a border
     */
    public Rectangle getRectangle(int gridX, int gridY, CardinalDirection direction) {
        return new Rectangle(
                gridX + (direction == CardinalDirection.WEST ? -GameConstants.TEN_PERCENT : GameConstants.TEN_PERCENT),
                gridY + (direction == CardinalDirection.NORTH ? -GameConstants.TEN_PERCENT : GameConstants.TEN_PERCENT),
                (gridX + 1) + (direction == CardinalDirection.EAST ? GameConstants.TEN_PERCENT : -GameConstants.TEN_PERCENT),
                (gridY + 1) + (direction == CardinalDirection.SOUTH ? GameConstants.TEN_PERCENT : -GameConstants.TEN_PERCENT)
        );
    }

    /**
     * @param gridX the x-coordinate of this rectangle
     * @param gridY the y-coordinate of this rectangle
     * @return a square rectangle
     */
    public Rectangle getSquare(int gridX, int gridY) {
        return new Rectangle(
                gridX + GameConstants.TEN_PERCENT,
                gridY + GameConstants.TEN_PERCENT,
                (gridX + 1) - GameConstants.TEN_PERCENT,
                (gridY + 1) - GameConstants.TEN_PERCENT);
    }

    /**
     * A scaled square rectangle
     *
     * @param gridX the x-coordinate of this rectangle
     * @param gridY the y-coordinate of this rectangle
     * @param scale the scale factor
     * @return the scaled square rectangle
     */
    public Rectangle getSquare(int gridX, int gridY, float scale) {
        return new Rectangle(
                gridX + GameConstants.FIFTY_PERCENT - (GameConstants.FORTY_PERCENT * scale),
                gridY + GameConstants.FIFTY_PERCENT - (GameConstants.FORTY_PERCENT * scale),
                gridX + GameConstants.FIFTY_PERCENT + (GameConstants.FORTY_PERCENT * scale),
                gridY + + GameConstants.FIFTY_PERCENT + (GameConstants.FORTY_PERCENT * scale)
        );
    }

    /**
     * @param progress the progress from 0 to 1
     * @return the accelerator value, that slowly increases and gets faster until it reaches the
     * tipping point at progress = 0.5 and then slowly slows down.
     * sin curve
     */
    public float getMoveAccelerator(float progress) {
        return (float) ((Math.sin((Math.PI * (progress - 0.5f))) + 1) * 0.5f);
    }

    /**
     * @return the paint used to draw a player-snakes head on the screen
     */
    public Paint getHeadPaint() {
        return headPaint;
    }

    /**
     * @return the paint used to draw a player-snakes body on the screen
     */
    public Paint getBodyPaint() {
        return bodyPaint;
    }
}