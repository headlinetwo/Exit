package de.headlinetwo.exit.game.logic.level.animations;

import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.util.direction.CardinalDirection;

public class LevelGridShakeVerticallyAnimation extends AbstractLevelAnimation {

    private GamePanel panel;
    private CardinalDirection direction; //up or down

    public LevelGridShakeVerticallyAnimation(GamePanel panel, CardinalDirection direction) {
        this.panel = panel;
        this.direction = direction;
    }

    @Override
    public void tick() {
        super.tick();

        panel.setGridStartYOffset((int) ((direction.getAddY() * Math.sin(getProgress() * Math.PI * 4) * panel.getBlockSize() * 0.25 * (END_PROGRESS - getProgress()))));
    }

    @Override
    public void onFinish() {
        panel.setGridStartYOffset(0);
    }
}