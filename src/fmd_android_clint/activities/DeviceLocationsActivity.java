package fmd_android_clint.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.findmydevice.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import fmd_android_clint.common.BaseActivity;

public class DeviceLocationsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_devicelocations);

		String device_id = getIntent().getStringExtra("device_id");
		Toast.makeText(getApplicationContext(), device_id, Toast.LENGTH_LONG)
				.show();

		LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout_tags);
		layout.setOrientation(LinearLayout.VERTICAL);

		OnClickListener onClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String loc[] = v.getTag().toString().split("::");
				Uri uri = Uri.parse("geo:0,0?q=" + loc[0] + "," + loc[1]
						+ " (Maninagar)");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				intent.setClassName("com.google.android.apps.maps",
						"com.google.android.maps.MapsActivity");
				startActivity(intent);
			}
		};

		for (int i = 0; i < 3; i++) {

			Button btnTag = new Button(this);
			btnTag.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			btnTag.setBackground(getResources().getDrawable(
					R.drawable.button_theme));
			btnTag.setTextColor(Color.WHITE);
			btnTag.setPadding(0, 22, 0, 22);
			btnTag.setBottom(10);
			btnTag.setOnClickListener(onClickListener);
			btnTag.setTextSize(22);
			btnTag.setText("Button " + i); // name
			btnTag.setId(i);
			btnTag.setTag(i);
			layout.addView(btnTag);
		}
	}

	/**
	 * Method that performs RESTful webservice invocations
	 * 
	 * @param params
	 */
	public void GetAllMyDevicesWebService(boolean is_email) {
		String login_by = "";
		if (is_email)
			login_by = "email";
		else
			login_by = "username";

		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://" + getServerIP()
				+ ":8080/fmd/webService/user/login/" + login_by, null,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						try {
							JSONObject obj = new JSONObject(response);
							if (obj.getString("status").equals("Success")
									&& obj.getBoolean("active") == true) {
								loginSuccessfully(Integer.valueOf(obj
										.getInt("id")));
								saveUserName(obj.getString("name"));
								Toast.makeText(getApplicationContext(),
										"You are successfully logged in!",
										Toast.LENGTH_LONG).show();
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
									"Requested resource not found",
									Toast.LENGTH_LONG).show();
						} else if (statusCode == 500) {
							Toast.makeText(getApplicationContext(),
									"Something went wrong at server",
									Toast.LENGTH_LONG).show();
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