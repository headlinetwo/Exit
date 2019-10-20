package de.headlinetwo.exit.game.logic.entities.player.drawmodel.playerbodypartmovement;

import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.util.Rectangle;

public class PlayerTailSuckIntoPortal extends AbstractPlayerBodyPartMovement {

    private Rectangle suckIntoPortalRectangle; //the rectangle that gets sucked into the portal (gets smaller at every tick until it disappears)

    public PlayerTailSuckIntoPortal(Player player) {
        super(player);

        tick(0);
    }

    @Override
    public void tick(float progress) {
        suckIntoPortalRectangle = getPlayer().getDrawManager().getSquare(getPlayer().getBody().getTail().getX(), getPlayer().getBody().getTail().getY(), 1 - progress);
    }

    @Override
    public void draw(GamePanel panel) {
        panel.fillRect(suckIntoPortalRectangle, getPlayer().getDrawManager().getBodyPaint());
    }
}