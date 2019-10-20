package de.headlinetwo.exit.game.logic.blocks.blocks;

import de.headlinetwo.exit.game.logic.blocks.AbstractBlock;
import de.headlinetwo.exit.game.logic.blocks.BlockType;

/**
 * Created by headlinetwo on 22.10.17.
 */
public class VoidBlock extends AbstractBlock {

    public VoidBlock(int gridX, int gridY) {
        super(BlockType.VOID, gridX, gridY);
    }

    @Override
    public boolean playerCanMoveTo() {
        return false;
    }
}