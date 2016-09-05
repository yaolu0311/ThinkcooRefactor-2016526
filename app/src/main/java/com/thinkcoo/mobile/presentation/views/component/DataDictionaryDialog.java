package com.thinkcoo.mobile.presentation.views.component;

import android.app.Activity;
import android.content.Context;
import com.bigkoo.pickerview.OptionsPickerView;
import com.thinkcoo.mobile.model.entity.DataDictionary;
import com.thinkcoo.mobile.model.strategy.DataDictionaryStrategy;
import com.thinkcoo.mobile.presentation.mvp.presenters.DataDictionaryDialogPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.DataDictionaryDialogView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Robert.yao on 2016/6/14.
 */
@Singleton
public class DataDictionaryDialog implements DataDictionaryDialogView , OptionsPickerView.OnOptionsSelectListener {

    private List<DataDictionary> mDataDictionaryList;
    private OptionsPickerView mOptionsPickerView;
    private Context mContext;
    private DataDictionary mDefaultDataDictionary;
    private DataDictionarySelectedListener mDataDictionarySelectedListener;
    private DataDictionaryStrategy mDataDictionaryStrategy;

    public DataDictionaryDialogPresenter mDataDictionaryDialogPresenter;

    public interface DataDictionarySelectedListener{
        void onDataDictionarySelected(DataDictionary dataDictionary);
    }

    @Inject
    public DataDictionaryDialog(Activity context , DataDictionaryStrategy dataDictionaryStrategy , DataDictionaryDialogPresenter dataDictionaryDialogPresenter ) {
        mContext = context;
        mDataDictionaryStrategy = dataDictionaryStrategy;
        mDataDictionaryDialogPresenter = dataDictionaryDialogPresenter;
        initOptionsPickerView();
    }
    private void initOptionsPickerView() {
        mOptionsPickerView = new OptionsPickerView(mContext);
        mOptionsPickerView.setOnoptionsSelectListener(this);
        mOptionsPickerView.setTitle(getString(mDataDictionaryStrategy.getDataDictionaryTitle()));
    }
    public void setDefaultDataDictionary(DataDictionary dataDictionary){
        mDefaultDataDictionary = dataDictionary;
    }
    public void show(){
        mDataDictionaryDialogPresenter.attachView(this);
        mDataDictionaryDialogPresenter.queryDataDictionary(mDataDictionaryStrategy);
    }

    @Override
    public void showDialogByDataDictionaryList(List<DataDictionary> dataDictionaries) {
        mDataDictionaryList = dataDictionaries;
        mOptionsPickerView.setPicker((ArrayList)dataDictionaries);
        mOptionsPickerView.setCyclic(false);
        mOptionsPickerView.show();
    }

    @Override
    public void selectDefaultDataDictionaryIfNeed() {
        if (null == mDefaultDataDictionary){
            return;
        }
        for (int i = 0; i < mDataDictionaryList.size(); i++) {
            DataDictionary item = mDataDictionaryList.get(i);
            if (mDefaultDataDictionary.getDisplayName().equals(item.getDisplayName())){
                mOptionsPickerView.setSelectOptions(i);
            }
        }
    }

    @Override
    public void onOptionsSelect(int options1, int option2, int options3) {
        mDataDictionaryDialogPresenter.detachView(false);
        callBackDataDictionarySelectedListener(getSelectDataDictionary(options1));
    }

    private void callBackDataDictionarySelectedListener(DataDictionary selectDataDictionary) {
        if (null != mDataDictionarySelectedListener){
            mDataDictionarySelectedListener.onDataDictionarySelected(selectDataDictionary);
        }
    }

    private DataDictionary getSelectDataDictionary(int options1) {
        if (null != mDataDictionaryList && optionsInListScope(options1)){
            return mDataDictionaryList.get(options1);
        }
        return DataDictionary.NULL_DATA_DICTIONARY;
    }

    private boolean optionsInListScope(int options1) {
        return mDataDictionaryList.size() > 0 && (options1 >= 0 && options1 < mDataDictionaryList.size());
    }

    private String getString(int resId){
        return mContext.getString(resId);
    }
    public void setDataDictionarySelectedListener(DataDictionarySelectedListener dataDictionarySelectedListener) {
        mDataDictionarySelectedListener = dataDictionarySelectedListener;
    }
}
