package common;

import activities.HomeActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.view.Menu;
import android.view.MenuItem;

import com.findmydevice.R;

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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean isAddedDevise() {
		SharedPreferences prefs = getSharedPreferences("MyPrefsFile",
				MODE_PRIVATE);

		String name = prefs.getString("devise_added", "No name defined");
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
}
