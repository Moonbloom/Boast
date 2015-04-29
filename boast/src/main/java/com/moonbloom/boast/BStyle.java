package com.moonbloom.boast;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

public final class BStyle {

    //region Variables
    //Debug TAG
    private static transient final String TAG = BStyle.class.getSimpleName();

    /** Red background color ({@link BConstants#materialRed}) */
    public static final BStyle ALERT;

    /** Amber background color ({@link BConstants#materialAmber}) */
    public static final BStyle CAUTION;

    /** Green background color ({@link BConstants#materialGreen}) */
    public static final BStyle OK;

    /** Blue background color ({@link BConstants#materialBlue}) */
    public static final BStyle MESSAGE;

    static {
        ALERT = new Builder()
                .setBackgroundColorValue(BConstants.materialRed)
                .setDuration(BConstants.BDuration.Long)
                .build();

        CAUTION = new Builder()
                .setBackgroundColorValue(BConstants.materialAmber)
                .setDuration(BConstants.BDuration.Long)
                .build();

        OK = new Builder()
                .setBackgroundColorValue(BConstants.materialGreen)
                .build();

        MESSAGE = new Builder()
                .setBackgroundColorValue(BConstants.materialBlue)
                .build();
    }

    /** The resource id of the wanted background color. */
    final int backgroundColorResourceId;

    /** The background color value e.g. 0xffff4444. */
    final int backgroundColorValue;

    /** The text color resource id. */
    final int textColorResourceId;

    /** The text color value e.g. 0xffff4444. */
    final int textColorValue;

    /** The height of the {@link Boast}. */
    final int layoutHeight;

    /** The width of the {@link Boast}. */
    final int layoutWidth;

    /** The text's gravity as provided by {@link BConstants.BTextGravity}. */
    final BConstants.BTextGravity textGravity;

    /** The image drawable to display in the {@link Boast}. */
    final Drawable imageDrawable;

    /** The id of the image to display in the {@link Boast}. */
    final int imageResourceId;

    /** The image's gravity as provided by {@link BConstants.BImageGravity} */
    final BConstants.BImageGravity imageGravity;

    /** The text size in sp. */
    final int textSize;

    /** The horizontal padding for the contentView. */
    final int horizontalPadding;

    /** The vertical padding for the contentView. */
    final int verticalPadding;

    /** The duration that the {@link Boast} should be shown e.g. {@link BConstants.BDuration#Short}. */
    final BConstants.BDuration duration;

    /** The height of the image */
    final int imageHeight;

    /** The width of the image */
    final int imageWidth;

    /** Determines if showing a 2nd {@link Boast}, the 1st {@link Boast} should be immediately cancelled. */
    final boolean autoCancel;
    //endregion

    //region BStyle constructor
    private BStyle(Builder builder) {
        this.backgroundColorResourceId = builder.backgroundColorResourceId;
        this.backgroundColorValue = builder.backgroundColorValue;
        this.textColorResourceId = builder.textColorResourceId;
        this.textColorValue = builder.textColorValue;
        this.layoutHeight = builder.layoutHeight;
        this.layoutWidth = builder.layoutWidth;
        this.textGravity = builder.textGravity;
        this.imageDrawable = builder.imageDrawable;
        this.imageResourceId = builder.imageResourceId;
        this.imageGravity = builder.imageGravity;
        this.textSize = builder.textSize;
        this.horizontalPadding = builder.horizontalPadding;
        this.verticalPadding = builder.verticalPadding;
        this.duration = builder.duration;
        this.imageHeight = builder.imageHeight;
        this.imageWidth = builder.imageWidth;
        this.autoCancel = builder.autoCancel;
    }
    //endregion

    //region Builder
    /** Builder for the {@link BStyle} object. */
    public final static class Builder {
        private int backgroundColorResourceId;
        private int backgroundColorValue;
        private int textColorResourceId;
        private int textColorValue;
        private int layoutHeight;
        private int layoutWidth;
        private BConstants.BTextGravity textGravity;
        private Drawable imageDrawable;
        private int imageResourceId;
        private BConstants.BImageGravity imageGravity;
        private int textSize;
        private int horizontalPadding;
        private int verticalPadding;
        private BConstants.BDuration duration;
        private int imageHeight;
        private int imageWidth;
        private boolean autoCancel;

        /** Creates a {@link Builder} to build a {@link BStyle} upon. */
        public Builder() {
            backgroundColorResourceId = android.R.color.black;
            backgroundColorValue = BConstants.NOT_SET; //Is set by default according to different styles in the static block initializer
            textColorResourceId = android.R.color.white;
            textColorValue = BConstants.NOT_SET;
            layoutHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
            textGravity = BConstants.BTextGravity.Center;
            imageDrawable = null;
            imageResourceId = BConstants.NOT_SET;
            imageGravity = BConstants.BImageGravity.Left;
            textSize = 0;
            horizontalPadding = 24;
            verticalPadding = 14;
            duration = BConstants.BDuration.Short;
            imageHeight = 50;
            imageWidth = 50;
            autoCancel = true;
        }

        /**
         * Creates a {@link Builder} to build a {@link BStyle} upon.
         *
         * @param baseStyle The base {@link BStyle} to use for this {@link BStyle}.
         */
        public Builder(final BStyle baseStyle) {
            this.backgroundColorResourceId = baseStyle.backgroundColorResourceId;
            this.backgroundColorValue = baseStyle.backgroundColorValue;
            this.textColorResourceId = baseStyle.textColorResourceId;
            this.textColorValue = baseStyle.textColorValue;
            this.layoutHeight = baseStyle.layoutHeight;
            this.layoutWidth = baseStyle.layoutWidth;
            this.textGravity = baseStyle.textGravity;
            this.imageDrawable = baseStyle.imageDrawable;
            this.imageResourceId = baseStyle.imageResourceId;
            this.imageGravity = baseStyle.imageGravity;
            this.textSize = baseStyle.textSize;
            this.horizontalPadding = baseStyle.horizontalPadding;
            this.verticalPadding = baseStyle.verticalPadding;
            this.duration = baseStyle.duration;
            this.imageHeight = baseStyle.imageHeight;
            this.imageWidth = baseStyle.imageWidth;
            this.autoCancel = baseStyle.autoCancel;
        }

        /**
         * Set the backgroundColorResourceId option of the {@link Boast}.
         * <br/>
         * Note: Set to 0 for no value.
         * <br/>
         * Default value: {@link android.R.color#black}.
         *
         * @param backgroundColorResourceId The background color resource id.
         *
         * @return the {@link Builder}.
         */
        public Builder setBackgroundColorResource(int backgroundColorResourceId) {
            this.backgroundColorResourceId = backgroundColorResourceId;
            return this;
        }

        /**
         * Set the backgroundColorValue option of the {@link Boast}.
         * <br/>
         * Note: Set to {@link BConstants#NOT_SET} for no value.
         * <br/>
         * Default value: {@link BConstants#NOT_SET}.
         *
         * @param backgroundColorValue The background color value e.g. 0xffff4444.
         *
         * @return the {@link Builder}.
         */
        public Builder setBackgroundColorValue(int backgroundColorValue) {
            this.backgroundColorValue = backgroundColorValue;
            return this;
        }

        /**
         * Set the textColorResourceId option for the {@link Boast}.
         * <br/>
         * Note: Set to 0 for no value.
         * <br/>
         * Default value: {@link android.R.color#white}.
         *
         * @param textColorResourceId The resource id of the text color.
         *
         * @return the {@link Builder}.
         */
        public Builder setTextColorResource(int textColorResourceId) {
            this.textColorResourceId = textColorResourceId;
            return this;
        }

        /**
         * Set the textColorValue option of the {@link Boast}.
         * <br/>
         * Note: Set to {@link BConstants#NOT_SET} for no value.
         * <br/>
         * Default value: {@link BConstants#NOT_SET}.
         *
         * @param textColorValue The text color value e.g. 0xffff4444.
         *
         * @return the {@link Builder}.
         */
        public Builder setTextColorValue(int textColorValue) {
            this.textColorValue = textColorValue;
            return this;
        }

        /**
         * Set the layoutHeight option for the {@link Boast}.
         * <br/>
         * Default value: {@link ViewGroup.LayoutParams#WRAP_CONTENT}.
         *
         * @param height The height of the {@link Boast}. Can also be {@link ViewGroup.LayoutParams#MATCH_PARENT} or {@link ViewGroup.LayoutParams#WRAP_CONTENT}.
         *
         * @return the {@link Builder}.
         */
        @Deprecated
        public Builder setHeight(int height) {
            this.layoutHeight = height;
            return this;
        }

        /**
         * Set the layoutWidth option for the {@link Boast}.
         * <br/>
         * Default value: {@link ViewGroup.LayoutParams#WRAP_CONTENT}.
         *
         * @param width The width of the {@link Boast}. Can also be {@link ViewGroup.LayoutParams#MATCH_PARENT} or {@link ViewGroup.LayoutParams#WRAP_CONTENT}.
         *
         * @return the {@link Builder}.
         */
        @Deprecated
        public Builder setWidth(int width) {
            this.layoutWidth = width;
            return this;
        }

        /**
         * Set the textGravity option for the {@link Boast}.
         * <br/>
         * Default value: {@link BConstants.BTextGravity#Center}.
         *
         * @param textGravity The text's gravity as provided by {@link BConstants.BTextGravity} e.g. {@link BConstants.BTextGravity#Center}.
         *
         * @return the {@link Builder}.
         */
        public Builder setTextGravity(BConstants.BTextGravity textGravity) {
            this.textGravity = textGravity;
            return this;
        }

        /**
         * Set the imageDrawable option for the {@link Boast}.
         * <br/>
         * Default value: null.
         *
         * @param imageDrawable An image drawable to display in the {@link Boast}.
         *
         * @return the {@link Builder}.
         */
        public Builder setImageDrawable(Drawable imageDrawable) {
            this.imageDrawable = imageDrawable;
            return this;
        }

        /**
         * Set the imageResourceId option for the {@link Boast}.
         * <br/>
         * Default value: {@link BConstants#NOT_SET}.
         *
         * @param imageResourceId The id of the image to display in the {@link Boast}.
         *
         * @return The {@link Builder}.
         */
        public Builder setImageResource(int imageResourceId) {
            this.imageResourceId = imageResourceId;
            return this;
        }

        /**
         * Set the imageGravity to position the image according to the text.
         * <br/>
         * Default value: {@link BConstants.BImageGravity#Left}.
         *
         * @param imageGravity The image's gravity as provided by {@link BConstants.BImageGravity} e.g. {@link BConstants.BImageGravity#Left}.
         *
         * @return The {@link Builder}.
         */
        public Builder setImageGravity(BConstants.BImageGravity imageGravity) {
            this.imageGravity = imageGravity;
            return this;
        }

        /**
         * Set the text size used for textViews in the {@link Boast}.
         * <br/>
         * Note: Set to 0 to use system default text size.
         * <br/>
         * Default value: 0 (Meaning it'll use system default text size).
         *
         * @param textSize The text size in sp.
         *
         * @return The {@link Builder}.
         */
        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        /**
         * Set the horizontal and vertical padding for the {@link Boast} view's content.
         * <br/>
         * The values will be converted to dp to allow proper scaling on multiple screen sizes.
         * <br/>
         * Default horizontal value: 24.
         * Default vertical value: 14.
         *
         * @param horizontalPadding The horizontal padding.
         * @param verticalPadding The vertical padding.
         *
         * @return The {@link Builder}.
         */
        public Builder setPadding(int horizontalPadding, int verticalPadding) {
            this.horizontalPadding = horizontalPadding;
            this.verticalPadding = verticalPadding;
            return this;
        }

        /**
         * Set the duration for the {@link Boast}.
         * <br/>
         * Default value: {@link BConstants.BDuration#Short} for {@link BStyle#OK} and {@link BStyle#MESSAGE}, {@link BConstants.BDuration#Long} for {@link BStyle#CAUTION} and {@link BStyle#ALERT}.
         *
         * @param duration The duration e.g. {@link BConstants.BDuration#Short}.
         *
         * @return The {@link Builder}.
         */
        public Builder setDuration(BConstants.BDuration duration) {
            this.duration = duration;
            return this;
        }

        /**
         * Set the image height of the image in the {@link Boast}.
         * <br/>
         * The values will be converted to dp to allow proper scaling on multiple screen sizes.
         * <br/>
         * Default value: 80.
         *
         * @param imageHeight The image height.
         *
         * @return The {@link Builder}.
         */
        public Builder setImageHeight(int imageHeight) {
            this.imageHeight = imageHeight;
            return this;
        }

        /**
         * Set the image width of the image in the {@link Boast}.
         * <br/>
         * The values will be converted to dp to allow proper scaling on multiple screen sizes.
         * <br/>
         * Default value: 80.
         *
         * @param imageWidth The image width.
         *
         * @return The {@link Builder}.
         */
        public Builder setImageWidth(int imageWidth) {
            this.imageWidth = imageWidth;
            return this;
        }

        /**
         * Enable or disable automatic cancelling of old {@link Boast} when a new one is shown.
         * <br/>
         * Default value: true.
         *
         * @param enable Enable (true) or disable (false) automatic {@link Boast} cancelling.
         *
         * @return The {@link Builder}.
         */
        public Builder setAutoCancel(boolean enable) {
            this.autoCancel = enable;
            return this;
        }

        /**
         * Builds the {@link BStyle} object.
         *
         * @return A configured {@link BStyle} object.
         */
        public BStyle build() {
            return new BStyle(this);
        }
    }
    //endregion
}