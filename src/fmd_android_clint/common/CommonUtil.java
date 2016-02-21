package fmd_android_clint.common;

import android.content.Context;
import android.content.SharedPreferences;

public class CommonUtil {

	public static SharedPreferences getSharedPreferences(Context ctxt) {
		return ctxt.getSharedPreferences("MyPrefsFile", 0);
	}

	public static Integer getDeviceID(Context c) {
		SharedPreferences prefs = getSharedPreferences(c);
		return prefs.getInt("device_id", 0);
	}

	public static Integer getLoggedInUserID(Context c) {
		SharedPreferences prefs = getSharedPreferences(c);
		return prefs.getInt("user_id", 0);
	}

}
