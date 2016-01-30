package fmd_android_clint.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.findmydevice.R;

import fmd_android_clint.common.BaseActivity;

public class HomeActivity extends BaseActivity {

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

	public void checkFirstRun() {
		boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
				.getBoolean("isFirstRun", true);
		if (isFirstRun) {
			getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
					.putBoolean("isFirstRun", false).apply();
		}
	}

}
