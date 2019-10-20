package de.headlinetwo.exit.game.logic.entities.player.drawmodel;

import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.game.logic.entities.player.drawmodel.playerbodypartmovement.AbstractPlayerBodyPartMovement;
import de.headlinetwo.exit.util.Callback;
import de.headlinetwo.exit.util.Rectangle;

public class PlayerEnterPortalDrawModel extends AnimateablePlayerDrawModel {

    private Rectangle[] bodyRectangles;
    private Rectangle headConnector;
    private AbstractPlayerBodyPartMovement tailMovement;

    private Rectangle disappearHeadRectangle;
    private Rectangle appearHeadRectangle;

    private int portalTargetX;
    private int portalTargetY;

    public PlayerEnterPortalDrawModel(Player player, int portalTargetX, int portalTargetY, Callback animationFinishCallback) {
        super(player, animationFinishCallback);

        this.portalTargetX = portalTargetX;
        this.portalTargetY = portalTargetY;

        updateRectangles(0);

        bodyRectangles = player.getDrawManager().getBodyRectangles(player.getBody());
        tailMovement = player.getDrawManager().getNextTailMovement(player);

        headConnector = getPlayer().getDrawManager().getSquare(player.getBody().getHead().getX(), player.getBody().getHead().getY());
    }

    @Override
    public void draw(GamePanel panel) {
        tailMovement.draw(panel);
        for (Rectangle rectangle : bodyRectangles) panel.fillRect(rectangle, getPlayer().getDrawManager().getBodyPaint());
        panel.fillRect(headConnector, getPlayer().getDrawManager().getBodyPaint());
        panel.fillRect(disappearHeadRectangle, getPlayer().getDrawManager().getHeadPaint());
        panel.fillRect(appearHeadRectangle, getPlayer().getDrawManager().getHeadPaint());
    }

    @Override
    public void tick() {
        super.tick();

        tailMovement.tick(getProgress());

        updateRectangles(getProgress());
    }

    public void updateRectangles(float progress) {
        disappearHeadRectangle = getPlayer().getDrawManager().getHeadRectangle(getPlayer().getBody().getHead().getX(), getPlayer().getBody().getHead().getY(), 1 - progress);
        appearHeadRectangle = getPlayer().getDrawManager().getHeadRectangle(portalTargetX, portalTargetY, progress);
    }
}