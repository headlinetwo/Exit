package de.headlinetwo.exit.game.logic.entities.player.drawmodel;

import android.graphics.Paint;

import de.headlinetwo.exit.game.GameConstants;
import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.util.Rectangle;

public class PlayerInGoalDrawModel extends PlayerIdleDrawModel {

    private Rectangle goldSquare;
    private Paint goalPaint;

    public PlayerInGoalDrawModel(Player player, int alpha) {
        super(player);

        goldSquare = new Rectangle(
                player.getBody().getHead().getX() + GameConstants.THIRTY_PERCENT,
                player.getBody().getHead().getY() + GameConstants.THIRTY_PERCENT,
                player.getBody().getHead().getX() + GameConstants.SEVENTY_PERCENT,
                player.getBody().getHead().getY() + GameConstants.SEVENTY_PERCENT
        );

        goalPaint = new Paint();
        goalPaint.setColor(GameConstants.GOAL_BLOCK_COLOR);

        setAlpha(alpha);
    }

    @Override
    public void draw(GamePanel panel) {
        super.draw(panel);

        panel.fillRect(goldSquare, goalPaint);
    }
}