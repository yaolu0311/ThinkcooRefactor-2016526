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
package com.yz.im;

import com.hyphenate.easeui.EaseConstant;

public class Constant extends EaseConstant {
	public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
	public static final String GROUP_USERNAME = "item_groups";
	public static final String CHAT_ROOM = "item_chat_room";
	public static final String CHAT_ROBOT = "item_robots";
	public static final String ACTION_GROUP_CHANGE = "action_group_change";
	public static final String ACTION_CONTACT_CHANGE = "action_contact_change";

	public static final String ACTION_GROUP_INVITE_RECEIVE = "action_group_invite_receive";

	public static final String ACTION_NEW_NOTICE_RECEIVE = "action_new_notice_receive";

	//intentService Action
	public static final String ACTION_USER_REMOVED = "action_user_removed";
	public static final String ACTION_LOGIN_ANOTHER_DEVICE = "action_login_another_device";
	public static final String ACTION_REFRESH_UNREAD_MESSAGE_COUNT = "action_refresh_unread_message_count";
	public static final String ACTION_FINISH_ALL_ACTIVITY = "action_finish_all_activity";

	public static final String KEY_UNREAD_MESSAGE = "key_unread_message";

	public static final String IS_FRIEND = "1";
	public static final String IS_BLACK_USER = "1";
	public static final String STATUS_CHECKED = "1";
	public static final String STATUS_UNCHECKED = "0";
	public static final String IS_SHIELD = "1";  //屏蔽此人

	public static String TYPE_INITIATIVE_GROUP = "1";  //主动圈
	public static String TYPE_EVENT_GROUP = "3";  //事件圈
	public static String GROUP_IS_PUBLIC = "1";  //公开群
	public static String GROUP_IS_PRIVATE = "0";  //不公开群
	public static String GROUP_APPROVAL = "1";  //加入群时需要批准
	public static String GROUP_NOT_APPROVAL = "0";  //加入群时不需要批准

	public static final String ACTION_LOGIN = "yzke.action.login.activity";
	public static String OPEN_SETTING = "1";
	public static String CLOSE_SETTING = "0";

	public static int REQUESTCODE_CLICK_NOTICE_MESSAGE = 101;
	public static String ACTION_NOTIFICATION_CLICK = "action_notification_click";
}
