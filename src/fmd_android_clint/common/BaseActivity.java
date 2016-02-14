package fmd_android_clint.common;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.view.Menu;
import android.view.MenuItem;

import com.findmydevice.R;

import fmd_android_clint.activities.HomeActivity;
import fmd_android_clint.activities.SettingsActivity;

public class BaseActivity extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent homeIntent = new Intent(getApplicationContext(),
					SettingsActivity.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeIntent);
		} else if (id == R.id.action_exit) {
			System.exit(1);
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean isAddedDevise() {
		SharedPreferences prefs = getSharedPreferences("MyPrefsFile",
				MODE_PRIVATE);

		String name = prefs.getString("device_added", "No name defined");
		if (name.equals("true")) {
			return true;
		}
		return false;
	}

	public boolean isLoggedInUser() {
		SharedPreferences prefs = getSharedPreferences("MyPrefsFile",
				MODE_PRIVATE);

		String name = prefs.getString("logged_in_user", "No name defined");
		if (name.equals("true")) {
			return true;
		}
		return false;
	}

	public void navigatetoHomeActivity() {
		Intent homeIntent = new Intent(getApplicationContext(),
				HomeActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);
	}

	public String getMacAddress() {
		return Secure.getString(getApplicationContext().getContentResolver(),
				Secure.ANDROID_ID);
	}

	public Integer getLoggedInUserID() {
		SharedPreferences prefs = getSharedPreferences("MyPrefsFile",
				MODE_PRIVATE);

		return prefs.getInt("user_id", 0);
	}

	public Integer getDeviceID() {
		SharedPreferences prefs = getSharedPreferences("MyPrefsFile",
				MODE_PRIVATE);

		return prefs.getInt("device_id", 0);
	}

	public String getHostName() {
		SharedPreferences prefs = getSharedPreferences("MyPrefsFile",
				MODE_PRIVATE);

		return prefs.getString("host_name", "null");
	}

	public void saveUserName(String name) {
		SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile",
				MODE_PRIVATE).edit();
		editor.putString("user_name", name);
		editor.commit();
	}

	public String getCurrentUserName() {
		SharedPreferences prefs = getSharedPreferences("MyPrefsFile",
				MODE_PRIVATE);

		return prefs.getString("user_name", "null");
	}

	public void removedSuccessfully() {
		SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile",
				MODE_PRIVATE).edit();
		editor.putString("device_added", "false");
		editor.putInt("device_id", 0);
		editor.commit();
	}
}
