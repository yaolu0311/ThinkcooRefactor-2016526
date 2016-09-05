package com.thinkcoo.mobile.presentation.views.adapter.Schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.thinkcoo.mobile.R;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author ：ml on 2016/8/3
 */
public class ResultClassAdapter extends RecyclerView.Adapter {


    private List<String> mList ;
    private Context mContext;
    private static final int ITEM_BUTTON = 1;
    private static final int ITEM_CLASSLIST = 2;

    public ResultClassAdapter(Context context, ArrayList<String> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sc_rockcallresult,parent,false);
        if (viewType == ITEM_BUTTON) {
            View buttonView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rock_wholebutton, parent, false);
            return new ButtonViewHolder(buttonView);
        } else if (viewType == ITEM_CLASSLIST) {
            View classView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rock_classlist, parent, false);
            return new ClassViewHolder(classView);
        }
        throw new IllegalArgumentException("UNKNOWN_ITEMTYPE");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == ITEM_BUTTON) {
            OnBindButtonViewHolder((ButtonViewHolder) holder, position);
        } else if (itemViewType == ITEM_CLASSLIST) {
            OnBindClassViewHolder((ClassViewHolder) holder, position);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_BUTTON : ITEM_CLASSLIST;
    }

    private void OnBindClassViewHolder(ClassViewHolder holder, int position) {
                // 显示button
            holder.tvClassname.setText(mList.get(position));
            holder.tvSchoolname.setText(mList.get(position));
    }

    private void OnBindButtonViewHolder(ButtonViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : mList.size();
        //return mList.size();
    }

    @OnClick(R.id.btn_whole)
    public void onClick() {
        // TODO: 2016/8/4    展示到下方的listview
    }


    public class ClassViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_classname)
        TextView tvClassname;
        @Bind(R.id.tv_schoolname)
        TextView tvSchoolname;

        public ClassViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public class ButtonViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.btn_whole)
        Button btnWhole;
        public ButtonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }


}
