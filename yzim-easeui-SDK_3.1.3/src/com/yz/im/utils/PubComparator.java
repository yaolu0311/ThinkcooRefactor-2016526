package com.yz.im.utils;

import android.content.Context;

import com.example.administrator.publicmodule.util.PinyinHelper;
import com.yz.im.model.db.entity.Friend;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by cys on 2016/7/9
 */
public class PubComparator {

    public void setSortLetter(Context context, List<Friend> entities) {
        char searchKey;
        if (entities != null && entities.size() > 0) {
            for (int i = 0; i < entities.size(); i++) {
                Friend entity = entities.get(i);
                String name = entity.getRemarkName();
                // 汉字转换成拼音
                String nicknamePinyin = PinyinHelper.getInstance()
                        .getPinyins(name.trim());
                if (nicknamePinyin != null && nicknamePinyin.length() > 0) {
                    char key = nicknamePinyin.charAt(0);
                    if (key >= 'A' && key <= 'Z') {

                    } else if (key >= 'a' && key <= 'z') {
                        key -= 32;
                    } else {
                        key = '#';
                    }
                    searchKey = key;
                } else {
                    searchKey = '#';
                }
                entity.setSortLetters(String.valueOf(searchKey));

                entities.remove(i);
                entities.add(i, entity);
            }
            // 根据a-z进行排序源数据
            Collections.sort(entities, new ChineseCharComp());
            Collections.sort(entities, new PinyinComparator());
        }
    }

    class ChineseCharComp implements Comparator {
        public int compare(Object o1, Object o2) {
            Collator myCollator = Collator.getInstance(java.util.Locale.CHINA);
            if (myCollator.compare(((Friend) o1).getRemarkName(), ((Friend) o2).getRemarkName()) < 0) {
                return -1;
            } else if (myCollator.compare(((Friend) o1).getRemarkName(), ((Friend) o2).getRemarkName()) > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    class PinyinComparator implements Comparator<Friend> {

        @Override
        public int compare(Friend o1, Friend o2) {
            if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
                return -1;
            } else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) {
                return 1;
            } else {
                return o1.getSortLetters().compareTo(o2.getSortLetters());
            }
        }
    }

}
