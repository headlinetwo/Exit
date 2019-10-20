package de.headlinetwo.exit.game.logic.entities.player.drawmodel;

import android.graphics.Paint;

import de.headlinetwo.exit.game.GameConstants;
import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.util.Callback;
import de.headlinetwo.exit.util.Rectangle;

public class PlayerEnterGoalDrawModel extends AnimateablePlayerDrawModel {

    private PlayerIdleDrawModel playerIdleDrawModel;

    private Rectangle goldSquare;
    private Paint goalPaint;

    public PlayerEnterGoalDrawModel(Player player, Callback animationFinishCallback) {
        super(player, animationFinishCallback);

        playerIdleDrawModel = new PlayerIdleDrawModel(player);

        goldSquare = new Rectangle(
                player.getBody().getHead().getX() + GameConstants.THIRTY_PERCENT,
                player.getBody().getHead().getY() + GameConstants.THIRTY_PERCENT,
                player.getBody().getHead().getX() + GameConstants.SEVENTY_PERCENT,
                player.getBody().getHead().getY() + GameConstants.SEVENTY_PERCENT
        );

        goalPaint = new Paint();
        goalPaint.setColor(GameConstants.FIELD_BLOCK_COLOR);
        goalPaint.setAlpha(0);
    }

    @Override
    public void draw(GamePanel panel) {
        playerIdleDrawModel.draw(panel);

        panel.fillRect(goldSquare, goalPaint);
    }

    @Override
    public void tick() {
        super.tick();

        goalPaint.setAlpha((int) (getProgress() * 255));
        playerIdleDrawModel.setAlpha((int) (255 - (getProgress() * 100)));
    }
}