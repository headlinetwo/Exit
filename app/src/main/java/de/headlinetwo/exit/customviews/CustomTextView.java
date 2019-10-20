package de.headlinetwo.exit.customviews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by headlinetwo on 14.01.18.
 */

public class CustomTextView extends androidx.appcompat.widget.AppCompatTextView {

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/visitor1.otf"));
        this.setTextColor(Color.BLACK);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/visitor1.otf"));
        this.setTextColor(Color.BLACK);
    }

    public CustomTextView(Context context) {
        super(context);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/visitor1.otf"));
        this.setTextColor(Color.BLACK);
    }
}