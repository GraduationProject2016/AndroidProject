package com.findmydevice;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddDeviceActivity extends Activity {

	EditText devise_name, devise_password;
	Button addDevise;
	private String android_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adddevice);

		devise_name = (EditText) findViewById(R.id.devise_name);
		devise_password = (EditText) findViewById(R.id.devise_password);
		addDevise = (Button) findViewById(R.id.add_devise);

		addDevise.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				android_id = Secure.getString(getApplicationContext()
						.getContentResolver(), Secure.ANDROID_ID);
				Toast.makeText(getApplicationContext(), android_id,
						Toast.LENGTH_LONG).show();
				addedSuccessfully();
				navigatetoHomeActivity();
			}
		});

	}

	/**
	 * Method which navigates from Login Activity to Home Activity
	 */
	public void navigatetoHomeActivity() {
		Intent homeIntent = new Intent(getApplicationContext(),
				HomeActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void addedSuccessfully() {
		SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile",
				MODE_PRIVATE).edit();
		editor.putString("devise_added", "true");
		editor.commit();
	}

}
