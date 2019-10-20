package de.headlinetwo.exit.game.logic.entities.player.drawmodel;

import android.graphics.Paint;

import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.util.Callback;
import de.headlinetwo.exit.util.GridUtil;
import de.headlinetwo.exit.util.Point;
import de.headlinetwo.exit.util.Rectangle;
import de.headlinetwo.exit.util.direction.CardinalDirection;
import de.headlinetwo.exit.util.direction.DirectionUtil;

public class PlayerAppendTailBlockDrawModel extends AnimateablePlayerDrawModel {

    private Rectangle headRectangle;
    private Rectangle[] bodyRectangles;
    private Rectangle tailRectangle;

    private Rectangle fadeInRectangle;
    private Paint fadeInPaint;

    public PlayerAppendTailBlockDrawModel(Player player, int fadeInX, int fadeInY, Callback animationFinishCallback) {
        super(player, animationFinishCallback);

        headRectangle = player.getDrawManager().getHeadRectangle(player.getBody().getHead().getX(), player.getBody().getHead().getY());
        bodyRectangles = player.getDrawManager().getBodyRectangles(player.getBody());
        tailRectangle = player.getDrawManager().getTailRectangle(player.getBody());

        if (GridUtil.isConnectedCardinal(new Point(fadeInX, fadeInY), player.getBody().getTail())) {
            fadeInRectangle = player.getDrawManager().getRectangle(fadeInX, fadeInY, (CardinalDirection) DirectionUtil.getDirection(fadeInX, fadeInY, player.getBody().getTail().getX(), player.getBody().getTail().getY()));
        }
        else fadeInRectangle = player.getDrawManager().getSquare(fadeInX, fadeInY);


        fadeInPaint = new Paint();
        fadeInPaint.setColor(player.getDrawManager().getBodyPaint().getColor());
        fadeInPaint.setAlpha(0);
    }

    @Override
    public void draw(GamePanel panel) {
        panel.fillRect(fadeInRectangle, fadeInPaint);
        panel.fillRect(tailRectangle, getPlayer().getDrawManager().getBodyPaint());
        for (Rectangle rectangle : bodyRectangles) panel.fillRect(rectangle, getPlayer().getDrawManager().getBodyPaint());
        panel.fillRect(headRectangle, getPlayer().getDrawManager().getHeadPaint());
    }

    @Override
    public void tick() {
        super.tick();

        fadeInPaint.setAlpha((int) (getProgress() * 255));
    }
}
