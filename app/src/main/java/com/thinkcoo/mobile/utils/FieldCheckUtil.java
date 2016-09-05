package com.thinkcoo.mobile.utils;

import android.content.Context;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.annotation.FieldEmptyCheck;
import com.thinkcoo.mobile.annotation.FieldFormatCheck;
import com.thinkcoo.mobile.annotation.FieldPosition;
import com.thinkcoo.mobile.presentation.mvp.views.BaseHintView;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/6/4.
 */
public class FieldCheckUtil {

    private static final String TAG = "FieldCheckUtil";

    @Inject
    public FieldCheckUtil() {
    }

    /**
     * 检查实体类所有字段是否符合要求
     *
     * @param t
     * @return return true if the entity is legal, other false
     */
    public <T> boolean checkEntityFieldsFormat(Context context, T t, BaseHintView baseHintView) {
        if (null == t) {
            return false;
        }
        Class objectClass = t.getClass();
        Field[] fields = softFieldsByAnnotation(objectClass.getDeclaredFields());
        for (Field field : fields) {
            if (!checkSingleFileFormat(context, t, baseHintView, field)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查单个字段内容及格式
     *
     * @param t
     * @return return true if the field is legal, other false
     */
    private <T> boolean checkSingleFileFormat(Context context, T t, BaseHintView baseHintView, Field field) {

        FieldEmptyCheck fieldEmptyCheck = field.getAnnotation(FieldEmptyCheck.class);
        if (null != fieldEmptyCheck) {
            if (checkFieldIsEmpty(context, t, baseHintView, field, fieldEmptyCheck)) {
                return false;
            }
        }

        FieldFormatCheck fieldFormatCheck = field.getAnnotation(FieldFormatCheck.class);
        if (null != fieldFormatCheck) {
            if (!checkFieldFormatIsLegal(context, t, baseHintView, field, fieldFormatCheck)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查字段是否为空
     *
     * @param t
     * @param field
     * @return return true if the field is empty, other false
     */
    private <T> boolean checkFieldIsEmpty(Context context, T t, BaseHintView baseHintView, Field field, FieldEmptyCheck annotation) {
        if (null != annotation) {
            field.setAccessible(true);
            try {
                String fieldValue = String.valueOf(field.get(t));
                if (stringIsNull(fieldValue)) {
                    baseHintView.showToast(context.getString(annotation.value()));
                    return true;
                }
            } catch (IllegalAccessException e) {
                ThinkcooLog.e(TAG, e.getMessage());
                return true;
            }
        }
        return false;
    }

    private <T> boolean checkFieldFormatIsLegal(Context context, T t, BaseHintView baseHintView, Field field, FieldFormatCheck fieldFormatCheck) {
        if (null != fieldFormatCheck) {
            field.setAccessible(true);
            try {
                String regex = fieldFormatCheck.regex();
                int resourceId = fieldFormatCheck.value();
                String fieldValue = String.valueOf(field.get(t));

                if (!doMatcherByRegex(regex, fieldValue)) {
                    baseHintView.showToast(context.getString(resourceId));
                    return false;
                }

            } catch (IllegalAccessException e) {
                ThinkcooLog.e(TAG, e.getMessage());
                return true;
            }
        }
        return true;
    }

    public boolean doMatcherByRegex(String regex, String fieldValue) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fieldValue);
        return matcher.matches();
    }

    private boolean stringIsNull(String string) {
        return null == string || string.trim().length() == 0 || string.equals("null");
    }

    private Field[] softFieldsByAnnotation(Field[] fields) {
        if (null == fields) {
            return new Field[0];
        }

        if (fields.length <= 1) {
            return fields;
        }

        List<Field> list = Arrays.asList(fields);
        Collections.sort(list, new FieldComparator());
        return (Field[]) list.toArray();
       }

    private static class FieldComparator implements Comparator<Field> {

        @Override
        public int compare(Field lhs, Field rhs) {
            FieldPosition lhsAnnotation = lhs.getAnnotation(FieldPosition.class);
            FieldPosition rhsAnnotation = rhs.getAnnotation(FieldPosition.class);

            if (null == lhsAnnotation && null == rhsAnnotation) {
                return 0;
            }

            //set field which has not the FieldPosition Annotation at the last
            if (null == lhsAnnotation && null != rhsAnnotation) {
                return 1;
            }

            if (null != lhsAnnotation && null == rhsAnnotation) {
                return -1;
            }

            if (null != lhsAnnotation && null != rhsAnnotation) {
                if (lhsAnnotation.value() > rhsAnnotation.value()) {
                    return 1;
                }
                if (lhsAnnotation.value() == rhsAnnotation.value()) {
                    return 0;
                }
                return -1;
            }

            return 1;
        }

        @Override
        public boolean equals(Object object) {
            return false;
        }
    }
}
