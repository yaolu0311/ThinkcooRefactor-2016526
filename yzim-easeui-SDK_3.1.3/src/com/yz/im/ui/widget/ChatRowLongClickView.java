package com.yz.im.ui.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.publicmodule.ui.widget.BaseDialog;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.yz.im.ui.adapter.DialogItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cys on 2016/8/12
 */
public class ChatRowLongClickView extends LinearLayout {

    private static final String TAG = "ChatRowLongClickView";

    private BaseDialog mDialog;
    private OnItemClickListener mClickListener;

    private RecyclerView mRecyclerView;
    private DialogItemAdapter mAdapter;
    private List<String> mList;

    public ChatRowLongClickView(Context context) {
        this(context, null);
    }

    public ChatRowLongClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttribute();
        initRecycleView();

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(mRecyclerView, params);

        mDialog = new BaseDialog.Builder(context).setRootView(this).setCanceledOnTouchOutside(true)
                .setForbidBackPress(false).setThemeStyle(R.style.AlertDialogStyle).create();
    }

    private void initAttribute() {
        setOrientation(VERTICAL);
    }

    private void initRecycleView() {
        mRecyclerView = new RecyclerView(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        mList = new ArrayList<>();
        mAdapter = new DialogItemAdapter(getContext(), mList);
        mAdapter.setListener(new DialogItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(String content, int position) {
                if (mList == null) {
                    ThinkcooLog.e(TAG, "mClickListener is null");
                    return;
                }
                mClickListener.onClick(content, position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void refreshData(List<String> items) {
        if (items == null) {
            return;
        }
        mList.clear();
        mList.addAll(items);
        mAdapter.notifyDataSetChanged();
    }

    private void show() {
        if (mDialog != null) {
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }
    }

    private void dismiss() {
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    private void setClickListener(OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    /**
     * ---------------- Delegate ----------------
     */

    public static class ChatRowLongClickViewDelegate {

        private Context mContext;
        private ChatRowLongClickView mLongClickView;

        public ChatRowLongClickViewDelegate(Context context, List<String> list) {
            mContext = context;
            initView(list);
        }

        private void initView(List<String> list) {
            mLongClickView = new ChatRowLongClickView(mContext);
            mLongClickView.refreshData(list);
        }

        public void show() {
            mLongClickView.show();
        }

        public void dismiss() {
            mLongClickView.dismiss();
        }

        public void setClickListener(OnItemClickListener clickListener) {
            if (clickListener != null) {
                mLongClickView.setClickListener(clickListener);
            }
        }
    }


    /**
     * ---------------- listener ----------------
     */
    public interface OnItemClickListener {

        void onClick(String content, int position);
    }

}
