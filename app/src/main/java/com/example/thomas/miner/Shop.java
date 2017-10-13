package com.example.thomas.miner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Thomas on 21/06/2017.
 */

public class Shop extends OnClickFragment {

    private ShopMemory shopMemory;
    View myView;
    OnButtonClickedListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.shop_menu, container, false);
        shopMemory = new ShopMemory(getActivity());
        setButtons();
        return myView;
    }

    private void setButtons()
    {
        LinearLayout house = (LinearLayout) myView.findViewById(R.id.shop_house_item);
        house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onButtonClicked(GlobalConstants.SHOP_PAGES, GlobalConstants.HOUSEUPGRADE);
            }
        });
        ImageView imageViewHouse = (ImageView) myView.findViewById(R.id.shop_house_image);
        imageViewHouse.setImageDrawable(getHouseDrawable());

        LinearLayout dynamite = (LinearLayout) myView.findViewById(R.id.shop_dynamite_item);
        dynamite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onButtonClicked(GlobalConstants.SHOP_PAGES, GlobalConstants.DYNAMITEUPGRADE);
            }
        });
        //ImageView imageViewDynamite = (ImageView) myView.findViewById(R.id.shop_dynamite_item);
        //imageViewHouse.setImageDrawable(getHouseDrawable());
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

    private Drawable getHouseDrawable()
    {
        int itemLevel = shopMemory.getItem(GlobalConstants.HOUSEUPGRADE);
        switch (itemLevel)
        {
            case (0):
                return getResources().getDrawable(R.drawable.house_tent);
            case (1):
                return getResources().getDrawable(R.drawable.house_shack);
            case (2):
                return getResources().getDrawable(R.drawable.house_caravan);
            default:
                return getResources().getDrawable(R.drawable.house_tent);
        }

    }


}
