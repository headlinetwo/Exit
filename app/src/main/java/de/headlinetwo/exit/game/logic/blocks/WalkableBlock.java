package de.headlinetwo.exit.game.logic.blocks;

import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.util.Callback;

/**
 * Created by headlinetwo on 15.03.18.
 */

public interface WalkableBlock {

    /**
     * Returns {@code true} if an action is going to occur if a player-snake enters this block.
     * Returns {@code false} otherwise, for example if the destination of a portal block is occupied
     *
     * @param player the player-snake entering this block
     * @return whether or not an action occurs whilst a player-snake enters this block
     */
    boolean triggersActionOnEnter(Player player);

    /**
     * Called when a player-snake enters this block
     *
     * @param player the player-snake entering this block
     * @param onActionFinished must be called once all actions that were called as a result of a
     *                         player-snake entering this block are finished.
     */
    void onPlayerEnter(Player player, Callback onActionFinished);
}