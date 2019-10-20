package de.headlinetwo.exit.game.logic.blocks.blocks;

import de.headlinetwo.exit.game.logic.entities.EntityState;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.game.logic.entities.player.drawmodel.PlayerRemoveTailBlockDrawModel;
import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.blocks.AbstractBlock;
import de.headlinetwo.exit.game.logic.blocks.BlockState;
import de.headlinetwo.exit.game.logic.blocks.BlockType;
import de.headlinetwo.exit.game.logic.blocks.WalkableBlock;
import de.headlinetwo.exit.game.logic.entities.player.drawmodel.PlayerIdleDrawModel;
import de.headlinetwo.exit.util.Callback;
import de.headlinetwo.exit.util.Point;

/**
 * Created by headlinetwo on 16.01.18.
 */

public class RemovePlayerBodyBlock extends AbstractBlock implements WalkableBlock {

    public RemovePlayerBodyBlock(int gridX, int gridY) {
        super(BlockType.REMOVE_PLAYER_BODY_BLOCK, gridX, gridY);
    }

    @Override
    public void draw(GamePanel panel) {
        super.draw(panel);
        panel.drawText(getGridX(), getGridY(), "-1");
    }

    @Override
    public boolean triggersActionOnEnter(Player player) {
        return true;
    }

    @Override
    public void onPlayerEnter(final Player player, final Callback onActionFinished) {
        if (player.getBody().size() > 2) {
            player.setCurrentState(EntityState.REMOVE_BLOCK, new PlayerRemoveTailBlockDrawModel(player, new Callback() {
                @Override
                public void onFinish() {
                    Point point = player.getBody().removeLastPoint();
                    player.setCurrentState(EntityState.IDLE, new PlayerIdleDrawModel(player));
                    player.getLevel().getGrid().updateBlockState(point.getX(), point.getY(), BlockState.DEFAULT);
                    player.getLevel().getGrid().setBlock(new FieldBlock(getGridX(), getGridY()));
                    player.getLevel().getGrid().getBlock(getGridX(), getGridY()).setBlockState(BlockState.OCCUPIED_BY_PLAYER);

                    onActionFinished.onFinish();
                }
            }));
        }
    }
}