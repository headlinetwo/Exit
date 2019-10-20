package de.headlinetwo.exit.game.logic.blocks.blocks;

import de.headlinetwo.exit.game.logic.entities.EntityState;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.game.logic.blocks.AbstractBlock;
import de.headlinetwo.exit.game.logic.blocks.BlockType;
import de.headlinetwo.exit.game.logic.blocks.WalkableBlock;
import de.headlinetwo.exit.game.logic.entities.player.drawmodel.PlayerEnterGoalDrawModel;
import de.headlinetwo.exit.game.logic.entities.player.drawmodel.PlayerInGoalDrawModel;
import de.headlinetwo.exit.util.Callback;

/**
 * Created by headlinetwo on 22.10.17.
 */
public class GoalBlock extends AbstractBlock implements WalkableBlock {

    public GoalBlock(int gridX, int gridY) {
        super(BlockType.GOAL, gridX, gridY);
    }

    @Override
    public boolean triggersActionOnEnter(Player player) {
        return true;
    }

    @Override
    public void onPlayerEnter(final Player player, final Callback onActionFinished) {
        player.setCurrentState(EntityState.ENTER_GOAL, new PlayerEnterGoalDrawModel(player, new Callback() {
            @Override
            public void onFinish() {
                player.setCurrentState(EntityState.IN_GOAL, new PlayerInGoalDrawModel(player,  155)); //155 = same value as in enter goal animation

                onActionFinished.onFinish();
            }
        }));
    }
}