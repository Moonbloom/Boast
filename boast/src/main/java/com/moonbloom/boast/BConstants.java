package com.moonbloom.boast;

import android.annotation.SuppressLint;
import android.widget.Toast;

@SuppressLint("RtlHardcoded")
public abstract class BConstants {

    //region Variables
    //Debug TAG
    private static transient final String TAG = BConstants.class.getSimpleName();

    public static final int NOT_SET = -1;

    public static final int materialGreenOk = 0xFF4CAF50; //Ok
    public static final int materialBlueMessage = 0xFF2196F3; //Message
    public static final int materialAmberCaution = 0xFFFFC107; //Caution
    public static final int materialRedAlert = 0xFFF44336; //Alert

    public static final int materialDarkGreen = 0xFF388E3C;
    public static final int materialDarkBlue = 0xFF1976D2;
    public static final int materialDarkAmber = 0xFFFFA000;
    public static final int materialDarkRed = 0xFFD32F2F;

    private static int durationShort = Toast.LENGTH_SHORT;
    private static int durationLong = Toast.LENGTH_LONG;
    //endregion

    /** Enum used to determine the duration of a {@link Boast}. */
    public enum BDuration {
        Short(durationShort),
        Long(durationLong);

        private int duration;

        BDuration(int duration) {
            this.duration = duration;
        }

        public int getDuration() {
            return duration;
        }
    }

    /** Enum used to determine the background color of a {@link Boast} - Can be used instead of resource color IDs for predefined colors */
    public enum BColor {
        GreenOk(materialGreenOk),
        BlueMessage(materialBlueMessage),
        AmberCaution(materialAmberCaution),
        RedAlert(materialRedAlert);

        private int color;

        BColor(int color) {
            this.color = color;
        }

        public int getColor() {
            return color;
        }
    }
}