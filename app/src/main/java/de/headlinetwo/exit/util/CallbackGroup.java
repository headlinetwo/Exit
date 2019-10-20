package de.headlinetwo.exit.util;

public abstract class CallbackGroup {

    private int count = 0;

    public void addCallback() {
        count++;
    }

    /**
     * Called after one callback within the group is finished.
     * Calls the {@link #onAllFinish()} after all callbacks within this group have been finished
     */
    public void onCallbackFinish() {
        count--;
        if (count == 0) onAllFinish();
    }

    /**
     * Called after all callbacks within this group has been finished
     */
    public abstract void onAllFinish();
}