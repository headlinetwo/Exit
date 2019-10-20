package de.headlinetwo.exit.game;

/**
 * Created by headlinetwo on 30.11.17.
 */

public class GameThread extends Thread {

    private GameHandler gameHandler;

    private boolean running = true;

    public GameThread(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    /**
     * The main update/tick loop of the game
     */
    @Override
    public void run() {
        long lastLoopTime;
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        while (running) {
            long now = System.nanoTime();
            lastLoopTime = now;

            gameHandler.tick();

            gameHandler.draw();

            long millis = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
            if (millis < 0) millis = 0;

            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}