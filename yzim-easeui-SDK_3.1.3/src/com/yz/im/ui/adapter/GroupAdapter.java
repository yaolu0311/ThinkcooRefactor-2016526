package com.yz.im.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.R;
import com.yz.im.model.db.entity.Group;
import com.yz.im.utils.GlideRoundTransform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by cys on 2016/7/22
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private Context mContext;
    private List<Group> mGroups;
    private List<Group> mCopyGroups;
    private OnItemClickListener mItemClickListener;
    private GroupFilter mFilter;
    private boolean originalDataIsChange = true;

    public GroupAdapter(Context context, List<Group> groups) {
        mContext = context;
        mGroups = groups;
        mCopyGroups = new ArrayList<>();
        mCopyGroups.addAll(mGroups);
    }


    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_group_list, null);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, final int position) {
        final Group group = mGroups.get(position);
        Glide.with(mContext).load(group.getGroupImage()).transform(new GlideRoundTransform(mContext, 10)).error(R.drawable.default_group).into(holder.headPortraits);
        holder.name.setText(group.getGroupName());
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener == null) {
                    throw new NullPointerException("GroupAdapter's OnItemClickListener is null");
                }
                mItemClickListener.onClick(group, position);
            }
        });
    }

    public void refreshData(List<Group> list){
        mGroups.clear();
        mGroups.addAll(list);
        notifyDataSetChanged();
        if (originalDataIsChange) {
            mCopyGroups.clear();
            mCopyGroups.addAll(list);
        }
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {

        private ImageView headPortraits;
        private TextView name;
        private RelativeLayout mRelativeLayout;

        public GroupViewHolder(View itemView) {
            super(itemView);
            headPortraits = (ImageView) itemView.findViewById(R.id.img_head_portraits);
            name = (TextView) itemView.findViewById(R.id.tv_group_name);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_group);
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(Group group, int position);
    }

    public void filter(CharSequence s) {
        if (mFilter == null) {
            mFilter = new GroupFilter(mCopyGroups);
        }
        mFilter.filter(s);
    }

    class GroupFilter extends Filter {

        private List<Group> originalData;

        public GroupFilter(List<Group> originalData) {
            this.originalData = originalData;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (originalData == null) {
                originalData = new ArrayList<>();
            }
            getFilterResult(constraint, results);
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mGroups.clear();
            mGroups.addAll((Collection<Group>) results.values);
            originalDataIsChange = false;
            notifyDataSetChanged();
            originalDataIsChange = true;
        }

        private void getFilterResult(CharSequence constraint, FilterResults results) {
            if (constraint == null || constraint.length() == 0) {
                results.values = mCopyGroups;
                results.count = mCopyGroups.size();
            } else {
                String prefixString = constraint.toString();
                final int count = originalData.size();
                final ArrayList<Group> newValues = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    final Group bean = originalData.get(i);
                    String username = bean.getGroupName();

                    if (username.toUpperCase().startsWith(prefixString.toUpperCase())) {
                        newValues.add(bean);
                    }
//                    else {
//                        final String[] words = username.split(" ");
//                        final int wordCount = words.length;
//                        for (int k = 0; k < wordCount; k++) {
//                            if (words[k].startsWith(prefixString)) {
//                                newValues.add(bean);
//                                break;
//                            }
//                        }
//                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }
        }
    }
}
