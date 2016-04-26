package fmd_android_clint.activities;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
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

public class DevicesActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_devices);

		LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout_tags);
		layout.setOrientation(LinearLayout.VERTICAL);

		OnClickListener onClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(DevicesActivity.this,
						DeviceLocationsActivity.class);
				i.putExtra("device_id", v.getTag().toString());
				startActivity(i);

				// Toast.makeText(getApplicationContext(),
				// v.getTag().toString(),
				// Toast.LENGTH_LONG).show();
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
