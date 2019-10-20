package de.headlinetwo.exit.menu.levelselection;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomRecyclerViewGridLayout extends GridLayoutManager  {

    private static final int MINIMUM_SPAN_COUNT = 1;
    private int minItemWidthPX;

    public CustomRecyclerViewGridLayout(Context context, int minItemWidthDP) {
        super(context, 1);
        this.minItemWidthPX = (int) (minItemWidthDP * context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        updateSpanCount();
        super.onLayoutChildren(recycler, state);
    }

    /**
     * Updates the span count so that each item is at least {@link #minItemWidthPX} wide
     * whilst maximizing the number of items next to each other
     */
    private void updateSpanCount() {
        int spanCount = getWidth() / minItemWidthPX;
        if (spanCount < MINIMUM_SPAN_COUNT) {
            setSpanCount(MINIMUM_SPAN_COUNT);
        }
        else {
            setSpanCount(spanCount);
        }
    }
}
