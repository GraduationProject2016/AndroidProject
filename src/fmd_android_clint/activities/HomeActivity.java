package fmd_android_clint.activities;

import java.io.IOException;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.findmydevice.R;

import fmd_android_clint.common.BaseActivity;

public class HomeActivity extends BaseActivity {

	Button addDevise, logoutBtn;
	TextView deviseStatus, welcome;
	private PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		addDevise = (Button) findViewById(R.id.add_devise);
		logoutBtn = (Button) findViewById(R.id.logout);
		deviseStatus = (TextView) findViewById(R.id.devise_status);
		welcome = (TextView) findViewById(R.id.welcome);

		welcome.setText("Welcome " + getCurrentUserName());

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		if (isAddedDevise()) {
			deviseStatus
					.setText("This Device added successfully, you can control it remotely now :)");
			addDevise.setVisibility(View.GONE);

			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						doWork();
					} catch (IOException e) {
						Toast.makeText(getApplicationContext(),
								"Please Check Connection!", Toast.LENGTH_LONG)
								.show();
					} catch (InterruptedException e) {
						Toast.makeText(getApplicationContext(),
								"Please Check Connection!", Toast.LENGTH_LONG)
								.show();
					}
				}
			}).start();

			// startService(new Intent(this, BackgroundService.class));
			// Intent myIntent = new Intent(HomeActivity.this,
			// BackgroundService.class);
			// pendingIntent = PendingIntent.getService(HomeActivity.this, 0,
			// myIntent, 0);
			//
			// AlarmManager alarmManager = (AlarmManager)
			// getSystemService(ALARM_SERVICE);
			//
			// Calendar calendar = Calendar.getInstance();
			// calendar.setTimeInMillis(System.currentTimeMillis());
			// calendar.add(Calendar.SECOND, 5);
			// alarmManager.set(AlarmManager.RTC_WAKEUP,
			// calendar.getTimeInMillis(), pendingIntent);
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
