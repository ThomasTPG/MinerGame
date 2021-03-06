package com.alienpg.release.aflatminer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Thomas on 13/10/2017.
 */

public class MainMenuFragment extends OnClickFragment {

    OnButtonClickedListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu_layout, container, false);

        OreMemory oreMemory = new OreMemory(getActivity());
        ShopMemory shopMemory = new ShopMemory(getActivity());

        Button start = (Button) view.findViewById(R.id.buttonStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onButtonClicked(GlobalConstants.START);
            }
        });

        Button shop = (Button) view.findViewById(R.id.buttonShop);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onButtonClicked(GlobalConstants.SHOP);
            }
        });

        Button viewOres = (Button) view.findViewById(R.id.buttonViewOres);
        viewOres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onButtonClicked(GlobalConstants.VIEWORES);
            }
        });

        Button encyclopedia = (Button) view.findViewById(R.id.buttonEncyclopedia);
        encyclopedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onButtonClicked(GlobalConstants.ENCYCLOPEDIA);
            }
        });

        Button achievements = (Button) view.findViewById(R.id.buttonAchievements);
        achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onAchievementsClicked();
            }
        });

        Button signin = (Button) view.findViewById(R.id.buttonSignIn);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onSignIn();
            }
        });

        Button signout = (Button) view.findViewById(R.id.buttonSignOut);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onSignOut();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnButtonClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnButtonClickedListener");
        }
    }
}
