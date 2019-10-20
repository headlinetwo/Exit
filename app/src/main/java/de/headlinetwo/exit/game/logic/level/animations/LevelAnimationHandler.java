package de.headlinetwo.exit.game.logic.level.animations;

import java.util.HashSet;

public class LevelAnimationHandler {

    private HashSet<AbstractLevelAnimation> animations = new HashSet<>();

    public void tick() {
        for (AbstractLevelAnimation animation : new HashSet<>(animations)) {
            animation.tick();

            if (animation.getProgress() > AbstractLevelAnimation.END_PROGRESS) animations.remove(animation);
        }
    }

    /**
     * Adds a currently active animation to be displayed on the screen
     * @param animation the animation to play on the screen
     */
    public void addAnimation(AbstractLevelAnimation animation) {
        animations.add(animation);
    }
}