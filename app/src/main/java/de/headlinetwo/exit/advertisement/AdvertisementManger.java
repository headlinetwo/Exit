package de.headlinetwo.exit.advertisement;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import de.headlinetwo.exit.MainActivity;
import de.headlinetwo.exit.R;

public class AdvertisementManger {

    private Context context;
    private boolean insideEEA; //whether or not the user is within the european economic area

    private AdvertisementConsentManager consentManager;

    private RewardedAd rewardedAd;

    public AdvertisementManger(Context context) {
        this.context = context;
        insideEEA = ConsentInformation.getInstance(context).isRequestLocationInEeaOrUnknown();

        this.consentManager = new AdvertisementConsentManager(context);

        consentManager.loadConsentStatus();
    }

    /**
     * Loads a new advertisement video into ram to allow faster display once the user decides
     * to watch an advertisement
     *
     * @param adLoadCallback called once the advertisement has been loaded / a failure occurs
     * @return the loaded reward advertisement video
     */
    private RewardedAd loadRewardedVideoAd(RewardedAdLoadCallback adLoadCallback) {
        RewardedAd rewardedAd = new RewardedAd(context, context.getResources().getString(R.string.advertisement_ad_id));

        Bundle extras = new Bundle();
        if (insideEEA && consentManager.getCurrentStatus() != ConsentStatus.PERSONALIZED) {
            //no personal ads tag
            extras.putString("npa", "1");
        }

        AdRequest request = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)

                .build();

        rewardedAd.loadAd(request, adLoadCallback);

        return rewardedAd;
    }

    /**
     * Displays an advertisement that unlocks a new level once the user has finished watching the
     * advertisement completely
     *
     * @param callback called once the user has completely watched the advertisement and is ready
     *                 to receive the reward
     */
    public void showAdvertisementToUnlockNextLevel(final AdvertisementRewardCallback callback) {
        rewardedAd = loadRewardedVideoAd(new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                RewardedAdCallback adCallback = new RewardedAdCallback() {
                    public void onRewardedAdOpened() {
                    }

                    public void onRewardedAdClosed() {
                    }

                    public void onUserEarnedReward(RewardItem reward) {
                        callback.onReward();
                    }

                    public void onRewardedAdFailedToShow(int errorCode) {
                        Toast.makeText(context, context.getResources().getString(R.string.ad_failed_to_show), Toast.LENGTH_LONG).show();
                    }
                };

                rewardedAd.show(MainActivity.instance, adCallback);
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                Toast.makeText(context, context.getResources().getString(R.string.ad_failed_to_load), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * @return whether or not the user is located within the european economic area
     */
    public boolean isInsideEEA() {
        return insideEEA;
    }

    public AdvertisementConsentManager getConsentManager() {
        return consentManager;
    }
}