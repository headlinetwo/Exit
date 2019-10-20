package de.headlinetwo.exit.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import de.headlinetwo.exit.util.MathUtil;

public class CustomLinearLayoutLevelSelectionLevelCard extends LinearLayout {

    public CustomLinearLayoutLevelSelectionLevelCard(Context context, AttributeSet attrs) {
        super(context, attrs);

        setPadding(0, 0, MathUtil.dpToPx(5), MathUtil.dpToPx(5));
    }

    public CustomLinearLayoutLevelSelectionLevelCard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setPadding(0, 0, MathUtil.dpToPx(5), MathUtil.dpToPx(5));
    }

    public CustomLinearLayoutLevelSelectionLevelCard(Context context) {
        super(context);

        setPadding(0, 0, MathUtil.dpToPx(5), MathUtil.dpToPx(5));
    }

    @Override
    public void setPressed(boolean pressed) {
        if (pressed) setPadding(MathUtil.dpToPx(5), MathUtil.dpToPx(5), 0, 0);
        else setPadding(0, 0, MathUtil.dpToPx(5), MathUtil.dpToPx(5));
        super.setPressed(pressed);
    }
}
