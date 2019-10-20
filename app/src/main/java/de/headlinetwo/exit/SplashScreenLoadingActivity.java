package de.headlinetwo.exit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * This class functions as a simple splash screen that serves no other purpose than to display
 * a defined layout whilst some data is loaded.
 *
 * Created by headlinetwo on 04.12.17.
 */

public class SplashScreenLoadingActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //the layout for this activity is defined in the AndroidManifest.xml

        //start the main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}