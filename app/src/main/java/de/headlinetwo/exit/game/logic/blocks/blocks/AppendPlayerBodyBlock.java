package de.headlinetwo.exit.game.logic.blocks.blocks;

import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.blocks.AbstractBlock;
import de.headlinetwo.exit.game.logic.blocks.BlockState;
import de.headlinetwo.exit.game.logic.blocks.BlockType;
import de.headlinetwo.exit.game.logic.blocks.WalkableBlock;
import de.headlinetwo.exit.game.logic.entities.EntityState;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.game.logic.entities.player.drawmodel.PlayerAppendTailBlockDrawModel;
import de.headlinetwo.exit.game.logic.entities.player.drawmodel.PlayerIdleDrawModel;
import de.headlinetwo.exit.util.Callback;
import de.headlinetwo.exit.util.Point;

/**
 * Represents the block that increases the length of the player-snake entering this block by one.
 *
 * Created by headlinetwo on 16.01.18.
 */

public class AppendPlayerBodyBlock extends AbstractBlock implements WalkableBlock {

    public AppendPlayerBodyBlock(int gridX, int gridY) {
        super(BlockType.ADD_PLAYER_BODY_BLOCK, gridX, gridY);
    }

    @Override
    public void draw(GamePanel panel) {
        super.draw(panel);
        panel.drawText(getGridX(), getGridY(), "+1");
    }

    @Override
    public boolean triggersActionOnEnter(Player player) {
        return player.getLevel().getGrid().getBlock(player.getBody().getPreMoveTailX(), player.getBody().getPreMoveTailY()).getBlockState() != BlockState.OCCUPIED_BY_PLAYER;
    }

    @Override
    public void onPlayerEnter(final Player player, final Callback onActionFinished) {
        player.setCurrentState(EntityState.APPEND_BLOCK, new PlayerAppendTailBlockDrawModel(player, player.getBody().getPreMoveTailX(), player.getBody().getPreMoveTailY(), new Callback() {
            @Override
            public void onFinish() {
                Point point = player.getBody().appendPoint();
                player.getLevel().getGrid().updateBlockState(point.getX(), point.getY(), BlockState.OCCUPIED_BY_PLAYER);
                player.setCurrentState(EntityState.IDLE, new PlayerIdleDrawModel(player));
                player.getLevel().getGrid().setBlock(new FieldBlock(getGridX(), getGridY()));
                player.getLevel().getGrid().getBlock(getGridX(), getGridY()).setBlockState(BlockState.OCCUPIED_BY_PLAYER);

                onActionFinished.onFinish();
            }
        }));
    }
}