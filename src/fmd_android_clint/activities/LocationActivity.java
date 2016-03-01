package fmd_android_clint.activities;

import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.findmydevice.R;

import fmd_android_clint.common.BaseActivity;
import fmd_android_clint.location.MyLocation;
import fmd_android_clint.location.MyLocation.LocationResult;

public class LocationActivity extends BaseActivity {

	TextView textview;
	public static Location loc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);

		//turnGPSOn();

		// to Find the Location
		LocationResult locationResult = new LocationResult() {
			@Override
			public String gotLocation(final Location location) {
				loc = location;
				textview = (TextView) findViewById(R.id.fieldLatitude);
				textview.setText(loc.getLatitude() + "");
				textview = (TextView) findViewById(R.id.fieldLongitude);
				textview.setText(loc.getLongitude() + "");
				return null;
			}
		};

		MyLocation myLocation = new MyLocation();
		myLocation.getLocation(LocationActivity.this, locationResult);

		// check if GPS enabled
//		GPSTracker gpsTracker = new GPSTracker(this);
//
//		if (gpsTracker.getIsGPSTrackingEnabled()) {
//			String stringLatitude = String.valueOf(gpsTracker.latitude);
//			textview = (TextView) findViewById(R.id.fieldLatitude);
//			textview.setText(stringLatitude);
//
//			String stringLongitude = String.valueOf(gpsTracker.longitude);
//			textview = (TextView) findViewById(R.id.fieldLongitude);
//			textview.setText(stringLongitude);
//
//			String country = gpsTracker.getCountryName(this);
//			textview = (TextView) findViewById(R.id.fieldCountry);
//			textview.setText(country);
//
//			String city = gpsTracker.getLocality(this);
//			textview = (TextView) findViewById(R.id.fieldCity);
//			textview.setText(city);
//
//			String postalCode = gpsTracker.getPostalCode(this);
//			textview = (TextView) findViewById(R.id.fieldPostalCode);
//			textview.setText(postalCode);
//
//			String addressLine = gpsTracker.getAddressLine(this);
//			textview = (TextView) findViewById(R.id.fieldAddressLine);
//			textview.setText(addressLine);
//		} else {
//			// can't get location
//			// GPS or Network is not enabled
//			// Ask user to enable GPS/network in settings
//			gpsTracker.showSettingsAlert();
//		}
	}

}