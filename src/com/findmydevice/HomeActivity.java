package com.findmydevice;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends Activity {

	Button addDevise, logoutBtn;
	TextView deviseStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		addDevise = (Button) findViewById(R.id.add_devise);
		logoutBtn = (Button) findViewById(R.id.logout);
		deviseStatus = (TextView) findViewById(R.id.devise_status);

		if (isAddedDevise()) {
			deviseStatus
					.setText("This Device added suucessfully, you can control it remotely now :)");
			addDevise.setVisibility(View.GONE);
		}

		addDevise.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(HomeActivity.this,
						AddDeviceActivity.class);
				startActivity(in);
			}
		});

		logoutBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences.Editor editor = getSharedPreferences(
						"MyPrefsFile", MODE_PRIVATE).edit();
				editor.putString("logged_in_user", "false");
				editor.commit();
				Intent in = new Intent(HomeActivity.this, SignInActivity.class);
				startActivity(in);
			}
		});
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

	public void checkFirstRun() {
		boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
				.getBoolean("isFirstRun", true);
		if (isFirstRun) {
			getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
					.putBoolean("isFirstRun", false).apply();
		}
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

}
