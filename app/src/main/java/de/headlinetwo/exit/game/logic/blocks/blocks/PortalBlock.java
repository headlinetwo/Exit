package de.headlinetwo.exit.game.logic.blocks.blocks;

import de.headlinetwo.exit.game.logic.entities.EntityState;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.game.GameConstants;
import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.blocks.AbstractBlock;
import de.headlinetwo.exit.game.logic.blocks.BlockState;
import de.headlinetwo.exit.game.logic.blocks.BlockType;
import de.headlinetwo.exit.game.logic.blocks.WalkableBlock;
import de.headlinetwo.exit.game.logic.entities.player.drawmodel.PlayerEnterPortalDrawModel;
import de.headlinetwo.exit.game.logic.entities.player.drawmodel.PlayerIdleDrawModel;
import de.headlinetwo.exit.util.Callback;

public class PortalBlock extends AbstractBlock implements WalkableBlock {

    private int targetX = -1;
    private int targetY = -1;

    public PortalBlock(int gridX, int gridY) {
        super(BlockType.PORTAL, gridX, gridY);
    }

    @Override
    public boolean triggersActionOnEnter(Player player) {
        return player.getLevel().getGrid().getBlock(targetX, targetY).getBlockState() != BlockState.OCCUPIED_BY_PLAYER;
    }

    @Override
    public void onPlayerEnter(final Player player, final Callback onActionFinished) {
        if (player.getLevel().getGrid().getBlock(targetX, targetY).getBlockState() == BlockState.OCCUPIED_BY_PLAYER) {
            //it is not possible to teleport to a block that is currently occupied by another player-snake
            return;
        }

        player.setCurrentState(EntityState.TELEPORTING, new PlayerEnterPortalDrawModel(player, targetX, targetY, new Callback() {
            @Override
            public void onFinish() {
                player.handlePlayerMovement(targetX, targetY);

                player.setCurrentState(EntityState.IDLE, new PlayerIdleDrawModel(player));

                onActionFinished.onFinish();
            }
        }));
    }

    @Override
    public void drawBlock(GamePanel panel, float gridX, float gridY) {
        panel.fillRect(gridX, gridY, BlockType.FIELD.getPaint());
        panel.fillRect(gridX, gridY, GameConstants.FIVE_PERCENT, GameConstants.FIVE_PERCENT, GameConstants.FIVE_PERCENT, GameConstants.FIVE_PERCENT, getBlockType().getPaint());
    }

    /**
     * Sets the x-coordinate where this portal block leads to
     *
     * @param targetX the target x-coordinate
     */
    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    /**
     * Sets the y-coordinate where this portal block leads to
     *
     * @param targetY the target y-coordinate
     */
    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }
}