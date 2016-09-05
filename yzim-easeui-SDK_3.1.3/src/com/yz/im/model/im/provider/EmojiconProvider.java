package com.yz.im.model.im.provider;

import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseEmojiIcon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.yz.im.utils.EmojiconExampleGroupData;

import java.util.Map;

/**
 * Created by cys on 2016/7/2 0002.
 */
public class EmojiconProvider implements EaseUI.EaseEmojiconInfoProvider {

    @Override
    public EaseEmojiIcon getEmojiconInfo(String emojiconIdentityCode) {
        EaseEmojiconGroupEntity data = EmojiconExampleGroupData.getData();
        for(EaseEmojiIcon emojicon : data.getEmojiconList()){
            if(emojicon.getIdentityCode().equals(emojiconIdentityCode)){
                return emojicon;
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> getTextEmojiconMapping() {
        return null;
    }
}
