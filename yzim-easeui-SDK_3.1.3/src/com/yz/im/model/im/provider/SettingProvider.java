package com.yz.im.model.im.provider;

import android.content.Context;

import com.hyphenate.chat.EMMessage;
import com.yz.im.model.cache.SystemSettingCache;

/**
 * Created by cys on 2016/7/2 0002.
 */
public class SettingProvider implements SettingProviderInterface {

    private Context mContext;
    private SystemSettingCache mSettingCache;

    public SettingProvider(Context context) {
        mContext = context;
    }

    private void checkCacheObject() {
        if (mSettingCache == null) {
            mSettingCache = SystemSettingCache.getInstance(mContext.getApplicationContext());
        }
    }

    @Override
    public boolean isSpeakerOpened() {
        return true;
    }

    @Override
    public boolean isMsgVibrateAllowed(EMMessage message) {
        checkCacheObject();
        return mSettingCache.allowVoiceNotice();
    }

    @Override
    public boolean isMsgSoundAllowed(EMMessage message) {
        checkCacheObject();
        return mSettingCache.allowVoiceNotice();
    }

    @Override
    public boolean isMsgNotifyAllowed(EMMessage message) {
        if (message == null) {
            return true;
        }
        checkCacheObject();
        return mSettingCache.allowVoiceNotice();
    }
}
