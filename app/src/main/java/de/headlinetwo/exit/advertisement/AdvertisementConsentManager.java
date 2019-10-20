package de.headlinetwo.exit.advertisement;

import android.content.Context;

import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;

import java.net.MalformedURLException;
import java.net.URL;

import de.headlinetwo.exit.MainActivity;
import de.headlinetwo.exit.R;

public class AdvertisementConsentManager {

    private static final ConsentStatus DEFAULT_CONSENT_STATUS = ConsentStatus.UNKNOWN;

    private Context context;
    private String[] publisherIds;
    private URL privacyPolicy;
    private ConsentStatus currentStatus = DEFAULT_CONSENT_STATUS;

    private ConsentForm consentForm;

    public AdvertisementConsentManager(Context context) {
        this.context = context;
        publisherIds = new String[] {context.getResources().getString(R.string.publisherId)};

        try {
            privacyPolicy = new URL(context.getResources().getString(R.string.privacyPolicy));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the currently selected consent status or opens the {@link #openConsentRequestWindow()}
     * consent request window in case the user has not selected any preferences yet
     */
    public void loadConsentStatus() {
        ConsentInformation consentInformation = ConsentInformation.getInstance(context);

        consentInformation.requestConsentInfoUpdate(publisherIds, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                currentStatus = consentStatus;

                if (currentStatus == DEFAULT_CONSENT_STATUS) {
                    openConsentRequestWindow();
                }
            }

            @Override
            public void onFailedToUpdateConsentInfo(String errorDescription) {
                currentStatus = DEFAULT_CONSENT_STATUS;
            }
        });
    }

    /**
     * opens a new consent request window that lets the user change his current preferences
     * regarding personalized advertisement
     */
    public void openConsentRequestWindow() {
        consentForm = new ConsentForm.Builder(MainActivity.instance, privacyPolicy)
                .withListener(new ConsentFormListener() {
                    @Override
                    public void onConsentFormLoaded() {
                        consentForm.show();
                    }

                    @Override
                    public void onConsentFormOpened() {
                    }

                    @Override
                    public void onConsentFormClosed(ConsentStatus consentStatus, Boolean userPrefersAdFree) {
                        currentStatus = consentStatus;
                    }

                    @Override
                    public void onConsentFormError(String errorDescription) {
                        currentStatus = ConsentStatus.NON_PERSONALIZED; //fallback to default (best) case
                    }
                })
                .withPersonalizedAdsOption()
                .withNonPersonalizedAdsOption()
                .build();

        consentForm.load();
    }

    /**
     * @return the currently selected consent status by the user or {@link #DEFAULT_CONSENT_STATUS}
     * in case the user has yet to make a decision
     */
    public ConsentStatus getCurrentStatus() {
        return currentStatus;
    }
}