package com.alienpg.release.aflatminer;

import android.support.v4.app.Fragment;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Thomas on 13/10/2017.
 */

public class OnClickFragment extends Fragment {

    // Container Activity must implement this interface
    public interface OnButtonClickedListener {
        public void onButtonClicked(int button);
        public void onButtonClicked(int button, int page);
        public void onSignIn();
        public void onSignOut();
        public void onAchievementsClicked();
    }

    public interface GoogleApiListener
    {
        public GoogleApiClient getApiClientFromActivity();
    }
}
