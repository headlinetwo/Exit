package de.headlinetwo.exit.game.logic.level.animations;

import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.util.direction.CardinalDirection;

public class LevelGridShakeHorizontallyAnimation extends AbstractLevelAnimation {

    private GamePanel panel;
    private CardinalDirection direction; //left or right

    public LevelGridShakeHorizontallyAnimation(GamePanel panel, CardinalDirection direction) {
        this.panel = panel;
        this.direction = direction;
    }

    @Override
    public void tick() {
        super.tick();

        panel.setGridStartXOffset((int) ((direction.getAddX() * Math.sin(getProgress() * Math.PI * 4) * panel.getBlockSize() * 0.25 * (END_PROGRESS - getProgress()))));
    }

    @Override
    public void onFinish() {
        panel.setGridStartXOffset(0);
    }
}