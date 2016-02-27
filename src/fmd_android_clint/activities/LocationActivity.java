package fmd_android_clint.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.findmydevice.R;

import fmd_android_clint.common.BaseActivity;
import fmd_android_clint.location.GPSTracker;

public class LocationActivity extends BaseActivity {

	TextView textview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);

		turnGPSOn();

		// check if GPS enabled
		GPSTracker gpsTracker = new GPSTracker(this);

		if (gpsTracker.getIsGPSTrackingEnabled()) {
			String stringLatitude = String.valueOf(gpsTracker.latitude);
			textview = (TextView) findViewById(R.id.fieldLatitude);
			textview.setText(stringLatitude);

			String stringLongitude = String.valueOf(gpsTracker.longitude);
			textview = (TextView) findViewById(R.id.fieldLongitude);
			textview.setText(stringLongitude);

			String country = gpsTracker.getCountryName(this);
			textview = (TextView) findViewById(R.id.fieldCountry);
			textview.setText(country);

			String city = gpsTracker.getLocality(this);
			textview = (TextView) findViewById(R.id.fieldCity);
			textview.setText(city);

			String postalCode = gpsTracker.getPostalCode(this);
			textview = (TextView) findViewById(R.id.fieldPostalCode);
			textview.setText(postalCode);

			String addressLine = gpsTracker.getAddressLine(this);
			textview = (TextView) findViewById(R.id.fieldAddressLine);
			textview.setText(addressLine);
		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gpsTracker.showSettingsAlert();
		}
	}

}