package com.example.thomas.miner;

import android.support.v4.app.Fragment;

/**
 * Created by Thomas on 13/10/2017.
 */

public class OnClickFragment extends Fragment {

    // Container Activity must implement this interface
    public interface OnButtonClickedListener {
        public void onButtonClicked(int button);
        public void onButtonClicked(int button, int page);
    }
}
