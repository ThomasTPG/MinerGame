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
    private int screenWidth;
    private int screenHeight;
    ArrayOfBlocksOnScreen blocksOnScreen;

    public Achievements(Context context) {
        mContext = context;
    }

    public void setmGoogleApiClient(GoogleApiClient googleApiClient)
    {
        mGoogleApiClient = googleApiClient;
    }

    public void initialize(int screenWidth, int screenHeight, ArrayOfBlocksOnScreen arrayOfBlocksOnScreen)
    {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        blocksOnScreen = arrayOfBlocksOnScreen;
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

    public void checkOops(Block explodium)
    {
        if (explodium.getIndex() == blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(screenWidth/2, screenHeight/2).getIndex())
        {
            unlockAchievement(mContext.getResources().getString(R.string.oops));
        }
    }


    public void checkChainReactionI(Block fallen)
    {
        if (fallen.getHeightFallen() >= 7)
        {
            unlockAchievement(mContext.getResources().getString(R.string.chain_reaction_i));
        }
    }

    public void checkChainReactionII(Block explodium, Block exploding)
    {
        if (explodium.getHeightFallen() >= 5)
        {
            if (exploding.getGasPercentage() > BlockPhysics.GAS_THRESHOLD)
            {
                unlockAchievement(mContext.getResources().getString(R.string.chain_reaction_ii));
            }
            else
            {
                exploding.setAchievementChainReactionII();
            }
        }
    }

}
