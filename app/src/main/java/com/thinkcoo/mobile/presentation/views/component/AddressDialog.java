package com.thinkcoo.mobile.presentation.views.component;

import android.app.Activity;
import android.content.Context;

import com.bigkoo.pickerview.OptionsPickerView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.Address;
import com.thinkcoo.mobile.model.entity.Area;
import com.thinkcoo.mobile.model.entity.City;
import com.thinkcoo.mobile.model.entity.Province;
import com.thinkcoo.mobile.presentation.mvp.presenters.AddressPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.AddressView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Leevin
 * CreateTime: 2016/6/14  10:58
 */
public class AddressDialog implements AddressView, OptionsPickerView.OnOptionsSelectListener {

    private static final String TAG = "AddressDialog";
    AddressPresenter mAddressPresenter;
    OptionsPickerView mOptionsPickerView;
    Address mSelectAddress;
    Context mContext;
    OnAddressSelectedListener mOnAddressSelectedListener;
    private List<Province> mProvinceList;
    public interface OnAddressSelectedListener {
        void onAddressSelected(Address address);
    }

    @Inject
    public AddressDialog(AddressPresenter addressPresenter, Activity activity) {
        mAddressPresenter = addressPresenter;
        mContext = activity;
        initOptionsPickerView();
    }

    private void initOptionsPickerView() {
        mOptionsPickerView = new OptionsPickerView(mContext);
        mOptionsPickerView.setTitle(mContext.getString(R.string.select_city));
        mOptionsPickerView.setOnoptionsSelectListener(this);
    }

    public void setSelectAddress(Address selectAddress) {
        this.mSelectAddress = selectAddress;
    }

    @Override
    public void setSelect() {
        if (mSelectAddress == null) {
            return;
        }
        int areaCode = mSelectAddress.getCode();
        int selectedProvincePosition = getProvincePositionByCode(areaCode);
        int selectedCityPosition = getCityPositonByCode(selectedProvincePosition,areaCode);
        int selectedAreaPosition = getAreaPotionByCode(selectedProvincePosition, selectedCityPosition ,areaCode);
        mOptionsPickerView.setSelectOptions(selectedProvincePosition, selectedCityPosition, selectedAreaPosition);
    }

    public void show() {
        mAddressPresenter.attachView(this);
        mAddressPresenter.loadDD();
    }

    @Override
    public void setAddressDataDictionary(List<Province> provinceList) {
        mProvinceList = provinceList;
        ArrayList allProvinces = (ArrayList) provinceList;
        ArrayList<ArrayList<City>> allCitys = getAllCitys(provinceList);
        ArrayList<ArrayList<ArrayList<Area>>> allAreas = getAllAreas(allCitys);
        mOptionsPickerView.setPicker(allProvinces, allCitys, allAreas, true);
        mOptionsPickerView.setCyclic(false, false, false);
        mOptionsPickerView.show();
    }

    @Override
    public void onOptionsSelect(int option1, int option2, int option3) {
        mAddressPresenter.detachView(false);
        callBackOnAddressSelected(getAddressFromDD(option1, option2, option3));
    }

    private Address getAddressFromDD(int option1, int option2, int option3) {
        Province province = mProvinceList.get(option1);
        City city = province.getCityList().get(option2);
        Area area = city.getAreaList().get(option3);
        String addressName = province.getName() + city.getName() + area.getName();
        Address address = new Address(addressName,area.getCode());
        return address;
    }

    private void callBackOnAddressSelected(Address address) {
        if (null != mOnAddressSelectedListener) {
            mOnAddressSelectedListener.onAddressSelected(address);
        }
    }

    public void setOnAddressSelectedListener(OnAddressSelectedListener onAddressSelectedListener) {
        mOnAddressSelectedListener = onAddressSelectedListener;
    }

    // 二分查找法
    private <T extends Address> int getOptionPostion(List<T> list, int code) {
        int low = 0;
        int high = list.size() - 1;
        int mid = 0;
        while (low <= high) {
            mid = (low + high) / 2;
            if (list.get(mid).getCode() == code) {
                return mid;
            } else if (list.get(mid).getCode() > code) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return mid;
    }

    public int getProvincePositionByCode(int areaCode) {
        if (areaCode < 0) {
            return 0;
        }
        int provinceCode ;
        // 当只有两位省级别时 ,
        if (areaCode > 0 && areaCode < 100) {
            provinceCode = areaCode;
        } else {
            // 6位整数areaCode取前两位
            provinceCode = areaCode / 10000;
        }
        return getOptionPostion(mProvinceList, provinceCode);
    }

    private int getCityPositonByCode(int provincePosition,int areaCode) {
        if (areaCode < 0) {
            return 0;
        }
        int cityCode = areaCode / 100;
        return getOptionPostion( mProvinceList.get(provincePosition).getCityList(), cityCode);
    }

    private int getAreaPotionByCode(int provincePosition,int cityPosition,int areaCode) {
        if (areaCode < 0) {
            return 0;
        }
        return getOptionPostion(mProvinceList.get(provincePosition).getCityList().get(cityPosition).getAreaList(), areaCode);
    }

    private ArrayList<ArrayList<ArrayList<Area>>> getAllAreas(ArrayList<ArrayList<City>> allCitys) {
        ArrayList<ArrayList<ArrayList<Area>>> allArea = new ArrayList<>();
        for (ArrayList<City> cityList : allCitys) {
            ArrayList<ArrayList<Area>> areaListList = new ArrayList<>();
            for (City city : cityList) {
                areaListList.add((ArrayList<Area>) city.getAreaList());
            }
            allArea.add(areaListList);
        }
        return allArea;
    }

    private ArrayList<ArrayList<City>> getAllCitys(List<Province> provinceList) {
        ArrayList<ArrayList<City>> allCitys = new ArrayList<>();
        for (Province province : provinceList) {
            if (province.getCityList().size() == 0) {
                // 特殊省份处理,如台湾,香港,澳门
                City emptyCity = new City("", province.getCode());
                emptyCity.getAreaList().add(new Area("", province.getCode()));
                province.getCityList().add(emptyCity);
            }
            allCitys.add((ArrayList<City>) province.getCityList());
        }
        return allCitys;
    }

}
