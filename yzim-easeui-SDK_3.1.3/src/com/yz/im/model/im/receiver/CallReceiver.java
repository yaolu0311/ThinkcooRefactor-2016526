/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yz.im.model.im.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.yz.im.IMHelper;
import com.yz.im.ui.activity.VideoCallActivity;
import com.yz.im.ui.activity.VoiceCallActivity;

public class CallReceiver extends BroadcastReceiver{

	private static String TAG = "CallReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		if(!IMHelper.getInstance().isLoggedIn())
		    return;
		String from = intent.getStringExtra("from");
		String type = intent.getStringExtra("type");
		if("video".equals(type)){
		    context.startActivity(new Intent(context, VideoCallActivity.class).
                    putExtra("username", from).putExtra("isComingCall", true).
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
		}else{
		    context.startActivity(new Intent(context, VoiceCallActivity.class).
		            putExtra("username", from).putExtra("isComingCall", true).
		            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
		}
		ThinkcooLog.d(TAG, "app received a incoming call");
	}
}
