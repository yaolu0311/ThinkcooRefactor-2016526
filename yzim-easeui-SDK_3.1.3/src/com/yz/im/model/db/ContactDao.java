package com.yz.im.model.db;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.yz.im.model.entity.ContactBean;
import com.yzkj.android.orm.util.IOUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by cys on 2016/8/2
 */
public class ContactDao {

    public static  final String[] projection = {

            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.DATA1,
            "sort_key",
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY

    };

    public static final Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    public Observable<List<ContactBean>> queryContactList(Context context){
        return Observable.just(innerQueryContactList(context));
    }

    private List<ContactBean> innerQueryContactList(Context context){

        List<ContactBean> contactBeanList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri,projection,null,null,null);
            for (cursor.moveToFirst() ; !cursor.isAfterLast() ; cursor.moveToNext()) {
                ContactBean contactBean = cursor2Contact(cursor);
                contactBeanList.add(contactBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IOUtil.closeQuietly(cursor);
        }
        return contactBeanList;
    }
    private ContactBean cursor2Contact(Cursor cursor) {

        int offset = 0;


        ContactBean contactBean = new ContactBean();
        int id = cursor.getInt(offset++);
        String name = cursor.getString(offset++);
        String number = cursor.getString(offset++);
        String sortKey = cursor.getString(offset++);
        int contactId = cursor.getInt(offset++);
        Long photoId = cursor.getLong(offset++);
        String lookUpKey = cursor.getString(offset++);

        contactBean.setDesplayName(name);
        String phoneNum=number.replace(" ","");
        contactBean.setPhoneNum(phoneNum);
        contactBean.setSortKey(sortKey);
        contactBean.setPhotoId(photoId);
        contactBean.setLookUpKey(lookUpKey);

        return contactBean;
    }
}
