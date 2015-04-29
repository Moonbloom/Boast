package com.moonbloom.boast;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public final class Boast {

    //region Variables
    //Debug TAG
    private static transient final String TAG = Boast.class.getSimpleName();

    private static int customLayoutResourceId = 0;
    private static int customTextViewId = 0;

    private static BStyle defaultBStyle;

    private volatile static Boast globalBoast = null;

    private Toast internalToast;

    static {
        defaultBStyle = BStyle.OK;
    }
    //endregion

    //region Public setup functions - Are not required to be used
    /**
     * Set the default used {@link Boast} layout resource ID and textView ID, to avoid having to send it as parameter each time Boast.makeText() is used.
     * <br/>
     * If this function is used, the custom resources will always be used when creating {@link Boast} with Boast.makeText().
     * <br/>
     * Note: Set to 0 to use standard {@link Boast} layout.
     *
     * @param layoutResourceId Resource layout e.g. R.layout.custom_toast_layout.
     * @param textViewId textView ID e.g. R.id.custom_toast_text.
     */
    public static void setCustomResource(int layoutResourceId, int textViewId) {
        customLayoutResourceId = layoutResourceId;
        customTextViewId = textViewId;
    }

    /**
     * Set the default used {@link BStyle} when using Boast.makeText() without a {@link BStyle} parameter.
     * <br/>
     * Default value: {@link BStyle#OK}.
     *
     * @param style The wanted default {@link BStyle}.
     */
    public static void setDefaultBStyle(BStyle style) {
        defaultBStyle = style;
    }
    //endregion

    //region Private functions
    private Boast(Toast toast) {
        if (toast == null) {
            throw new NullPointerException("Boast.Boast(Toast) requires a non-null parameter");
        }

        internalToast = toast;
    }

    private void cancel() {
        internalToast.cancel();
    }

    private void show(boolean autoCancel) {
        if (globalBoast != null && autoCancel) {
            globalBoast.cancel();
        }

        globalBoast = this;

        internalToast.show();
    }
    //endregion

    //region Public functions used to create Boasts
    /**
     * Create and automatically show a {@link Boast}.
     *
     * @param context Used to create internal {@link Toast} & inflate view.
     * @param text Text to show in the {@link Boast}.
     */
    public static void makeText(Context context, CharSequence text) {
        makeText(context, text, defaultBStyle);
    }

    /**
     * Create and automatically show a {@link Boast}.
     *
     * @param context Used to create internal {@link Toast} & inflate view.
     * @param text Text to show in the {@link Boast}.
     * @param style {@link BStyle} e.g. {@link BStyle#OK}).
     */
    public static void makeText(Context context, CharSequence text, BStyle style) {
        //If custom view resources have been set, it'll use and inflate those, otherwise it'll use the standard setup
        if(customLayoutResourceId == 0 && customTextViewId == 0) {
            inflateProgrammatically(context, text, style);
        } else {
            inflateFromResource(context, customLayoutResourceId, customTextViewId, text, style);
        }
    }

    /**
     * Create and automatically show a {@link Boast}.
     *
     * @param context Used to create internal {@link Toast} & inflate view.
     * @param textResourceId ID of the text resource to show in the {@link Boast}.
     */
    public static void makeText(Context context, int textResourceId) {
        makeText(context, context.getResources().getString(textResourceId));
    }

    /**
     * Create and automatically show a {@link Boast}.
     *
     * @param context Used to create internal {@link Toast} & inflate view.
     * @param textResourceId ID of the text resource to show in the {@link Boast}.
     * @param style {@link BStyle} e.g. {@link BStyle#OK}).
     */
    public static void makeText(Context context, int textResourceId, BStyle style) {
        makeText(context, context.getResources().getString(textResourceId), style);
    }

    /**
     * Create and automatically show a {@link Boast}.
     *
     * @param context Used to create internal {@link Toast} & inflate view.
     * @param layoutResource Resource layout e.g. R.layout.custom_toast_layout.
     * @param textViewId TextView ID e.g. R.id.custom_toast_text.
     * @param text Text to show in the {@link Boast}.
     */
    public static void makeText(Context context, int layoutResource, int textViewId, CharSequence text) {
        inflateFromResource(context, layoutResource, textViewId, text, defaultBStyle);
    }

    public static void makeText(Context context, int layoutResource, int textViewId, int textResourceId) {
        inflateFromResource(context, layoutResource, textViewId, context.getResources().getString(textResourceId), defaultBStyle);
    }
    //endregion

    //region Internal helper functions
    private static int convertPxToDp(Context context, int pixels) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(pixels * density);
    }

    private static void inflateProgrammatically(Context context, CharSequence text, BStyle style) {
        LinearLayout linearLayout = initializeContentView(context, text, style);

        finalizeToastSetup(context, linearLayout, style);
    }

    private static LinearLayout initializeContentView(Context context, CharSequence text, BStyle style) {
        //Create parent layout
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(linearLayoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        //Set background color
        if (style.backgroundColorValue != BConstants.NOT_SET) {
            linearLayout.setBackgroundColor(style.backgroundColorValue);
        } else {
            linearLayout.setBackgroundColor(context.getResources().getColor(style.backgroundColorResourceId));
        }

        //Set padding
        int horizontalPaddingDp = convertPxToDp(context, style.horizontalPadding);
        int verticalPaddingDp = convertPxToDp(context, style.verticalPadding);
        linearLayout.setPadding(horizontalPaddingDp, verticalPaddingDp, horizontalPaddingDp, verticalPaddingDp);

        //Only initialize imageView if one is requested
        ImageView imageView = null;
        if ((style.imageDrawable != null) || (style.imageResourceId != BConstants.NOT_SET)) {
            imageView = initializeImageView(context, style);
        }

        //Initialize textView
        TextView textView = initializeTextView(context, style, text);

        //Setup layout parameters for textView to correctly position it according to the imageView
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        textViewParams.weight = 1;
        textViewParams.gravity = Gravity.CENTER_VERTICAL;

        //Add the views in the correct order
        int imageGravity = style.imageGravity.getGravity();
        if(imageGravity == BConstants.BImageGravity.Left.getGravity()) {
            if(imageView != null) {
                linearLayout.addView(imageView);
            }
            linearLayout.addView(textView, textViewParams);
        } else if(imageGravity == BConstants.BImageGravity.Right.getGravity()) {
            linearLayout.addView(textView, textViewParams);
            if(imageView != null) {
                linearLayout.addView(imageView);
            }
        }

        return linearLayout;
    }

    private static TextView initializeTextView(Context context, BStyle style, CharSequence text) {
        TextView textView = new TextView(context);

        textView.setText(text);

        textView.setGravity(style.textGravity.getGravity());

        //Set text color
        if (style.textColorValue != BConstants.NOT_SET) {
            textView.setTextColor(style.textColorValue);
        } else if (style.textColorResourceId != BConstants.NOT_SET) {
            textView.setTextColor(context.getResources().getColor(style.textColorResourceId));
        }

        //Set text size
        if(style.textSize != 0) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, style.textSize);
        }

        return textView;
    }

    private static ImageView initializeImageView(Context context, BStyle style) {
        ImageView imageView = new ImageView(context);

        imageView.setAdjustViewBounds(true);

        //Set image drawable if requested
        if(style.imageDrawable != null) {
            imageView.setImageDrawable(style.imageDrawable);
        }

        //Set image resource if requested (This will override image drawable if both are set)
        if(style.imageResourceId != BConstants.NOT_SET) {
            imageView.setImageResource(style.imageResourceId);
        }

        //Setup layout parameters to correctly position imageView
        int imageWidth = convertPxToDp(context, style.imageWidth);
        int imageHeight = convertPxToDp(context, style.imageHeight);
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(imageWidth, imageHeight);
        imageViewParams.weight = 1;
        int margin = 30;
        int imageGravity = style.imageGravity.getGravity();
        if(imageGravity == BConstants.BImageGravity.Left.getGravity()) {
            imageViewParams.setMargins(0, 0, margin, 0);
        } else if(imageGravity == BConstants.BImageGravity.Right.getGravity()) {
            imageViewParams.setMargins(margin, 0, 0, 0);
        }

        imageView.setLayoutParams(imageViewParams);

        return imageView;
    }

    private static void inflateFromResource(Context context, int resourceLayoutId, int textViewId, CharSequence text, BStyle style) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(resourceLayoutId, null);

        TextView textView = (TextView) layout.findViewById(textViewId);
        textView.setText(text);

        finalizeToastSetup(context, layout, style);
    }

    private static void finalizeToastSetup(Context context, View parentLayout, BStyle style) {
        //Setup the toast
        Toast toast = new Toast(context);
        toast.setDuration(style.duration.getDuration());
        toast.setView(parentLayout);

        //Show the new boast
        Boast boast = new Boast(toast);
        boast.show(style.autoCancel);
    }
    //endregion
}