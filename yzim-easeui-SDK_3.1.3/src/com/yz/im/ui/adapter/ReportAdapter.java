package com.yz.im.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.yz.im.model.entity.ReportEntity;

import java.util.List;

/**
 * Created by cys on 2016/7/26
 */
public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private Context mContext;
    private List<ReportEntity> mEntityList;

    public ReportAdapter(Context context, List<ReportEntity> entityList) {
        mContext = context;
        mEntityList = entityList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_report, null);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ReportEntity entity = mEntityList.get(position);
        holder.mTextView.setText(entity.getTitle());
        holder.mCheckBox.setChecked(entity.isChecked());
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entity.setChecked(!holder.mCheckBox.isChecked());
                holder.mCheckBox.setChecked(!holder.mCheckBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox mCheckBox;
        private LinearLayout mLayout;
        private TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox_report);
            mLayout = (LinearLayout) itemView.findViewById(R.id.layout_item_report);
            mTextView = (TextView) itemView.findViewById(R.id.tv_report_reason);
        }
    }

    public String getCheckResult() {
        StringBuilder builder = new StringBuilder();
        for (ReportEntity entity : mEntityList) {
            if (entity.isChecked()) {
                builder.append(entity.getIndex()).append(",");
            }
        }
        String result = builder.toString();
        if (TextUtils.isEmpty(result)) {
            return "";
        }
        return result.substring(0, result.length()-1);
    }
}
