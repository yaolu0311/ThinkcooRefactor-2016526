package com.thinkcoo.mobile.presentation.views.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.publicmodule.util.AppUtil;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.presentation.views.activitys.MainActivity;
import com.yz.im.Constant;
import com.yz.im.ui.activity.ChatActivity;

/**
 * author ：ml on 2016/8/17
 */
public class NotificationBroadCastReceiver extends BroadcastReceiver {

    public static final String ACTION = "action_notification_click";
    private static final String KEY_TYPE = "key_type";
    private static final String KEY_CHAT_ID = "key_chat_id";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION.equals(intent.getAction())) {
            //判断app进程是否存活
            if (AppUtil.isAppAlive(context, context.getPackageName())) {
                //如果存活的话，就直接启动DetailActivity，但要考虑一种情况，就是app的进程虽然仍然在
                //但Task栈已经空了，比如用户点击Back键退出应用，但进程还没有被系统回收，如果直接启动
                //DetailActivity,再按Back键就不会返回MainActivity了。所以在启动
                //DetailActivity前，要先启动MainActivity。
                ThinkcooLog.d("NotificationReceiver", "the app process is alive");
                Intent mainIntent = new Intent(context, MainActivity.class);
                //将MainAtivity的launchMode设置成SingleTask, 或者在下面flag中加上Intent.FLAG_CLEAR_TOP,
                //如果Task栈中有MainActivity的实例，就会把它移到栈顶，把在它之上的Activity都清理出栈，
                //如果Task栈不存在MainActivity实例，则在栈顶创建
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent detailIntent = new Intent(context, ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(KEY_CHAT_ID, intent.getStringExtra(KEY_CHAT_ID));
                bundle.putInt(KEY_TYPE, intent.getIntExtra(KEY_TYPE, Constant.CHATTYPE_SINGLE));
                bundle.putBoolean(ChatActivity.KEY_FROM_NOTIFICATION, true);
                detailIntent.putExtras(bundle);
                Intent[] intents = {mainIntent, detailIntent};

                context.startActivities(intents);
            } else {
                //如果app进程已经被杀死，先重新启动app，将DetailActivity的启动参数传入Intent中，参数经过
                //SplashActivity传入MainActivity，此时app的初始化已经完成，在MainActivity中就可以根据传入
                // 参数跳转到DetailActivity中去了
                Log.i("NotificationReceiver", "the app process is dead");
                Intent launchIntent = context.getPackageManager().
                        getLaunchIntentForPackage(context.getPackageName());
                launchIntent.setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                Bundle bundle = new Bundle();
                bundle.putString(KEY_CHAT_ID, intent.getStringExtra(KEY_CHAT_ID));
                bundle.putInt(KEY_TYPE, intent.getIntExtra(KEY_TYPE, Constant.CHATTYPE_SINGLE));
                bundle.putBoolean(ChatActivity.KEY_FROM_NOTIFICATION, true);
                launchIntent.putExtras(bundle);
                context.startActivity(launchIntent);
            }
        }
    }
}
