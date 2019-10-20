package de.headlinetwo.exit.game.logic.level.animations;

public abstract class AbstractLevelAnimation {

    private static final float START_PROGRESS = 0f;
    public static final float END_PROGRESS = 1f;
    private static final float PROGRESS_PER_TICK = 0.05f;

    private float currentProgress = START_PROGRESS;

    public void tick() {
        currentProgress += PROGRESS_PER_TICK;

        if (currentProgress > END_PROGRESS) {
            onFinish();
        }
    }

    public abstract void onFinish();

    /**
     * @return the current progress between {@link #START_PROGRESS} and {@link #END_PROGRESS}
     * indicating how far this animation has advanced already
     */
    public float getProgress() {
        return currentProgress;
    }
}