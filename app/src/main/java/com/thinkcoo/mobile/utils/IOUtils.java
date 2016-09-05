package com.thinkcoo.mobile.utils;


import com.example.administrator.publicmodule.util.log.ThinkcooLog;

import java.io.Closeable;

public class IOUtils {
	
	public static final String TAG = "IOUtils";
	
	public static void safeIoClose(Closeable closeable){
		if (null == closeable) {
			return;
		}
		try {
			closeable.close();
		} catch (Exception e) {
			ThinkcooLog.e(TAG, e.getMessage());
		}
	}

}
