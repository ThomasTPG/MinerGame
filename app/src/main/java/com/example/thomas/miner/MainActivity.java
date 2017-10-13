package com.example.thomas.miner;

import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity implements OnClickFragment.OnButtonClickedListener{

    static String PAGE = "PAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            MainMenuFragment firstFragment = new MainMenuFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    public void onButtonClicked(int button) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        switch(button)
        {
            case(GlobalConstants.START):
                GameMain newGameMainFragment = new GameMain();
                newGameMainFragment.setArguments(args);
                transaction.replace(R.id.fragment_container, newGameMainFragment);
                transaction.addToBackStack(null);
                break;
            case(GlobalConstants.VIEWORES):
                OreView newOreViewFragment = new OreView();
                newOreViewFragment.setArguments(args);
                transaction.replace(R.id.fragment_container, newOreViewFragment);
                transaction.addToBackStack(null);
                break;
            case(GlobalConstants.ENCYCLOPEDIA):
                OreEncyclopedia newOreEncyclopediaFragment = new OreEncyclopedia();
                newOreEncyclopediaFragment.setArguments(args);
                transaction.replace(R.id.fragment_container, newOreEncyclopediaFragment);
                transaction.addToBackStack(null);
                break;
            case(GlobalConstants.SHOP):
                Shop newShopFragment = new Shop();
                newShopFragment.setArguments(args);
                transaction.replace(R.id.fragment_container, newShopFragment);
                transaction.addToBackStack(null);
                break;
            case(GlobalConstants.MAIN_MENU):
                MainMenuFragment newMainMenuFragment = new MainMenuFragment();
                newMainMenuFragment.setArguments(args);
                transaction.replace(R.id.fragment_container, newMainMenuFragment);
                transaction.addToBackStack(null);
                break;
        }
        transaction.commit();
    }

    public void onButtonClicked(int button, int page)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putInt(PAGE, page);
        switch(button)
        {
            case(GlobalConstants.ENCYCLOPEDIA_PAGES):
                EncyclopediaData newEncyclopediaFragment = new EncyclopediaData();
                newEncyclopediaFragment.setArguments(args);
                transaction.replace(R.id.fragment_container, newEncyclopediaFragment);
                transaction.addToBackStack(null);
                break;
            case(GlobalConstants.SHOP_PAGES):
                ShopDetails newShopPagesFragment = new ShopDetails();
                newShopPagesFragment.setArguments(args);
                transaction.replace(R.id.fragment_container, newShopPagesFragment);
                transaction.addToBackStack(null);
                break;
        }
        transaction.commit();
    }
}
