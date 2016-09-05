package com.thinkcoo.mobile.presentation.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.utils.EmojiFilterUtils;

import java.util.Random;

/**
 * Created by Administrator on 2016/6/3.
 */
public class UserSkillDialog {

    private Context mContext;
    private GlobalToast mGlobalToast;
    private String[] colorBg2 = new String[]{"f895a4", "7dccb9",
            "91c8f6", "f2ac8c", "f1c973", "80c22e", "ada1e4", "6d99ea",
            "f47cad", "ffdee2", "ffdcc0", "d9c8ff", "c0e79c", "e9c8a5",
            "f58e3e", "faf3d0", "fccbbe", "f2a0d1", "dd6fab", "27a977",
            "c9ebdc", "ffa100", "f5e4f5", "4dca9a", "f3ece2", "edd7bc",
            "fbdde7", "feeaa3", "e3e2e0", "86d1cc", "20cbd4"};

    OnSkillInputCompleteListener mOnSkillInputCompleteListener;

    public UserSkillDialog(Context mContext, GlobalToast globalToast) {
        this.mContext = mContext;
        this.mGlobalToast = globalToast;
    }

    public void show() {
        builder().show();
    }

    private Dialog builder() {
        final Dialog dialog = new Dialog(mContext, R.style.AlertDialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_input, null);
        dialog.setContentView(view);
        TextView text_dialog_no = (TextView) view.findViewById(R.id.text_dialog_no);
        TextView text_dialog_yes = (TextView) view.findViewById(R.id.text_dialog_yes);
        final EditText edit_dialog_input_skill = (EditText) view.findViewById(R.id.edit_dialog_input_skill);
        TextView text_dialog_input_title = (TextView) view.findViewById(R.id.text_dialog_input_title);
        text_dialog_input_title.setText(R.string.user_personalskill_title);
        text_dialog_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        edit_dialog_input_skill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() > 20) {
                    mGlobalToast.showLongToast(R.string.user_skill_over_twenty);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        text_dialog_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edit_dialog_input_skill.getText().toString().trim();

                checkStringFormat(dialog, content, R.string.user_personalskill_cannout_be_empty);
            }

        });
        return dialog;
    }

    private void checkStringFormat(final Dialog dialog, String content, int resourceId) {
        if (TextUtils.isEmpty(content)) {
            mGlobalToast.showShortToast(resourceId);
            return;
        }
        if (EmojiFilterUtils.containsEmoji(content)) {
            // 判断长度是否超过20
            if (content.length() <= 20) {
                int color = new Random().nextInt(30);
                String colorUserSkill = EmojiFilterUtils.filterEmoji(content) + "#" + colorBg2[color];
                callBack(colorUserSkill);
                dialog.dismiss();
            } else {
                mGlobalToast.showLongToast(R.string.user_skill_over_twenty);

            }
        }
    }

    private void callBack(String colorUserSkill) {
        if (null != mOnSkillInputCompleteListener) {
            mOnSkillInputCompleteListener.onSkillInputComplete(colorUserSkill);
        }
    }

    public interface OnSkillInputCompleteListener {
        void onSkillInputComplete(String skill);
    }

    public void setOnSkillInputCompleteListener(OnSkillInputCompleteListener listener) {
        this.mOnSkillInputCompleteListener = listener;

    }
}
