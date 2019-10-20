package de.headlinetwo.exit.game.logic.blocks.blocks;

import de.headlinetwo.exit.game.logic.blocks.AbstractBlock;
import de.headlinetwo.exit.game.logic.blocks.BlockType;

/**
 * Created by headlinetwo on 23.10.17.
 */
public class FieldBlock extends AbstractBlock {

    public FieldBlock(int gridX, int gridY) {
        super(BlockType.FIELD, gridX, gridY);
    }
}