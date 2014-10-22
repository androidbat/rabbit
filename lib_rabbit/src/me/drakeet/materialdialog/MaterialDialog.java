package me.drakeet.materialdialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m.rabbit.R;

/**
 * Created by drakeet on 9/28/14.
 */
public class MaterialDialog {

    private boolean                   mCancel;
    private Context                   mContext;
    private AlertDialog               mAlertDialog;
    private MaterialDialog.Builder    mBuilder;
    private View                      mView;
    private int                       mTitleResId;
    private CharSequence              mTitle;
    private int                       mMessageResId;
    private CharSequence              mMessage;
    private Button                    mPositiveButton;
    private LinearLayout.LayoutParams mLayoutParams;
    private Button                    mNegativeButton;
    private boolean mHasShow = false;
    private Drawable mBackgroundDrawable;
    private int      mBackgroundResId;

    public MaterialDialog(Context context) {
        this.mContext = context;
    }

    public void show() {
        if (mHasShow == false)
            mBuilder = new Builder();
        else
            mAlertDialog.show();
        mHasShow = true;
    }

    public void setView(View view) {
        mView = view;
        if (mBuilder != null) {
            mBuilder.setView(view);
        }
    }

    public void setBackground(Drawable drawable) {
        mBackgroundDrawable = drawable;
        if (mBuilder != null) {
            mBuilder.setBackground(mBackgroundDrawable);
        }
    }

    public void setBackgroundResource(int resId) {
        mBackgroundResId = resId;
        if (mBuilder != null) {
            mBuilder.setBackgroundResource(mBackgroundResId);
        }
    }


    public void dismiss() {
        mAlertDialog.dismiss();
    }

    private int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public MaterialDialog setTitle(int resId) {
        mTitleResId = resId;
        if (mBuilder != null)
            mBuilder.setTitle(resId);
        return this;
    }

    public MaterialDialog setTitle(CharSequence title) {
        mTitle = title;
        if (mBuilder != null) {
            mBuilder.setTitle(title);
        }
        return this;
    }

    public MaterialDialog setMessage(int resId) {
        mMessageResId = resId;
        if (mBuilder != null)
            mBuilder.setMessage(resId);
        return this;
    }

    public MaterialDialog setMessage(CharSequence message) {
        mMessage = message;
        if (mBuilder != null)
            mBuilder.setMessage(message);
        return this;
    }

    public MaterialDialog setPositiveButton(String text, final View.OnClickListener listener) {
        mPositiveButton = new Button(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        mPositiveButton.setLayoutParams(params);
        mPositiveButton.setBackgroundResource(R.drawable.material_card_nos);
        mPositiveButton.setTextColor(Color.argb(255, 35, 159, 242));
        mPositiveButton.setText(text);
        mPositiveButton.setGravity(Gravity.CENTER);
        mPositiveButton.setTextSize(14);
        mPositiveButton.setOnClickListener(listener);
        return this;
    }

    public MaterialDialog setCanceledOnTouchOutside(boolean cancel) {
        this.mCancel = cancel;
        return this;
    }

    /**
     * set negative button
     *
     * @param text     the name of button
     * @param listener
     */
    public MaterialDialog setNegativeButton(String text, final View.OnClickListener listener) {
        mNegativeButton = new Button(mContext);
        mLayoutParams = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams.weight = 1;
        mNegativeButton.setLayoutParams(mLayoutParams);
        mNegativeButton.setBackgroundResource(R.drawable.material_card_nos);
        mNegativeButton.setText(text);
        mNegativeButton.setTextColor(Color.argb(222, 0, 0, 0));
        mNegativeButton.setTextSize(14);
        mNegativeButton.setGravity(Gravity.CENTER);
        mNegativeButton.setOnClickListener(listener);
        return this;
    }


    private class Builder {

        private TextView     mTitleView;
        private TextView     mMessageView;
        private Window       mAlertDialogWindow;
        private LinearLayout mButtonLayout;

        private Builder() {
            mAlertDialog = new AlertDialog.Builder(mContext).create();
            mAlertDialog.show();
            mAlertDialogWindow = mAlertDialog.getWindow();
            mAlertDialogWindow.setContentView(R.layout.layout_materialdialog);
            mTitleView = (TextView) mAlertDialogWindow.findViewById(R.id.title);
            mMessageView = (TextView) mAlertDialogWindow.findViewById(R.id.message);
            mButtonLayout = (LinearLayout) mAlertDialogWindow.findViewById(R.id.buttonLayout);
            if (mView != null) {
                LinearLayout linearLayout = (LinearLayout) mAlertDialogWindow.findViewById(R.id.contentView);
                linearLayout.removeAllViews();
                linearLayout.addView(mView);
            }
            if (mTitleResId != 0) {
                setTitle(mTitleResId);
            }
            if (mTitle != null) {
                setTitle(mTitle);
            }
            if (mMessageResId != 0) {
                setMessage(mMessageResId);
            }
            if (mMessage != null) {
                setMessage(mMessage);
            }
            if (mPositiveButton != null) {
                mButtonLayout.addView(mPositiveButton);
            }
            if (mLayoutParams != null && mNegativeButton != null) {
                if (mButtonLayout.getChildCount() > 0) {
                    mNegativeButton.setLayoutParams(mLayoutParams);
                    mButtonLayout.addView(mNegativeButton, 1);
                } else {
                    mNegativeButton.setLayoutParams(mLayoutParams);
                    mButtonLayout.addView(mNegativeButton);
                }
            }
            if (mBackgroundResId != 0) {
                LinearLayout linearLayout = (LinearLayout) mAlertDialogWindow.findViewById(R.id.material_background);
                linearLayout.setBackgroundResource(mBackgroundResId);
            }
            if (mBackgroundDrawable != null) {
                LinearLayout linearLayout = (LinearLayout) mAlertDialogWindow.findViewById(R.id.material_background);
                linearLayout.setBackground(mBackgroundDrawable);
            }
            mAlertDialog.setCanceledOnTouchOutside(mCancel);
        }

        public void setTitle(int resId) {
            mTitleView.setText(resId);
        }

        public void setTitle(CharSequence title) {
            mTitleView.setText(title);
        }

        public void setMessage(int resId) {
            mMessageView.setText(resId);
        }

        public void setMessage(CharSequence message) {
            mMessageView.setText(message);
        }

        /**
         * set positive button
         *
         * @param text     the name of button
         * @param listener
         */
        public void setPositiveButton(String text, final View.OnClickListener listener) {
            Button button = new Button(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight=1;
            button.setLayoutParams(params);
            button.setBackgroundResource(R.drawable.material_card);
            button.setTextColor(Color.argb(255, 35, 159, 242));
            button.setText(text);
            button.setGravity(Gravity.CENTER);
            button.setTextSize(14);
            button.setPadding(dip2px(12), 0, dip2px(32), dip2px(16));
            button.setOnClickListener(listener);
            mButtonLayout.addView(button);
        }

        /**
         * set negative button
         *
         * @param text     the name of button
         * @param listener
         */
        public void setNegativeButton(String text, final View.OnClickListener listener) {
            Button button = new Button(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight=1;
            button.setLayoutParams(params);
            button.setBackgroundResource(R.drawable.material_card);
            button.setText(text);
            button.setTextColor(Color.argb(222, 0, 0, 0));
            button.setTextSize(14);
            button.setGravity(Gravity.CENTER);
            button.setPadding(0, 0, 0, dip2px(16));
            button.setOnClickListener(listener);
            if (mButtonLayout.getChildCount() > 0) {
                params.setMargins(20, 0, 10, 0);
                button.setLayoutParams(params);
                mButtonLayout.addView(button, 1);
            } else {
                button.setLayoutParams(params);
                mButtonLayout.addView(button);
            }
        }

        public void setView(View view) {
            LinearLayout l = (LinearLayout) mAlertDialogWindow.findViewById(R.id.contentView);
            l.removeAllViews();
            l.addView(view);
        }

        public void setBackground(Drawable drawable) {
            LinearLayout linearLayout = (LinearLayout) mAlertDialogWindow.findViewById(R.id.material_background);
            linearLayout.setBackground(drawable);
        }

        public void setBackgroundResource(int resId) {
            LinearLayout linearLayout = (LinearLayout) mAlertDialogWindow.findViewById(R.id.material_background);
            linearLayout.setBackgroundResource(resId);
        }
    }
}