package de.headlinetwo.exit.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import de.headlinetwo.exit.MainActivity;
import de.headlinetwo.exit.game.gui.GamePanel;
import de.headlinetwo.exit.game.logic.level.LevelState;
import de.headlinetwo.exit.R;

/**
 * Created by headlinetwo on 30.11.17.
 */

public class LevelActivity extends Activity {

    private GameHandler gameHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.level_activity_layout);

        GamePanel gamePanel = findViewById(R.id.level_activity_layout_game_panel_surface_view);
        gameHandler = new GameHandler(this, gamePanel, MainActivity.basicLevelConfigs[getIntent().getExtras().getInt("levelIndex")].getCompleteLevelConfig(this));

        TextView swipeCountTextView = findViewById(R.id.level_activity_swipe_counter_text_view);
        gameHandler.setSwipeCountTextView(swipeCountTextView);
        gameHandler.updateSwipeCountTextView(0);

        ImageButton pauseButton = findViewById(R.id.level_activity_layout_pause_button);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameHandler.getCurrentLevel().setCurrentState(LevelState.PAUSE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        gameHandler.getCurrentLevel().setCurrentState(LevelState.PAUSE);
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }
}