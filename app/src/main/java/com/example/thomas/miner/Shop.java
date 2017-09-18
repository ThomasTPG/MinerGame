package com.example.thomas.miner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Thomas on 21/06/2017.
 */

public class Shop extends Activity {

    private ShopMemory shopMemory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_menu);
        shopMemory = new ShopMemory(this);
        setButtons();
    }

    private void setButtons()
    {
        LinearLayout house = (LinearLayout) findViewById(R.id.shop_house_item);
        final Intent houseDetails = new Intent(this, ShopDetails.class);
        houseDetails.putExtra("Item", GlobalConstants.HOUSEUPGRADE);
        house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(houseDetails);
            }
        });
        ImageView imageViewHouse = (ImageView) findViewById(R.id.shop_house_image);
        imageViewHouse.setImageDrawable(getHouseDrawable());
    }

    private Drawable getHouseDrawable()
    {
        int itemLevel = shopMemory.getItem(GlobalConstants.HOUSEUPGRADE);
        switch (itemLevel)
        {
            case (0):
                return getResources().getDrawable(R.drawable.house_tent);
            case (1):
                return getResources().getDrawable(R.drawable.house_caravan);
            default:
                return getResources().getDrawable(R.drawable.house_tent);
        }

    }


}
