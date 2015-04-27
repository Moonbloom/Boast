package com.moonbloom.boast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public final class Boast {

    //region Variables
    //Debug TAG
    private static transient final String TAG = Boast.class.getSimpleName();

    private static int customLayoutResourceId = 0;
    private static int customTextViewId = 0;

    private static final int imageViewId = 0x100;
    private static final int textViewId = 0x101;

    private static BStyle defaultBStyle;

    private volatile static Boast globalBoast = null;

    private Toast internalToast;

    static {
        defaultBStyle = BStyle.INFO;
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
     * Default value: {@link BStyle#INFO}.
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
     * @param style {@link BStyle} e.g. {@link BStyle#INFO}).
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
     * @param layoutResource Resource layout e.g. R.layout.custom_toast_layout.
     * @param textViewId TextView ID e.g. R.id.custom_toast_text.
     * @param text Text to show in the {@link Boast}.
     */
    public static void makeText(Context context, int layoutResource, int textViewId, CharSequence text) {
        inflateFromResource(context, layoutResource, textViewId, text, defaultBStyle);
    }
    //endregion

    //region Internal helper functions
    private static int convertPxToDp(Context context, int pixels) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(pixels * density);
    }

    private static void inflateProgrammatically(Context context, CharSequence text, BStyle style) {
        RelativeLayout relativeLayout = initializeContentView(context, text, style);

        finalizeToastSetup(context, relativeLayout, style);
    }

    @SuppressLint("RtlHardcoded")
    private static RelativeLayout initializeContentView(Context context, CharSequence text, BStyle style) {
        //Create parent layout
        RelativeLayout relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams relLayoutParams = new RelativeLayout.LayoutParams(style.widthInPixels, style.heightInPixels);
        relativeLayout.setLayoutParams(relLayoutParams);

        //Set background color
        if (style.backgroundColorValue != BConstants.NOT_SET) {
            relativeLayout.setBackgroundColor(style.backgroundColorValue);
        } else {
            relativeLayout.setBackgroundColor(context.getResources().getColor(style.backgroundColorResourceId));
        }

        //Set padding
        int horizontalPaddingDp = convertPxToDp(context, style.horizontalPaddingInPixels);
        int verticalPaddingDp = convertPxToDp(context, style.verticalPaddingInPixels);
        relativeLayout.setPadding(horizontalPaddingDp, verticalPaddingDp, horizontalPaddingDp, verticalPaddingDp);

        //Only initialize imageView if one is requested
        ImageView imageView = null;
        if ((style.imageDrawable != null) || (style.imageResourceId != BConstants.NOT_SET)) {
            imageView = initializeImageView(context, style);
            relativeLayout.addView(imageView);
        }

        //Initialize textView
        TextView textView = initializeTextView(context, style, text);

        //Setup layout parameters for textView to correctly position it according to the imageView
        RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (imageView != null) {
            int imageGravity = style.imageGravity.getGravity();
            if(imageGravity == Gravity.LEFT) {
                textViewParams.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
            } else if(imageGravity == Gravity.RIGHT) {
                textViewParams.addRule(RelativeLayout.LEFT_OF, imageView.getId());
            }
        }

        //Set correct text gravity
        int textGravity = style.textGravity.getGravity();
        if ((textGravity & Gravity.CENTER) != 0) {
            textViewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        } else if ((textGravity & Gravity.CENTER_VERTICAL) != 0) {
            textViewParams.addRule(RelativeLayout.CENTER_VERTICAL);
        } else if ((textGravity & Gravity.CENTER_HORIZONTAL) != 0) {
            textViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        }

        relativeLayout.addView(textView, textViewParams);

        return relativeLayout;
    }

    private static TextView initializeTextView(Context context, BStyle style, CharSequence text) {
        TextView textView = new TextView(context);

        textView.setId(textViewId);

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

    @SuppressLint("RtlHardcoded")
    private static ImageView initializeImageView(Context context, BStyle style) {
        ImageView imageView = new ImageView(context);

        imageView.setId(imageViewId);

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
        RelativeLayout.LayoutParams imageViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int margin = 30;
        int imageGravity = style.imageGravity.getGravity();
        if(imageGravity == Gravity.LEFT) {
            imageViewParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            imageViewParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            imageViewParams.setMargins(0, 0, margin, 0);
        } else if(imageGravity == Gravity.RIGHT) {
            imageViewParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            imageViewParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
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