package com.alienpg.release.aflatminer;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

/**
 * Created by Thomas on 22/10/2017.
 */

public class Achievements {

    GoogleApiClient mGoogleApiClient = null;
    Context mContext;

    public Achievements(Context context) {
        mContext = context;
    }

    public void setmGoogleApiClient(GoogleApiClient googleApiClient)
    {
        mGoogleApiClient = googleApiClient;
    }

    public void unlockAchievement(String my_achievement_id)
    {
        if (mGoogleApiClient != null)
        {
            Games.Achievements.unlock(mGoogleApiClient, my_achievement_id);
        }
    }

    public void checkExitAchievements(int setting)
    {
        if (setting == GlobalConstants.NIGHT)
        {
            unlockAchievement(mContext.getResources().getString(R.string.night_man));
        }
        else if (setting == GlobalConstants.DAY)
        {
            unlockAchievement(mContext.getResources().getString(R.string.day_man));
        }
        else if (setting == GlobalConstants.SUNSET)
        {
            unlockAchievement(mContext.getResources().getString(R.string.champion_of_the_sun));
        }
    }
}
