package de.headlinetwo.exit.game.logic;

import android.view.MotionEvent;

import de.headlinetwo.exit.game.GameHandler;
import de.headlinetwo.exit.util.Point;
import de.headlinetwo.exit.util.direction.CardinalDirection;

/**
 * Created by headlinetwo on 01.12.17.
 */

public class SwipeHandler {

    private GameHandler gameHandler;

    private Point downLocation = null; //the location of the last down press on the screen

    public SwipeHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    /**
     * Called after each onTouchEvent(MotionEvent e) on the screen by the user.
     * Used to calculate the any swipes by the user
     * @param e the motion event on the screen itself
     */
    public void onEventOccur(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            downLocation = new Point((int) e.getX(), (int) e.getY());
        }
        else if (e.getAction() == MotionEvent.ACTION_UP) {
            if (downLocation != null) {
                int xOffset = (int) (downLocation.getX() - e.getX());
                int yOffset = (int) (downLocation.getY() - e.getY());

                /*
                the minimum distance, either vertical or horizontal from the downLocation,
                required to travel across the screen whilst pressing down to trigger an event
                 */
                int minSwipeDistance = (int) (gameHandler.getGraphicsPanel().getScreenWidth() / 7d);

                int absXOffset = Math.abs(xOffset);
                int absYOffset = Math.abs(yOffset);

                if (absXOffset > absYOffset && absXOffset > minSwipeDistance) {
                    if (xOffset > 0) gameHandler.handleMove(CardinalDirection.WEST);
                    else if (xOffset < 0) gameHandler.handleMove(CardinalDirection.EAST);
                }
                else if (absYOffset > absXOffset && absYOffset > minSwipeDistance) {
                    if (yOffset > 0) gameHandler.handleMove(CardinalDirection.NORTH);
                    else if (yOffset < 0) gameHandler.handleMove(CardinalDirection.SOUTH);
                }

                downLocation = null;
            }
        }
    }
}
