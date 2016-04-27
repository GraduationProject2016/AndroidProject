package fmd_android_clint.activities;

import java.io.IOException;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.findmydevice.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import fmd_android_clint.common.BaseActivity;
import fmd_android_clint.common.FromJasonToArrayList;
import fmd_android_clint.socket.entity.Device;

public class DevicesActivity extends BaseActivity {

	List<Device> devices = null;
	TextView no;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		String response = getIntent().getStringExtra("response");
		try {
			devices = FromJasonToArrayList.jsonToList(response, Device.class);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_devices);

		no = (TextView) findViewById(R.id.no);

		OnClickListener onClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GetAllMyDeviceLocationsWebService(v.getTag().toString());
			}
		};

		if (devices != null && devices.size() != 0) {

			LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout_tags);
			layout.setOrientation(LinearLayout.VERTICAL);

			for (int i = 0; i < devices.size(); i++) {

				Device device = devices.get(i);

				Button btnTag = new Button(this);
				btnTag.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				btnTag.setBackground(getResources().getDrawable(
						R.drawable.button_theme));
				btnTag.setTextColor(Color.WHITE);
				btnTag.setPadding(0, 22, 0, 22);
				btnTag.setBottom(10);
				btnTag.setOnClickListener(onClickListener);
				btnTag.setTextSize(22);
				if (device.getId().equals(getDeviceID())) {
					btnTag.setText("Current Mobile");
					btnTag.setTextColor(Color.BLACK);
				} else {
					btnTag.setText((!device.getType() ? "ANDROID : " : "PC : ")
							+ device.getName());
				}
				btnTag.setId(device.getId());
				btnTag.setTag(device.getId());
				layout.addView(btnTag);
			}
		} else {
			no.setVisibility(View.VISIBLE);
		}

	}

	public void GetAllMyDeviceLocationsWebService(String device_id) {

		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://" + getServerIP()
				+ ":8080/fmd/webService/DeviceLocations/" + device_id, null,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						Intent i = new Intent(DevicesActivity.this,
								DeviceLocationsActivity.class);
						i.putExtra("response", response);
						startActivity(i);
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
