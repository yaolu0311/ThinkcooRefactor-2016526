package com.yz.im.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.yz.im.model.entity.BaseSortEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by cys on 2016/7/7
 * in com.yz.im.ui.adapter
 */
public abstract class BaseContactListAdapter<T extends BaseSortEntity> extends BaseAdapter implements SectionIndexer {

    public static final int LEFT_CHECK_BOX = 0X0001;
    public static final int RIGHT_CHECK_BOX = 0X0002;
    public static final int NONE_CHECK_BOX = 0X0003;

    protected Context mContext;
    private List<T> mList;
    private List<T> mCopyList;

    private SparseIntArray positionOfSection;
    private SparseIntArray sectionOfPosition;

    private boolean originalDataIsChange = true;
    private ContactListFilter mFilter;

    private int checkBoxType = NONE_CHECK_BOX;
    private Selection mSelection;
    /**
     * 是否是群成员列表
     */
    private boolean isGroupMember = false;
    private boolean isShowInitLetter = true;

    /**
     * @param context
     * @param list
     * @param checkBoxType values={LEFT_CHECK_BOX = 0x0001, RIGHT_CHECK_BOX= 0x0002}
     */
    public BaseContactListAdapter(Context context, List<T> list, int checkBoxType, boolean isGroupMember, boolean isShowInitLetter) {
        this.checkBoxType = checkBoxType;
        mList = list;
        mContext = context;
        mCopyList = new ArrayList<>();
        mCopyList.addAll(list);
        this.isGroupMember = isGroupMember;
        this.isShowInitLetter = isShowInitLetter;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setSelection(Selection selection) {
        mSelection = selection;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_contact_list, null);
            holder = new ViewHolder(convertView, checkBoxType, isGroupMember);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final T bean = mList.get(position);

        bindDataToView(holder, bean, position);

        if (isShowInitLetter) {
            changeInitialLetterLayout(position, holder, bean);
        }

        if (checkBoxType != NONE_CHECK_BOX) {
            setCheckBoxStyle(holder, bean);
            setCheckboxStatus(holder, bean);
            holder.mCheckLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.isCanToggle()) {
                        holder.mCheckBox.toggle();
                        bean.setCheck(holder.mCheckBox.isChecked());
                    }
                }
            });
        }
        return convertView;
    }

    private void changeInitialLetterLayout(int position, ViewHolder holder, T bean) {
        String initialLetter = bean.getFirstLetter();
        if (position == 0 || initialLetter != null && !initialLetter.equals(getItem(position - 1).getFirstLetter())) {
            if (TextUtils.isEmpty(initialLetter)) {
                holder.mIndex.setVisibility(View.GONE);
            } else {
                holder.mIndex.setVisibility(View.VISIBLE);
                holder.mIndex.setText(initialLetter);
            }
        } else {
            holder.mIndex.setVisibility(View.GONE);
        }
    }

    private void setCheckBoxStyle(ViewHolder holder, T bean) {
        if (!bean.isCanToggle()) {   //不支持改变状态
            holder.mCheckBox.setBackgroundResource(R.drawable.icon_unchange_checked);
            holder.mCheckBox.setButtonDrawable(null);
        } else {
            holder.mCheckBox.setBackgroundResource(R.drawable.style_checkbox);
        }
    }

    private void setCheckboxStatus(ViewHolder holder, T bean) {
        if (mSelection == Selection.SELECT_ALL) {
            holder.mCheckBox.setChecked(true);
            bean.setCheck(true);
        } else if (mSelection == Selection.CLEAR_ALL) {
            holder.mCheckBox.setChecked(false);
            bean.setCheck(false);
        } else {
            holder.mCheckBox.setChecked(bean.isCheck());
        }
    }

    @Override
    public Object[] getSections() {
        positionOfSection = new SparseIntArray();
        sectionOfPosition = new SparseIntArray();
        int count = getCount();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String letter = getItem(i).getFirstLetter();
            int section = list.size();
            if (list.size() != 0 && list.get(section - 1) != null && !list.get(section - 1).equals(letter)) {
                section++;
                positionOfSection.put(section, i);
            }
            list.add(letter);
            sectionOfPosition.put(i, section);
        }

        if (!list.contains("#")) {
            int size = list.size();
            list.add("#");
            positionOfSection.put(size, count);
            sectionOfPosition.put(count, size);
        }
        return list.toArray(new String[list.size()]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return positionOfSection.get(sectionIndex);
    }

    @Override
    public int getSectionForPosition(int position) {
        return sectionOfPosition.get(position);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        //reset the backData when the original data has been changed
        if (originalDataIsChange) {
            mCopyList.clear();
            mCopyList.addAll(mList);
        }
    }

    public List<T> getCheckList() {
        List<T> checkList = new ArrayList<>();
        for (T bean : mList) {
            if (bean.isCheck() && bean.isCanToggle()) {
                checkList.add(bean);
            }
        }
        return checkList;
    }

    public void filter(CharSequence s) {
        if (mFilter == null) {
            mFilter = new ContactListFilter(mList);
        }
        mFilter.filter(s);
    }

    protected class ContactListFilter extends Filter {
        List<T> originalData;

        public ContactListFilter(List<T> myList) {
            this.originalData = myList;
        }

        @Override
        protected synchronized FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (originalData == null) {
                originalData = new ArrayList<>();
            }
            getFilterResult(prefix, results);
            return results;
        }

        @Override
        protected synchronized void publishResults(CharSequence constraint, FilterResults results) {
            mList.clear();
            mList.addAll((Collection<T>) results.values);
            if (results.count > 0) {
                originalDataIsChange = false;
                notifyDataSetChanged();
                originalDataIsChange = true;
            } else {
                notifyDataSetInvalidated();
            }
        }

        private void getFilterResult(CharSequence prefix, FilterResults results) {
            if (prefix == null || prefix.length() == 0) {
                results.values = mCopyList;
                results.count = mCopyList.size();
            } else {
                String prefixString = prefix.toString();
                final int count = originalData.size();
                final ArrayList<T> newValues = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    final T bean = originalData.get(i);
                    String username = bean.getShowName();

                    if (username.toUpperCase().startsWith(prefixString.toUpperCase())) {
                        newValues.add(bean);
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }
        }
    }

    public enum Selection {
        SELECT_ALL,
        CLEAR_ALL,
        SELECT_NORMAL,
    }

    class ViewHolder {
        TextView mIndex;
        TextView mName;
        ImageView mImageView;
        ImageView mPromoter;
        CheckBox mCheckBox;
        RelativeLayout mCheckLayout;
        RelativeLayout mMemberLayout;
        TextView mMemberId;
        int checkBoxType;

        public ViewHolder(View v, int checkBoxType, boolean isGroupMember) {
            this.checkBoxType = checkBoxType;

            mIndex = (TextView) v.findViewById(R.id.tv_index);
            mImageView = (ImageView) v.findViewById(R.id.img_head);
            mPromoter = (ImageView) v.findViewById(R.id.img_promoter);
            mMemberId = (TextView) v.findViewById(R.id.tv_member_id);
            mMemberLayout = (RelativeLayout) v.findViewById(R.id.layout_group_member);
            if (isGroupMember) {
                mMemberLayout.setVisibility(View.VISIBLE);
                mName = (TextView) v.findViewById(R.id.tv_member_name);
            } else {
                mName = (TextView) v.findViewById(R.id.tv_name);
            }
            mName.setVisibility(View.VISIBLE);

            if (checkBoxType == LEFT_CHECK_BOX) {
                mCheckBox = (CheckBox) v.findViewById(R.id.checkbox_left);
                mCheckLayout = (RelativeLayout) v.findViewById(R.id.layout_check_left);
                mCheckLayout.setVisibility(View.VISIBLE);
            } else if (checkBoxType == RIGHT_CHECK_BOX) {
                mCheckBox = (CheckBox) v.findViewById(R.id.checkbox_right);
                mCheckLayout = (RelativeLayout) v.findViewById(R.id.layout_check_right);
                mCheckLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    abstract void bindDataToView(ViewHolder holder, T entity, int position);
}
