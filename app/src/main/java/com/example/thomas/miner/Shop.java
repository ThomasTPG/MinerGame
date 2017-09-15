package com.example.thomas.miner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Thomas on 21/06/2017.
 */

public class Shop extends Activity {

    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_menu);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
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
    }

}
