package de.headlinetwo.exit.util;

public class GroupableCallback implements Callback {

    private CallbackGroup callbackGroup;

    public GroupableCallback(CallbackGroup callbackGroup) {
        this.callbackGroup = callbackGroup;

        callbackGroup.addCallback();
    }

    @Override
    public void onFinish() {
        callback();

        callbackGroup.onCallbackFinish();
    }

    public void callback() {} //can be overridden
}