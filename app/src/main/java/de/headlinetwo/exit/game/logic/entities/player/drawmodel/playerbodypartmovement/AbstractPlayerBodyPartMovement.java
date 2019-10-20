package de.headlinetwo.exit.game.logic.entities.player.drawmodel.playerbodypartmovement;

import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.entities.player.Player;

public abstract class AbstractPlayerBodyPartMovement {

    private Player player;

    public AbstractPlayerBodyPartMovement(Player player) {
        this.player = player;
    }

    public abstract void tick(float progress);

    public abstract void draw(GamePanel panel);

    /**
     * @return the player-snake whose body moves
     */
    public Player getPlayer() {
        return player;
    }
}