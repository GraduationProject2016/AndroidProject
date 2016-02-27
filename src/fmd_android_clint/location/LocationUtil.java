package fmd_android_clint.location;

import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import fmd_android_clint.common.BaseActivity;

public class LocationUtil extends BaseActivity {

	public String fieldLatitude;
	public String fieldLongitude;

	public LocationUtil() {
		turnGPSOn();

		// check if GPS enabled
		GPSTracker gpsTracker = new GPSTracker(this);
		if (gpsTracker.getIsGPSTrackingEnabled()) {
			fieldLatitude = String.valueOf(gpsTracker.latitude);
			fieldLongitude = String.valueOf(gpsTracker.longitude);
		}
	}

	public String getLocationLatitude() {
		return fieldLatitude;
	}

	public String getLocationLongtitude() {
		return fieldLongitude;
	}

	public void getDeviceLocation(String latitude, String longitude) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://" + getServerIP() + ":8080/fmd/webService/location/"
				+ getDeviceID() + "/" + latitude + "/" + longitude, null,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						// here
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
