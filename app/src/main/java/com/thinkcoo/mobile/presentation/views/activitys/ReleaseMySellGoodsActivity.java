package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerTradeComponent;
import com.thinkcoo.mobile.injector.modules.TradeModule;
import com.thinkcoo.mobile.model.entity.DataDictionary;
import com.thinkcoo.mobile.model.entity.EmptySigleLineEditContent;
import com.thinkcoo.mobile.model.entity.GoodsPhoto;
import com.thinkcoo.mobile.model.entity.MyGoods;
import com.thinkcoo.mobile.model.entity.MyGoodsDetail;
import com.thinkcoo.mobile.model.entity.SchoolLocation;
import com.thinkcoo.mobile.model.strategy.SingleLineEditAndAutoHintStrategyFactory;
import com.thinkcoo.mobile.model.strategy.SingleLineEditContent;
import com.thinkcoo.mobile.presentation.mvp.views.ReleaseMySellGoodsView;
import com.thinkcoo.mobile.presentation.views.component.DataDictionaryDialog;
import com.thinkcoo.mobile.presentation.views.dialog.TakePhotoChoiceDialog;
import com.thinkcoo.mobile.utils.TakePhotoUtils;
import com.thinkcoo.mobile.utils.PublicUIUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Robert.yao on 2016/8/11.
 */
public class ReleaseMySellGoodsActivity extends ReleaseMyGoodsActivity implements TakePhotoUtils.TakePhotoListener , ReleaseMySellGoodsView<MyGoods,MyGoodsDetail> {

    public static final int MAX_GOODS_PHOTO_COUNT = 4;

    public static Intent getReleaseMySellGoodsIntent(Context context) {
        MyGoods myGoods = new MyGoods();
        myGoods.setMyGoodsDetail(new MyGoodsDetail());
        myGoods.setType(MyGoods.SELL);
        Intent intent = new Intent(context, ReleaseMySellGoodsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(MODE_KEY, ADD_MODE);
        bundle.putParcelable(HOST_OBJECT,myGoods);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent getEditMySellGoodsIntent(Context context, MyGoods myGoods) {
        Intent intent = new Intent(context, ReleaseMySellGoodsActivity.class);
        intent.putExtra(HOST_OBJECT, myGoods);
        intent.putExtra(MODE_KEY, EDIT_MODE);
        return intent;
    }

    /**
     * 解决继承中ButterKnife注入异常的问题
     */
    class InnerViewHolder{

        @Bind(R.id.sell_gridView)
         GridView mSellGridView;
        @Bind(R.id.ac_sell_goods_details_title_name)
         TextView mAcSellGoodsDetailsTitleName;
        @Bind(R.id.ac_sell_goods_details_title_textview)
         EditText mAcSellGoodsDetailsTitleTextview;
        @Bind(R.id.ac_sell_goods_details_title)
         RelativeLayout mAcSellGoodsDetailsTitle;
        @Bind(R.id.ac_line_one)
         View mAcLineOne;
        @Bind(R.id.ac_sell_goods_details_price_textview)
         EditText mAcSellGoodsDetailsPriceTextview;
        @Bind(R.id.ac_sell_goods_details_price)
         LinearLayout mAcSellGoodsDetailsPrice;
        @Bind(R.id.ac_line_two)
         View mAcLineTwo;
        @Bind(R.id.ac_sell_goods_details_sort_name)
         TextView mAcSellGoodsDetailsSortName;
        @Bind(R.id.ac_sell_goods_details_sort_textview)
         TextView mAcSellGoodsDetailsSortTextview;
        @Bind(R.id.ac_sell_goods_details_sort_flag)
         ImageView mAcSellGoodsDetailsSortFlag;
        @Bind(R.id.ac_sell_goods_details_sort)
         RelativeLayout mAcSellGoodsDetailsSort;
        @Bind(R.id.ac_line_three)
         View mAcLineThree;
        @Bind(R.id.ac_line_four)
         View mAcLineFour;
        @Bind(R.id.ac_sell_goods_details_school_name)
         TextView mAcSellGoodsDetailsSchoolName;
        @Bind(R.id.ac_sell_goods_details_school_textview)
         TextView mAcSellGoodsDetailsSchoolTextview;
        @Bind(R.id.ac_sell_goods_details_school_flag)
         ImageView mAcSellGoodsDetailsSchoolFlag;
        @Bind(R.id.ac_sell_goods_details_school)
         RelativeLayout mAcSellGoodsDetailsSchool;
        @Bind(R.id.ac_line_five)
         View mAcLineFive;
        @Bind(R.id.ac_sell_goods_details_address_name)
         TextView mAcSellGoodsDetailsAddressName;
        @Bind(R.id.ac_sell_goods_details_address_textview)
         TextView mAcSellGoodsDetailsAddressTextview;
        @Bind(R.id.ac_sell_goods_details_address_flag)
         ImageView mAcSellGoodsDetailsAddressFlag;
        @Bind(R.id.ac_sell_goods_details_address)
         RelativeLayout mAcSellGoodsDetailsAddress;
        @Bind(R.id.ac_line_six)
         View mAcLineSix;
        @Bind(R.id.ac_sell_goods_pic_textview)
         TextView mAcSellGoodsPicTextview;
        @Bind(R.id.ac_sell_goods_pic)
         RelativeLayout mAcSellGoodsPic;
        @Bind(R.id.ac_sell_goods_details_edittext)
         EditText mAcSellGoodsDetailsEdittext;
        @Bind(R.id.ac_sell_goods_details_old_name)
         TextView mAcSellGoodsDetailsOldName;
        @Bind(R.id.ac_sell_goods_details_old_textview)
         TextView mAcSellGoodsDetailsOldTextview;
        @Bind(R.id.ac_sell_goods_details_old_flag)
         ImageView mAcSellGoodsDetailsOldFlag;
        @Bind(R.id.ac_sell_goods_details_old)
         RelativeLayout mAcSellGoodsDetailsOld;

        public InnerViewHolder(View rootView) {
            ButterKnife.bind(this,rootView);
        }
        public void release(){
            ButterKnife.unbind(this);
        }
        private String getSchoolText(){
            return mAcSellGoodsDetailsSchoolTextview.getText().toString().trim();
        }
        private String getGoodsName(){
            return mAcSellGoodsDetailsTitleTextview.getText().toString().trim();
        }
        private String getPrice(){
            return mAcSellGoodsDetailsPriceTextview.getText().toString().trim();
        }
        private String getCategory(){
            return mAcSellGoodsDetailsSortTextview.getText().toString().trim();
        }
        private String getQuality(){
            return mAcSellGoodsDetailsOldTextview.getText().toString().trim();
        }
        private SchoolLocation getSchoolAddress(){
            if (null == mAcSellGoodsDetailsAddressTextview.getTag()){
                return null;
            }
            return (SchoolLocation) mAcSellGoodsDetailsAddressTextview.getTag();
        }
        public List<GoodsPhoto> getGoodsPhotoList(){
            return mPhotoListAdapter.getGoodsPhotoList();
        }
        public String getIntroduce() {
            return mAcSellGoodsDetailsEdittext.getText().toString().trim();
        }
        public void setSchoolName(String schoolName){
            mAcSellGoodsDetailsSchoolTextview.setText(schoolName);
        }
        public void setGoodsName(String goodsName){
            mAcSellGoodsDetailsTitleName.setText(goodsName);
        }
        public void setPrice(String price){
            mAcSellGoodsDetailsPriceTextview.setText(price);
        }
        public void setCategory(String category){
            mAcSellGoodsDetailsSortTextview.setText(category);
        }
        public void setQuality(String quality){
            mAcSellGoodsDetailsOldTextview.setText(quality);
        }
        public void setSchoolAddress(SchoolLocation sl) {
            mAcSellGoodsDetailsAddressTextview.setTag(sl);
            if (null != sl){
                mAcSellGoodsDetailsAddressTextview.setText(sl.getAddress());
            }else {
                mAcSellGoodsDetailsAddressTextview.setText("");
            }
        }
        public void setIntroduce(String introduce) {
            mAcSellGoodsDetailsEdittext.setText(introduce);
        }


        public void initViewsListener(){

            mInnerViewHolder.mAcSellGoodsDetailsSort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDataDictionaryDialog.setDataDictionarySelectedListener(new DataDictionaryDialog.DataDictionarySelectedListener() {
                        @Override
                        public void onDataDictionarySelected(DataDictionary dataDictionary) {
                            mInnerViewHolder.mAcSellGoodsDetailsSortTextview.setText(dataDictionary.getDisplayName());
                        }
                    });
                    mDataDictionaryDialog.show();
                }
            });
            mInnerViewHolder.mAcSellGoodsDetailsSchool.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNavigator.navigateToSingleLineEditAndAutoHintActivity(ReleaseMySellGoodsActivity.this,
                            SingleLineEditAndAutoHintStrategyFactory.SINGLE_LINE_EDIT_TYPE_SCHOOL_SEARCH,
                            mInnerViewHolder.getSchoolText());
                }
            });
            mInnerViewHolder.mAcSellGoodsDetailsAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(mInnerViewHolder.getSchoolText())){
                        showToast("请您先选择学校");
                        return ;
                    }
                    mNavigator.navigatorLoadBaiduSchoolAddress(ReleaseMySellGoodsActivity.this,mInnerViewHolder.getSchoolText());
                }
            });
            mInnerViewHolder.mAcSellGoodsDetailsOld.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mQualityDataDictionaryDialog.setDataDictionarySelectedListener(new DataDictionaryDialog.DataDictionarySelectedListener() {
                        @Override
                        public void onDataDictionarySelected(DataDictionary dataDictionary) {
                            mInnerViewHolder.setQuality(dataDictionary.getDisplayName());
                        }
                    });
                    mQualityDataDictionaryDialog.show();
                }
            });


        }
    }

    @Inject
    TakePhotoUtils mTakePhotoUtils;
    @Inject
    TakePhotoChoiceDialog mTakePhotoChoiceDialog;
    @Inject
    PublicUIUtil mPublicUIUtil;
    @Inject
    @Named("GoodsCategory")
    DataDictionaryDialog mDataDictionaryDialog;
    @Inject
    @Named("quality")
    DataDictionaryDialog mQualityDataDictionaryDialog;

    InnerViewHolder mInnerViewHolder;

    private MyPhotoListAdapter mPhotoListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTakePhotoUtils.init(this,this);
        mTakePhotoUtils.onCreate(savedInstanceState);
        mTakePhotoChoiceDialog.setTakePhotoUtils(mTakePhotoUtils);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTakePhotoUtils.release();
        mInnerViewHolder.release();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mTakePhotoUtils.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTakePhotoUtils.onActivityResult(requestCode, resultCode, data);
        getSchoolFromActivityResult(requestCode,resultCode,data);
        getSchoolAddressFromActivityResult(requestCode,resultCode,data);
    }

    private void getSchoolAddressFromActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode!= LoadSchoolBaiduAddressActivity.REQUEST_CODE || resultCode != RESULT_OK){
            return;
        }
        if (null != data && data.hasExtra(LoadSchoolBaiduAddressActivity.KEY_RESULT)){
            SchoolLocation schoolLocations = data.getParcelableExtra(LoadSchoolBaiduAddressActivity.KEY_RESULT);
            mInnerViewHolder.setSchoolAddress(schoolLocations);
        }
    }

    private void getSchoolFromActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK){
            return;
        }
        if (SingleLineEditAndAutoHintStrategyFactory.SINGLE_LINE_EDIT_TYPE_SCHOOL_SEARCH == requestCode){
            SingleLineEditContent singleLineEditContent;
            if (null == data || !data.hasExtra(SingleLineEditActivity.KEY_CALL_BACK_CONTENT)) {
                singleLineEditContent = new EmptySigleLineEditContent();
            }else {
                singleLineEditContent = data.getParcelableExtra(SingleLineEditActivity.KEY_CALL_BACK_CONTENT);
            }
            mInnerViewHolder.setSchoolName(singleLineEditContent.getDisplayName());
            mInnerViewHolder.setSchoolAddress(null);
        }
    }

    @Override
    protected View getDetailLayout() {
        View view = getLayoutInflater().inflate(R.layout.view_my_sell_goods, null);
        mInnerViewHolder = new InnerViewHolder(view);
        mInnerViewHolder.initViewsListener();
        if (isAddMode()){
            setupPhotoList(null);
        }
        return view;
    }

    @Override
    protected void bindHostObjectToView(MyGoods hostObject){
        mInnerViewHolder.setGoodsName(hostObject.getName());
        mInnerViewHolder.setPrice(hostObject.getDisplayPrice());
        mInnerViewHolder.setCategory(hostObject.getCategory());
        mInnerViewHolder.setSchoolName(hostObject.getSchoolName());
    }

    @Override
    protected void bindDetailObjectToView(MyGoodsDetail detailObject) {
        setupPhotoList(detailObject.getGoodsPhotoList());
        mInnerViewHolder.setQuality(detailObject.getQuality());
        mInnerViewHolder.setSchoolAddress(detailObject.getLocation());
        mInnerViewHolder.setIntroduce(detailObject.getIntroduce());
    }

    @Override
    protected void initDaggerInject() {
        DaggerTradeComponent.builder().tradeModule(new TradeModule()).activityModule(getActivityModule()).applicationComponent(getApplicationComponent()).build().inject(this);
    }

    @Override
    public void onSuccess(List<String> resultList) {
        if (null == resultList || resultList.isEmpty()){
            return;
        }
        List<GoodsPhoto> goodsPhotoList = new ArrayList<>(resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            String path = resultList.get(i);
            goodsPhotoList.add(GoodsPhoto.createLocal(path));
        }
        mPhotoListAdapter.addGoodsPhoto(goodsPhotoList);
    }

    @Override
    public void onFailure(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public MyGoods getHostFromUi() {

        MyGoods myGoods = mHostObject;

        myGoods.setName(mInnerViewHolder.getGoodsName());
        myGoods.setPrice(mInnerViewHolder.getPrice());
        myGoods.setCategory(mInnerViewHolder.getCategory());
        myGoods.setSchoolName(mInnerViewHolder.getSchoolText());

        myGoods.getMyGoodsDetail().setQuality(mInnerViewHolder.getQuality());
        myGoods.getMyGoodsDetail().setLocation(mInnerViewHolder.getSchoolAddress());
        myGoods.getMyGoodsDetail().setGoodsPhotoList(mInnerViewHolder.getGoodsPhotoList());
        myGoods.getMyGoodsDetail().setIntroduce(mInnerViewHolder.getIntroduce());

        return myGoods;
    }

    private void setupPhotoList(List<GoodsPhoto> goodsPhotos) {
        if (null == mPhotoListAdapter) {
            mPhotoListAdapter = new MyPhotoListAdapter();
            mInnerViewHolder.mSellGridView.setAdapter(mPhotoListAdapter);
            mInnerViewHolder.mSellGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0 && !mPhotoListAdapter.plusSignIsGone()) {
                        mTakePhotoChoiceDialog.showDialog(getActivityContext(), MAX_GOODS_PHOTO_COUNT);
                    } else {
                        //mNavigator.navigateToViewBigImageActivity(ReleaseMySellGoodsActivity.this, mPhotoListAdapter.getItem(position));
                    }
                }
            });
        }
        if (null != goodsPhotos && !goodsPhotos.isEmpty()){
            mPhotoListAdapter.addGoodsPhoto(goodsPhotos);
        }
    }

    private class MyPhotoListAdapter extends BaseAdapter {

        private List<GoodsPhoto> mGoodsPhotoList;

        public MyPhotoListAdapter() {
            mGoodsPhotoList = new ArrayList<>();
            addPlusSignToFirstPosition();
        }

        private void addPlusSignToFirstPosition() {
            mGoodsPhotoList.add(0, GoodsPhoto.createPlusSign());
        }

        @Override
        public int getCount() {
            return mGoodsPhotoList.size();
        }

        @Override
        public GoodsPhoto getItem(int position) {
            return mGoodsPhotoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            GoodsPhoto goodsPhoto = mGoodsPhotoList.get(position);
            View view;
            if (GoodsPhoto.PLUS_SIGN_PHOTO_ID.equals(goodsPhoto.getPhotoId())) {
                view = inflateViewByLayoutId(R.layout.item_add_gv);
            } else {
                view = inflateViewByLayoutId(R.layout.item_goods_photo_and_delete);
                ImageButton delIb = (ImageButton) view.findViewById(R.id.delete);
                ImageView goodsIcon = (ImageView) view.findViewById(R.id.pic);
                delIb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mReleaseMyGoodsPresenter.deleteGoodsPhoto(mGoodsPhotoList.get(position),position);
                    }
                });
                Glide.with(ReleaseMySellGoodsActivity.this).load(goodsPhoto.getWorkPhotoPath()).into(goodsIcon);
            }
            return view;
        }

        private View inflateViewByLayoutId(int item_add_gv) {
            return getLayoutInflater().inflate(item_add_gv, null);
        }

        public void removeItemByPosition(int position) {
            if (!isAvailablePosition(position)) {
                return;
            }
            int oldSize = getRealGoodsPhotoListCount();
            mGoodsPhotoList.remove(position);
            if (oldSize == MAX_GOODS_PHOTO_COUNT) {
                addPlusSignToFirstPosition();
            }
            notifyDataSetChanged();
        }

        private boolean isAvailablePosition(int position) {
            return position > 0 && position < mGoodsPhotoList.size();
        }

        public void addGoodsPhoto(List<GoodsPhoto> goodsPhotoList) {
            if (null == mGoodsPhotoList || mGoodsPhotoList.isEmpty()) {
                return;
            }
            if (getFreeSpace() <= goodsPhotoList.size()) {
                removePlusSign();
            }
            mGoodsPhotoList.addAll(goodsPhotoList);
            notifyDataSetChanged();
        }

        public List<GoodsPhoto> getGoodsPhotoList() {
            return mGoodsPhotoList;
        }

        public boolean plusSignIsGone() {
            return getRealGoodsPhotoListCount() == MAX_GOODS_PHOTO_COUNT;
        }

        private void removePlusSign() {
            mGoodsPhotoList.remove(0);
        }

        private int getRealGoodsPhotoListCount() {
            return mGoodsPhotoList.size() - 1;//remove plus sign
        }

        private int getFreeSpace() {
            return MAX_GOODS_PHOTO_COUNT - getRealGoodsPhotoListCount();
        }
    }

    @Override
    public void deletePhotoByPosition(int position) {
        mPhotoListAdapter.removeItemByPosition(position);
    }

}
