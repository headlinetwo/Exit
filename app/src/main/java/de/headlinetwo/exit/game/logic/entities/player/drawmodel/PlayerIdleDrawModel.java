package de.headlinetwo.exit.game.logic.entities.player.drawmodel;

import android.graphics.Paint;

import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.util.Point;
import de.headlinetwo.exit.util.Rectangle;

public class PlayerIdleDrawModel extends AbstractPlayerDrawModel {

    private Rectangle headRectangle = new Rectangle(0, 0, 0, 0);
    private Rectangle[] bodyRectangles;
    private Rectangle tailRectangle;

    private Paint headPaint;
    private Paint bodyPaint;

    public PlayerIdleDrawModel(Player player) {
        super(player);

        updateHeadRectangle();
        updateBodyRectangles();
        updateTailRectangle();

        headPaint = new Paint(player.getDrawManager().getHeadPaint());
        bodyPaint = new Paint(player.getDrawManager().getBodyPaint());
    }

    @Override
    public void draw(GamePanel panel) {
        panel.fillRect(tailRectangle, bodyPaint);
        for (Rectangle rectangle : bodyRectangles) panel.fillRect(rectangle, bodyPaint);
        panel.fillRect(headRectangle, headPaint);
    }

    private void updateHeadRectangle() { //the head rectangle is a square centered inside its current grid block with a 5% margin on each side
        Point headCoordinate = getPlayer().getBody().getHead();
        getPlayer().getDrawManager().updateHeadRectangle(headCoordinate.getX(), headCoordinate.getY(), headRectangle);
    }

    private void updateBodyRectangles() {
        bodyRectangles = getPlayer().getDrawManager().getBodyRectangles(getPlayer().getBody());
    }

    private void updateTailRectangle() {
        tailRectangle = getPlayer().getDrawManager().getTailRectangle(getPlayer().getBody());
    }

    public void setAlpha(int alpha) {
        bodyPaint.setAlpha(alpha);
    }
}