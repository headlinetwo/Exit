package de.headlinetwo.exit.customviews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;

import de.headlinetwo.exit.util.MathUtil;

public class CustomButton extends androidx.appcompat.widget.AppCompatButton {

    private static final int SHADOW_OFFSET_PX = MathUtil.dpToPx(3);

    private static final int DEFAULT_PADDING_TOP_PX = MathUtil.dpToPx(15);
    private static final int DEFAULT_PADDING_RIGHT_PX = MathUtil.dpToPx(30);
    private static final int DEFAULT_PADDING_BOTTOM_PX = MathUtil.dpToPx(15);
    private static final int DEFAULT_PADDING_LEFT_PX = MathUtil.dpToPx(30);

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/visitor1.otf"));
        this.setTextColor(Color.BLACK);

        setDefaultPadding();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/visitor1.otf"));
        this.setTextColor(Color.BLACK);

        setDefaultPadding();
    }

    public CustomButton(Context context) {
        super(context);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/visitor1.otf"));
        this.setTextColor(Color.BLACK);

        setDefaultPadding();
    }

    @Override
    public void setPressed(boolean pressed) {
        if (pressed) {
            setPadding(DEFAULT_PADDING_LEFT_PX + MathUtil.dpToPx(3), DEFAULT_PADDING_TOP_PX + MathUtil.dpToPx(3), DEFAULT_PADDING_RIGHT_PX, DEFAULT_PADDING_BOTTOM_PX);
        }
        else setDefaultPadding();
        super.setPressed(pressed);
    }

    private void setDefaultPadding() {
        setPadding(DEFAULT_PADDING_LEFT_PX, DEFAULT_PADDING_TOP_PX, DEFAULT_PADDING_RIGHT_PX + SHADOW_OFFSET_PX, DEFAULT_PADDING_BOTTOM_PX + SHADOW_OFFSET_PX);
    }
}