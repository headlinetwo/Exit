package de.headlinetwo.exit.menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.headlinetwo.exit.MainActivity;
import de.headlinetwo.exit.R;

/**
 * Created by headlinetwo on 25.12.17.
 */

public class MainMenuAboutPage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_menu_about_page, container, false);

        final Button twitterButton = rootView.findViewById(R.id.main_menu_about_page_twitter_button);
        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                try {
                    // open twitter account via the twitter app if the user has twitter installed
                    MainActivity.instance.getPackageManager().getPackageInfo("com.twitter.android", 0);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.instance.getString(R.string.twitter_developer_account_id)));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (Exception e) {
                    // open twitter account via browser
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.instance.getString(R.string.twitter_developer_account_name)));
                }
                MainActivity.instance.startActivity(intent);
            }
        });

        Button rateButton = rootView.findViewById(R.id.main_menu_about_rate_us_button);
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.instance.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.instance.getString(R.string.google_play_store_listing))));
            }
        });

        return rootView;
    }
}