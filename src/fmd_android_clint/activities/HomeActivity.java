package fmd_android_clint.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.findmydevice.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import fmd_android_clint.common.BackgroundService;
import fmd_android_clint.common.BaseActivity;

public class HomeActivity extends BaseActivity {

	Button addDevise, logoutBtn;
	TextView deviseStatus, welcome;
	private PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if (isAddedDevise())
			checkDeviceWebService();

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

//			TelephonyManager telemamanger = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//		    String number = telemamanger.getSimSerialNumber(); 
//			Toast.makeText(getApplicationContext(), number, Toast.LENGTH_LONG);
//			deviseStatus.setText(number);
			
			startService(new Intent(this, BackgroundService.class));
		}

		

		addDevise.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomeActivity.this,
						AddDeviceActivity.class);
				i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(i);
				finish();
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

	/**
	 * Method that performs RESTful webservice invocations
	 * 
	 * @param params
	 */
	public void checkDeviceWebService() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://" + getServerIP()
				+ ":8080/fmd/webService/device/devicefounded/"
				+ getMacAddress(), null, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				try {
					JSONObject obj = new JSONObject(response);
					if (obj.getString("status").equals("not founded")) {
						unRegisterDevice();
						navigatetoHomeActivity();
					}
				} catch (JSONException e) {
					Toast.makeText(
							getApplicationContext(),
							"Error Occured [Server's JSON response might be invalid]!",
							Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				if (statusCode == 404) {
					Toast.makeText(getApplicationContext(),
							"Requested resource not found", Toast.LENGTH_LONG)
							.show();
				} else if (statusCode == 500) {
					Toast.makeText(getApplicationContext(),
							"Something went wrong at server", Toast.LENGTH_LONG)
							.show();
				}
				// When Http response code other than 404, 500
				else {
					Toast.makeText(
							getApplicationContext(),
							"[Device might not be connected to Internet or remote server is not up and running]",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

}
