package com.moonbloom.boast;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.widget.Toast;

@SuppressLint("RtlHardcoded")
public abstract class BConstants {

    //region Variables
    //Debug TAG
    private static transient final String TAG = BConstants.class.getSimpleName();

    public static final int NOT_SET = -1;

    public static final int materialRed = 0xFFF44336; //Alert
    public static final int materialAmber = 0xFFFFC107; //Caution
    public static final int materialGreen = 0xFF4CAF50; //Info
    public static final int materialBlue = 0xFF2196F3; //Message

    @SuppressWarnings("unused")
    public static final int materialDarkRed = 0xFFD32F2F;
    @SuppressWarnings("unused")
    public static final int materialDarkAmber = 0xFFFFA000;
    @SuppressWarnings("unused")
    public static final int materialDarkGreen = 0xFF388E3C;
    @SuppressWarnings("unused")
    public static final int materialDarkBlue = 0xFF1976D2;

    private static int durationShort = Toast.LENGTH_SHORT;
    private static int durationLong = Toast.LENGTH_LONG;

    private static int gravityLeft = Gravity.LEFT;
    private static int gravityCenter = Gravity.CENTER;
    private static int gravityRight = Gravity.RIGHT;
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

    /** Enum used to determine the gravity of the text in a {@link Boast}. */
    public enum BTextGravity {
        Left(gravityLeft),
        Center(gravityCenter),
        Right(gravityRight);

        private int gravity;

        BTextGravity(int gravity) {
            this.gravity = gravity;
        }

        public int getGravity() {
            return gravity;
        }
    }

    /** Enum used to determine the gravity of the image in a {@link Boast}. */
    public enum BImageGravity {
        Left(gravityLeft),
        Right(gravityRight);

        private int gravity;

        BImageGravity(int gravity) {
            this.gravity = gravity;
        }

        public int getGravity() {
            return gravity;
        }
    }
}