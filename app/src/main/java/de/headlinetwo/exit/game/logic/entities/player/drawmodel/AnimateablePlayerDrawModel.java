package de.headlinetwo.exit.game.logic.entities.player.drawmodel;

import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.entities.player.Player;
import de.headlinetwo.exit.util.Callback;

public abstract class AnimateablePlayerDrawModel extends AbstractPlayerDrawModel {

    private static final float START_PROGRESS = 0f;
    private static final float END_PROGRESS = 1f;
    private static final float PROGRESS_PER_TICK = 0.1f;

    private float currentProgress = START_PROGRESS;
    private Callback animationFinishCallback;

    public AnimateablePlayerDrawModel(Player player, Callback animationFinishCallback) {
        super(player);

        this.animationFinishCallback = animationFinishCallback;
    }

    public abstract void draw(GamePanel panel);

    public void tick() {
        currentProgress += PROGRESS_PER_TICK;

        if (currentProgress > END_PROGRESS) {
            if (animationFinishCallback != null) animationFinishCallback.onFinish();
        }
    }

    /**
     * @return the current progress between {@link #START_PROGRESS} and {@link #END_PROGRESS}
     * indicating how far this animation has advanced already
     */
    public float getProgress() {
        return currentProgress;
    }
}