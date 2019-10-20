package de.headlinetwo.exit.game.logic.entities.player.drawmodel;

import de.headlinetwo.exit.game.GameConstants;
import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.game.logic.entities.player.drawmodel.playerbodypartmovement.AbstractPlayerBodyPartMovement;
import de.headlinetwo.exit.util.Callback;
import de.headlinetwo.exit.util.Rectangle;
import de.headlinetwo.exit.util.direction.CardinalDirection;

public class PlayerMoveDrawModel extends AnimateablePlayerDrawModel {

    private Rectangle headRectangle;
    private CardinalDirection headDirection;

    private Rectangle[] bodyRectangles;

    private Rectangle headConnector;
    private float headConnectorMovingSideStartValue;

    private AbstractPlayerBodyPartMovement tailMovement;

    public PlayerMoveDrawModel(Player player, CardinalDirection direction, Callback animationFinishCallback) {
        super(player, animationFinishCallback);

        headRectangle = player.getDrawManager().getHeadRectangle(player.getBody().getHead().getX(), player.getBody().getHead().getY());
        headDirection = direction;

        bodyRectangles = player.getDrawManager().getBodyRectangles(player.getBody());

        int headX = player.getBody().getHead().getX();
        int headY = player.getBody().getHead().getY();

        headConnector = new Rectangle(
                headX + (headDirection == CardinalDirection.WEST ? GameConstants.NINETY_PERCENT : GameConstants.TEN_PERCENT),
                headY + (headDirection == CardinalDirection.NORTH ? GameConstants.NINETY_PERCENT : GameConstants.TEN_PERCENT),
                headX + (headDirection == CardinalDirection.EAST ? GameConstants.TEN_PERCENT : GameConstants.NINETY_PERCENT),
                headY + (headDirection == CardinalDirection.SOUTH ? GameConstants.TEN_PERCENT : GameConstants.NINETY_PERCENT)
        );

        headConnectorMovingSideStartValue = headRectangle.get(headDirection);

        tailMovement = player.getDrawManager().getNextTailMovement(player);
    }

    @Override
    public void draw(GamePanel panel) {
        tailMovement.draw(panel);

        for (Rectangle rectangle : bodyRectangles) panel.fillRect(rectangle, getPlayer().getDrawManager().getBodyPaint());
        panel.fillRect(headConnector, getPlayer().getDrawManager().getBodyPaint());
        panel.fillRect(headRectangle, getDistance(getProgress()), headDirection, getPlayer().getDrawManager().getHeadPaint());
    }

    @Override
    public void tick() {
        super.tick();

        tailMovement.tick(getProgress());

        headConnector.set(headDirection, headConnectorMovingSideStartValue + (getDistance(getProgress()) * (headDirection.isVertical() ? headDirection.getAddY() : headDirection.getAddX())));
    }

    private float getDistance(float progress) {
        return (float) ((Math.sin((Math.PI * (progress - 0.5f))) + 1) * 0.5f);
    }
}