package fmd_android_clint.location;

import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import fmd_android_clint.common.MyApplication;
import fmd_android_clint.location.MyLocation.LocationResult;

public class LocationUtil {

	public String fieldLatitude;
	public String fieldLongitude;
	public int deviceID;
	public String ServerIP;
	public static Location loc;

	public LocationUtil() {

		SharedPreferences prefs = MyApplication.getContext()
				.getSharedPreferences("MyPrefsFile", 0);
		deviceID = prefs.getInt("device_id", 0);
		ServerIP = prefs.getString("server_ip", "");
	}

	public String getLocation() {
		LocationResult locationResult = new LocationResult() {
			@Override
			public String gotLocation(final Location location) {
				loc = location;
				fieldLatitude = String.valueOf(loc.getLatitude());
				fieldLongitude = String.valueOf(loc.getLongitude());
				Log.d("test", fieldLatitude + " :: " + fieldLongitude);
				return fieldLatitude + "::" + fieldLongitude;
			}
		};
		MyLocation myLocation = new MyLocation();
		myLocation.getLocation(MyApplication.getContext(), locationResult);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return fieldLatitude + "::" + fieldLongitude;
	}

	public String getLocationLatitude() {
		return fieldLatitude;
	}

	public String getLocationLongtitude() {
		return fieldLongitude;
	}

	public void getDeviceLocation() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://" + ServerIP + ":8080/fmd/webService/location/"
				+ deviceID + "/" + fieldLatitude + "/" + fieldLongitude, null,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						// Toast.makeText(getApplicationContext(),
						// "Requested resource not found",
						// Toast.LENGTH_LONG).show();
					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						if (statusCode == 404) {
							// Toast.makeText(getApplicationContext(),
							// "Requested resource not found",
							// Toast.LENGTH_LONG).show();
						} else if (statusCode == 500) {
							// Toast.makeText(getApplicationContext(),
							// "Something went wrong at server",
							// Toast.LENGTH_LONG).show();
						}
						// When Http response code other than 404, 500
						else {
							// Toast.makeText(
							// getApplicationContext(),
							// "[Device might not be connected to Internet or remote server is not up and running]",
							// Toast.LENGTH_LONG).show();
						}
					}
				});
	}

}
