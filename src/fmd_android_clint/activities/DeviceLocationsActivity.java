package fmd_android_clint.activities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.findmydevice.R;

import fmd_android_clint.common.BaseActivity;
import fmd_android_clint.common.FromJasonToArrayList;
import fmd_android_clint.socket.entity.DeviceLocation;

public class DeviceLocationsActivity extends BaseActivity {

	List<DeviceLocation> locations = null;
	TextView no;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		String response = getIntent().getStringExtra("response");
		try {
			locations = FromJasonToArrayList.jsonToList(response,
					DeviceLocation.class);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_devicelocations);

		no = (TextView) findViewById(R.id.no);

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

		if (locations != null && locations.size() != 0) {

			LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout_tags);
			layout.setOrientation(LinearLayout.VERTICAL);

			for (int i = 0; i < locations.size(); i++) {

				DeviceLocation location = locations.get(i);

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

				SimpleDateFormat sdf = new SimpleDateFormat(
						"MM/dd/yyyy HH:mm:ss");
				String dateText = sdf.format(location.getTakeIn());

				btnTag.setText(dateText);
				btnTag.setId(location.getId());
				btnTag.setTag(location.getLatitude() + "::"
						+ location.getLongitude());
				layout.addView(btnTag);
			}
		} else {
			no.setVisibility(View.VISIBLE);
		}
	}
}