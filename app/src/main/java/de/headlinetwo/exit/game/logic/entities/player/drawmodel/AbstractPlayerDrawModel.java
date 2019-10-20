package de.headlinetwo.exit.game.logic.entities.player.drawmodel;

import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.entities.player.Player;

public abstract class AbstractPlayerDrawModel {

    private Player player;

    public AbstractPlayerDrawModel(Player player) {
        this.player = player;
    }

    public abstract void draw(GamePanel panel);

    public Player getPlayer() {
        return player;
    }
}