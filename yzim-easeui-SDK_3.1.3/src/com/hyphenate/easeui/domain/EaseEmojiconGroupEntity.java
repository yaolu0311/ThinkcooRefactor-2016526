package com.hyphenate.easeui.domain;

import java.util.List;

import com.hyphenate.easeui.domain.EaseEmojiIcon.Type;

/**
 * 一组表情所对应的实体类
 *
 */
public class EaseEmojiconGroupEntity {
    /**
     * 表情数据
     */
    private List<EaseEmojiIcon> emojiconList;
    /**
     * 图片
     */
    private int icon;
    /**
     * 组名
     */
    private String name;
    /**
     * 表情类型
     */
    private EaseEmojiIcon.Type type;
    
    public EaseEmojiconGroupEntity(){}
    
    public EaseEmojiconGroupEntity(int icon, List<EaseEmojiIcon> emojiconList){
        this.icon = icon;
        this.emojiconList = emojiconList;
        type = Type.NORMAL;
    }
    
    public EaseEmojiconGroupEntity(int icon, List<EaseEmojiIcon> emojiconList, EaseEmojiIcon.Type type){
        this.icon = icon;
        this.emojiconList = emojiconList;
        this.type = type;
    }
    
    public List<EaseEmojiIcon> getEmojiconList() {
        return emojiconList;
    }
    public void setEmojiconList(List<EaseEmojiIcon> emojiconList) {
        this.emojiconList = emojiconList;
    }
    public int getIcon() {
        return icon;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public EaseEmojiIcon.Type getType() {
        return type;
    }

    public void setType(EaseEmojiIcon.Type type) {
        this.type = type;
    }
    
    
}
