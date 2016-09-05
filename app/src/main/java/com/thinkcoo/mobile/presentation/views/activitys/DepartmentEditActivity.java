package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;

import com.thinkcoo.mobile.model.strategy.SingleLineEditAndAutoHintStrategy;

/**
 * Created by Robert.yao on 2016/6/21.
 */
public class DepartmentEditActivity extends SingleLineEditAndAutoHintActivity {

    public static  final String SCHOOL_NAME = "school_name";

    public static Intent getDepartmentEditIntent(Context context, SingleLineEditAndAutoHintStrategy singleLineEditAndAutoHintStrategy , String schoolName){
        Intent intent = new Intent(context,DepartmentEditActivity.class);
        intent.putExtra(KEY_STRATEGY,singleLineEditAndAutoHintStrategy);
        intent.putExtra(SCHOOL_NAME,schoolName);
        return intent;
    }

    private String school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSchoolFromIntent();
        getEditAndAutoHinPresenter().startSearch(getDataDictionaryStrategy(),getEditText().getText().toString().trim(),school);
    }

    private void initSchoolFromIntent() {

        if (!getIntent().hasExtra(SCHOOL_NAME)){
            school = "";
        }else {
            school = getIntent().getStringExtra(SCHOOL_NAME);
        }
    }

    @Override
    protected void afterEditTextChanged(Editable s) {
        String input = getEditText().getText().toString().trim();
        getEditText().setSelection(getEditText().length());
        mEditAndAutoHinPresenter.startSearch(getDataDictionaryStrategy(),input,school);
    }
}
