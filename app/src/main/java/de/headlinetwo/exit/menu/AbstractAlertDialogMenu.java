package de.headlinetwo.exit.menu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

import de.headlinetwo.exit.util.Callback;

/**
 * Created by headlinetwo on 06.12.17.
 */

public abstract class AbstractAlertDialogMenu<T extends Activity> {

    private static final long ANIMATION_TIME = 500; //time in milliseconds for the hide/show animation

    private AbstractAlertDialogMenu instance;

    private boolean hasBeenInitialized = false;
    private T activity; //the activity in which in this dialog is shown
    private AlertDialog alertDialog;

    private ArrayList<View> allButtons = new ArrayList(); //all buttons that need to be disabled while animations are playing

    private Callback onEnterAnimationFinish = null; //called when the enter animation of this dialog is finished
    private Callback onExitAnimationFinish = null; //called when the exit animation of this dialog is finished

    private boolean showingMenu = false; //disable to to show this menu more then once
    private boolean closingMenu = false; //disable to close this menu more then once

    public AbstractAlertDialogMenu(final T activity) {
        this.activity = activity;
        this.instance = this;
    }

    public abstract void createMenu(AlertDialog.Builder builder);

    private void initialize() {
        hasBeenInitialized = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        createMenu(builder);

        alertDialog = builder.create();

        alertDialog.setCancelable(this instanceof CancelableAlertDialogMenu);
        alertDialog.setCanceledOnTouchOutside(this instanceof CancelableAlertDialogMenu);

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (instance instanceof CancelableAlertDialogMenu) {
                    ((CancelableAlertDialogMenu) instance).onCancelMenu();
                }
            }
        });
    }

    public void showMenu() {
        if (!hasBeenInitialized) initialize();

        if (showingMenu) return;
        showingMenu = true;

        setButtonsClickable(false);

        alertDialog.show();

        ObjectAnimator scaleUp = ObjectAnimator.ofPropertyValuesHolder(alertDialog.getWindow().getDecorView(),
                PropertyValuesHolder.ofFloat("scaleX", 0, 1f),
                PropertyValuesHolder.ofFloat("scaleY", 0, 1f),
                PropertyValuesHolder.ofFloat("alpha", 0, 1f));

        scaleUp.setDuration(ANIMATION_TIME);
        scaleUp.start();

        scaleUp.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (onEnterAnimationFinish != null) onEnterAnimationFinish.onFinish();
                setButtonsClickable(true);
            }
        });
    }

    public void closeMenu() {
        if (closingMenu) return;
        closingMenu = true;

        setButtonsClickable(false);

        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(alertDialog.getWindow().getDecorView(),
                PropertyValuesHolder.ofFloat("scaleX", 1f, 0),
                PropertyValuesHolder.ofFloat("scaleY", 1f, 0),
                PropertyValuesHolder.ofFloat("alpha", 1f, 0));

        scaleDown.setDuration(ANIMATION_TIME);
        scaleDown.start();

        scaleDown.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                alertDialog.dismiss();

                if (onExitAnimationFinish != null) onExitAnimationFinish.onFinish();
            }
        });
    }

    private void setButtonsClickable(boolean clickable) {
        for (View button : allButtons) button.setClickable(clickable);
    }

    public T getActivity() {
        return activity;
    }

    public void setOnEnterAnimationFinish(Callback callback) {
        this.onEnterAnimationFinish = callback;
    }

    public void setOnExitAnimationFinish(Callback callback) {
        this.onExitAnimationFinish = callback;
    }

    public void addButtonToDisable(View... button) {
        allButtons.addAll(Arrays.asList(button));
    }
}