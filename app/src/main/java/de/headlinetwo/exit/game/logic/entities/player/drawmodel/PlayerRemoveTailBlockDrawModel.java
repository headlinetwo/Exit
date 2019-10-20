package de.headlinetwo.exit.game.logic.entities.player.drawmodel;

import android.graphics.Paint;

import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.util.Callback;
import de.headlinetwo.exit.util.Rectangle;

public class PlayerRemoveTailBlockDrawModel extends AnimateablePlayerDrawModel {

    private Rectangle headRectangle;
    private Rectangle[] bodyRectangles;

    private Rectangle fadeOutRectangle;
    private Paint fadeOutPaint;

    public PlayerRemoveTailBlockDrawModel(Player player, Callback animationFinishCallback) {
        super(player, animationFinishCallback);

        headRectangle = player.getDrawManager().getHeadRectangle(player.getBody().getHead().getX(), player.getBody().getHead().getY());
        bodyRectangles = player.getDrawManager().getBodyRectangles(player.getBody());
        fadeOutRectangle = player.getDrawManager().getTailRectangle(player.getBody());

        fadeOutPaint = new Paint();
        fadeOutPaint.setColor(player.getDrawManager().getBodyPaint().getColor());
        fadeOutPaint.setAlpha(255);
    }

    @Override
    public void draw(GamePanel panel) {
        panel.fillRect(fadeOutRectangle, fadeOutPaint);
        for (Rectangle rectangle : bodyRectangles) panel.fillRect(rectangle, getPlayer().getDrawManager().getBodyPaint());
        panel.fillRect(headRectangle, getPlayer().getDrawManager().getHeadPaint());
    }

    @Override
    public void tick() {
        super.tick();

        fadeOutPaint.setAlpha((int) ((1 - getProgress()) * 255));
    }
}
