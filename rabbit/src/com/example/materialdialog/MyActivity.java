package com.example.materialdialog;

import me.drakeet.materialdialog.MaterialDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.m.rabbit.R;


public class MyActivity extends ActionBarActivity {

    MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_materialdialog);
    }

    public void init(View v) {
        mMaterialDialog = new MaterialDialog(this);

        Toast.makeText(getApplicationContext(), "Initializes successfully.", Toast.LENGTH_SHORT).show();
    }

    public void show(View v) {
        if (mMaterialDialog != null) {
            mMaterialDialog.setTitle("MaterialDialog");
            mMaterialDialog.setMessage("Hi! This is a MaterialDialog. It's very easy to use, you just new and show() it " +
                    "then the beautiful AlertDialog will show automatedly. It is artistic, conforms to Google Material Design." +
                    " I hope that you will like it, and enjoy it. ^ ^");
            //mMaterialDialog.setBackgroundResource(R.drawable.background);
            mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDialog.dismiss();
                    Toast.makeText(MyActivity.this, "Ok", Toast.LENGTH_LONG).show();

                }
            });

            mMaterialDialog.setNegativeButton("CANCLE", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDialog.dismiss();
                    Toast.makeText(MyActivity.this, "Cancle", Toast.LENGTH_LONG).show();
                }
            });
            mMaterialDialog.setCanceledOnTouchOutside(true);
            // You can change the message anytime.
            // mMaterialDialog.setTitle("提示");
            mMaterialDialog.show();
            // You can change the message anytime.
            // mMaterialDialog.setMessage("嗨！这是一个 MaterialDialog. 它非常方便使用，你只需将它实例化，这个美观的对话框便会自动地显示出来。它简洁小巧，完全遵照 Google 2014 年发布的 Material Design 风格，希望你能喜欢它！^ ^");
        } else {
            Toast.makeText(getApplicationContext(), "You should init firstly!", Toast.LENGTH_SHORT).show();
        }
    }

    static int i = 0;

    public void setView(View v) {
        switch (v.getId()) {
            case R.id.button_set_view: {
                if (mMaterialDialog != null) {
                    EditText contentView = new EditText(this);
                    mMaterialDialog.setView(contentView);
                    mMaterialDialog.show();
                } else {
                    Toast.makeText(getApplicationContext(), "You should init firstly!", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.button_set_background: {
                mMaterialDialog = new MaterialDialog(this);
                if (mMaterialDialog != null) {
                    if (i % 2 != 0) {
                        mMaterialDialog.setBackgroundResource(R.drawable.background);
                    } else {
                        Resources res = getResources();
                        Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.background2);
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bmp);
                        mMaterialDialog.setBackground(bitmapDrawable);
                    }
                    mMaterialDialog.show();
                    i++;
                } else {
                    Toast.makeText(getApplicationContext(), "You should init firstly!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
