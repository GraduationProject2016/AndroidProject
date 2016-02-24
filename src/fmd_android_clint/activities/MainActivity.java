package fmd_android_clint.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.findmydevice.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import fmd_android_clint.common.BaseActivity;

public class MainActivity extends BaseActivity {

	Button signUpBtn, signInBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (isLoggedInUser()) {
			// navigatetoHomeActivity();

			if (isAddedDevise())
				checkDeviceWebService();

			Intent i = new Intent(getApplicationContext(), HomeActivity.class);
			i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(i);
			this.finish();
		}

		setContentView(R.layout.activity_main);

		signUpBtn = (Button) findViewById(R.id.signupbtn);
		signInBtn = (Button) findViewById(R.id.signinbtn);

		signUpBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(MainActivity.this, SignUpActivity.class);
				startActivity(in);
			}
		});

		signInBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(MainActivity.this, SignInActivity.class);
				startActivity(in);
			}
		});
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
					if (obj.getString("status").equals("not founded"))
						unRegisterDevice();
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