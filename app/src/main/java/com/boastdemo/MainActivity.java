package com.boastdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.moonbloom.boast.BConstants;
import com.moonbloom.boast.BStyle;
import com.moonbloom.boast.Boast;

public class MainActivity extends Activity {

    private int differentStylesCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if(getActionBar() != null) {
            getActionBar().setTitle(R.string.main_title);
        }

        Button differentStylesButton = (Button) findViewById(R.id.different_styles_button);
        differentStylesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                differentStylesCounter++;

                BStyle style;

                switch (differentStylesCounter) {
                    case 1:
                        style = BStyle.OK;
                        break;

                    case 2:
                        style = BStyle.MESSAGE;
                        break;

                    case 3:
                        style = BStyle.CAUTION;
                        break;

                    case 4:
                        style = BStyle.ALERT;
                        break;

                    default:
                        style = BStyle.OK;
                        break;
                }

                Boast.makeText(MainActivity.this, "This is a simple Boast", style);

                if (differentStylesCounter == 4) {
                    differentStylesCounter = 0;
                }
            }
        });

        Button customStyleOneButton = (Button) findViewById(R.id.custom_style_one_button);
        customStyleOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BStyle style = new BStyle.Builder().setBackgroundColorValue(0xFF673AB7).setTextGravity(BConstants.BTextGravity.Left).setPadding(5, 5).build();

                Boast.makeText(MainActivity.this, "This is a Boast with a custom style\nLeft text gravity, smaller padding and different background color", style);
            }
        });

        Button customStyleTwoButton = (Button) findViewById(R.id.custom_style_two_button);
        customStyleTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BStyle style = new BStyle.Builder().setTextGravity(BConstants.BTextGravity.Right).setDuration(BConstants.BDuration.Long).setTextSize(16).build();

                Boast.makeText(MainActivity.this, "This is a Boast with a custom style\nRight text gravity, long duration and bigger text size", style);
            }
        });

        Button imageLeftButton = (Button) findViewById(R.id.image_left_button);
        imageLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BStyle style = new BStyle.Builder(BStyle.OK).setImageResource(R.drawable.android_logo).build();

                Boast.makeText(MainActivity.this, "This is a Boast with an image to the left", style);
            }
        });

        Button imageRightButton = (Button) findViewById(R.id.image_right_button);
        imageRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BStyle style = new BStyle.Builder(BStyle.MESSAGE).setImageResource(R.drawable.android_logo).setImageGravity(BConstants.BImageGravity.Right).build();

                Boast.makeText(MainActivity.this, "This is a Boast with an image to the right", style);
            }
        });
    }
}