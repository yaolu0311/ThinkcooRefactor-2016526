package com.example.administrator.publicmodule.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.example.administrator.publicmodule.R;

/**
 * Created by cys on 2016/8/6
 */
public class CustomToggleButton extends RelativeLayout implements View.OnClickListener{

    private ToggleButton mToggleButton;

    public CustomToggleButton(Context context) {
        this(context, null);
    }

    public CustomToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        addToggleButton();
    }

    private void addToggleButton() {
        mToggleButton = (ToggleButton) LayoutInflater.from(getContext()).inflate(R.layout.toggle_button, null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(mToggleButton, params);
    }

    @Override
    public void onClick(View v) {

    }

    public void changeToggleStatus(){
        if (mToggleButton == null) {
            return;
        }
        mToggleButton.setChecked(!mToggleButton.isChecked());
    }
}
