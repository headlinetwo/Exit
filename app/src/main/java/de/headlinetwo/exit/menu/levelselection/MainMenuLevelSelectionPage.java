package de.headlinetwo.exit.menu.levelselection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.headlinetwo.exit.MainActivity;
import de.headlinetwo.exit.R;

/**
 * Created by headlinetwo on 01.12.17.
 */

public class MainMenuLevelSelectionPage extends Fragment {

    private static final int MIN_ITEM_WIDTH_DP = 65; //minimum item width measured in dp (density independent pixel)

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_menu_level_selection, container, false);

        recyclerView = rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setClickable(true);

        layoutManager = new LinearLayoutManager(inflater.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new CustomRecyclerViewGridLayout(rootView.getContext(), MIN_ITEM_WIDTH_DP));

        adapter = new LevelSelectionAdapter(this, MainActivity.basicLevelConfigs, MainActivity.playerDataManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }
}