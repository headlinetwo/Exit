package de.headlinetwo.exit.menu.levelselection;

import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import de.headlinetwo.exit.MainActivity;
import de.headlinetwo.exit.R;
import de.headlinetwo.exit.customviews.CustomTextView;
import de.headlinetwo.exit.game.LevelActivity;

/**
 * Created by headlinetwo on 08.12.17.
 */

public class LevelSelectionViewHolder extends RecyclerView.ViewHolder {

    private int viewType;
    private CustomTextView levelIndexTextView;
    private ProgressBar levelCompletedProgressBar;

    public LevelSelectionViewHolder(View view, int viewType) {
        super(view);

        this.viewType = viewType;

        if (viewType == LevelSelectionAdapter.VIEW_TYPE_LEVEL_LOCKED) levelIndexTextView = view.findViewById(R.id.level_selection_level_card_level_locked_level_index_text_view);
        else if (viewType == LevelSelectionAdapter.VIEW_TYPE_LEVEL_UNLOCKED) {
            levelIndexTextView = view.findViewById(R.id.level_selection_level_card_level_unlocked_level_index_text_view);
            levelCompletedProgressBar = view.findViewById(R.id.level_selection_level_card_level_unlocked_progress_bar);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position;

                try {
                    position = Integer.parseInt(levelIndexTextView.getText().toString()) - 1;
                }
                catch (Exception ex) {
                    return;
                }

                if (MainActivity.playerDataManager.hasLevelUnlocked(position)) {
                    Intent intent = new Intent(MainActivity.instance, LevelActivity.class);
                    intent.putExtra("levelIndex", position);
                    MainActivity.instance.startActivity(intent);
                }
                else new LevelLockedInfoMenu(MainActivity.instance, position).showMenu();
            }
        });
    }

    public int getViewType() {
        return viewType;
    }

    public CustomTextView getLevelIndexTextView() {
        return levelIndexTextView;
    }

    public ProgressBar getProgressBar() {
        return levelCompletedProgressBar;
    }
}
