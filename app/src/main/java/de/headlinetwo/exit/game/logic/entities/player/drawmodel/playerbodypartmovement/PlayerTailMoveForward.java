package de.headlinetwo.exit.game.logic.entities.player.drawmodel.playerbodypartmovement;

import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.util.Rectangle;
import de.headlinetwo.exit.util.direction.CardinalDirection;
import de.headlinetwo.exit.util.direction.DirectionUtil;

public class PlayerTailMoveForward extends AbstractPlayerBodyPartMovement {

    private CardinalDirection tailDirection;
    private Rectangle tailRectangle;
    private float tailMovingSideStartValue;

    public PlayerTailMoveForward(Player player) {
        super(player);

        tailDirection = (CardinalDirection) DirectionUtil.getDirection(player.getBody().getTail().getX(), player.getBody().getTail().getY(), player.getBody().get(player.getBody().size() - 2).getX(), player.getBody().get(player.getBody().size() - 2).getY());
        tailRectangle = player.getDrawManager().getTailRectangle(player.getBody());
        tailMovingSideStartValue = tailRectangle.get(tailDirection.getOpposite());

        tick(0);
    }

    @Override
    public void tick(float progress) {
        tailRectangle.set(tailDirection.getOpposite(), tailMovingSideStartValue + (getPlayer().getDrawManager().getMoveAccelerator(progress) * (tailDirection.isVertical() ? tailDirection.getAddY() : tailDirection.getAddX())));
    }

    @Override
    public void draw(GamePanel panel) {
        panel.fillRect(tailRectangle, getPlayer().getDrawManager().getBodyPaint());
    }
}