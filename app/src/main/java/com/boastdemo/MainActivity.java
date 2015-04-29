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
                        style = BStyle.INFO;
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
                        style = BStyle.INFO;
                        break;
                }

                Boast.makeText(MainActivity.this, "This is a simple Boast", style);

                if (differentStylesCounter == 4) {
                    differentStylesCounter = 0;
                }
            }
        });

        Button customStyleButton = (Button) findViewById(R.id.custom_style_button);
        customStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BStyle style = new BStyle.Builder().setImageGravity(BConstants.BImageGravity.Right).setTextGravity(BConstants.BTextGravity.Left).setDuration(BConstants.BDuration.Long).setImageResource(R.drawable.android_logo).setPaddingInPixels(10, 10).build();

                Boast.makeText(MainActivity.this, "Boast\nThis is a demo of the Boast library", style);
            }
        });
    }
}