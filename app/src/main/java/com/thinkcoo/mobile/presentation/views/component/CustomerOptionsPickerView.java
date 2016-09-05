package com.thinkcoo.mobile.presentation.views.component;

import android.content.Context;


import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;

/**
 * Created by  wyy
 * CreateTime: 2016/6/6  15:40
 */
public class CustomerOptionsPickerView extends OptionsPickerView{

    public CustomerOptionsPickerView(Context context , final ArrayList<?> options1Items, String tittle) {
        super(context);
        this.setPicker(options1Items);
        setTitle(tittle);
        setCyclic(false, true, true);
        setSelectOptions(1, 1, 1);
    }

    public CustomerOptionsPickerView(Context context ,   ArrayList<?> optionsItems,
                                     ArrayList<ArrayList<?>> options2Items, String tittle) {
        super(context);
        setPicker(optionsItems,options2Items,true);
        setTitle(tittle);
        setCyclic(false, true, true);
        setSelectOptions(1, 1, 1);

    }

    public CustomerOptionsPickerView(Context context , final ArrayList<?> options1Items,
                                     ArrayList<ArrayList<?>> options2Items,ArrayList<ArrayList<ArrayList<?>>> options3Items, String tittle) {
        super(context);
        setPicker(options1Items,options2Items,options3Items,true);
        setTitle(tittle);
        setCyclic(false, true, true);
        setSelectOptions(1, 1, 1);

    }
}

