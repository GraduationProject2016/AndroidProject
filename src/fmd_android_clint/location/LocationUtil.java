package fmd_android_clint.location;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import fmd_android_clint.common.MyApplication;
import fmd_android_clint.location.MyLocation.LocationResult;

public class LocationUtil {

	public String fieldLatitude = "30.030474";
	public String fieldLongitude = "31.209637";
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

		Handler h = new Handler(Looper.getMainLooper());
		h.post(new Runnable() {
			public void run() {
				LocationResult locationResult = new LocationResult() {
					@Override
					public String gotLocation(final Location location) {
						if (location == null)
							return fieldLatitude + "::" + fieldLongitude;

						loc = location;
						fieldLatitude = String.valueOf(loc.getLatitude());
						fieldLongitude = String.valueOf(loc.getLongitude());

						Log.d("location test", fieldLatitude + " :: "
								+ fieldLongitude);

						return fieldLatitude + "::" + fieldLongitude;
					}
				};
				MyLocation myLocation = new MyLocation();
				myLocation.getLocation(MyApplication.getContext(),
						locationResult);
			}
		});

		return fieldLatitude + "::" + fieldLongitude;
	}

	public String getLocationLatitude() {
		return fieldLatitude;
	}

	public String getLocationLongtitude() {
		return fieldLongitude;
	}

	public void getDeviceLocation() {

		Log.d("ws location test", fieldLatitude + " :: " + fieldLongitude);

		if (fieldLatitude != null && fieldLongitude != null) {

			AsyncHttpClient client = new AsyncHttpClient();
			client.get("http://" + ServerIP + ":8080/fmd/webService/location/"
					+ deviceID + "/" + fieldLatitude + "/" + fieldLongitude,
					null, new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String response) {
							Toast.makeText(MyApplication.getContext(),
									"Requested resource not found",
									Toast.LENGTH_LONG).show();
						}

						@Override
						public void onFailure(int statusCode, Throwable error,
								String content) {
							if (statusCode == 404) {
								Toast.makeText(MyApplication.getContext(),
										"Requested resource not found",
										Toast.LENGTH_LONG).show();
							} else if (statusCode == 500) {
								Toast.makeText(MyApplication.getContext(),
										"Something went wrong at server",
										Toast.LENGTH_LONG).show();
							} else {
								Toast.makeText(
										MyApplication.getContext(),
										"[Device might not be connected to Internet or remote server is not up and running]",
										Toast.LENGTH_LONG).show();
							}
						}
					});

		}
	}

}
