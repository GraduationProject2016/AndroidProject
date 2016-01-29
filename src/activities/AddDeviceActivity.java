package activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.findmydevice.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import common.BaseActivity;

public class AddDeviceActivity extends BaseActivity {

	EditText devise_name, devise_password, devise_repassword;
	TextView device_error;
	Button addDevise;
	private String devise_name_text, devise_password_text,
			devise_repassword_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adddevice);

		devise_name = (EditText) findViewById(R.id.devise_name);
		devise_password = (EditText) findViewById(R.id.devise_password);
		devise_repassword = (EditText) findViewById(R.id.devise_repassword);
		addDevise = (Button) findViewById(R.id.add_devise);
		device_error = (TextView) findViewById(R.id.device_error);

		addDevise.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				devise_name_text = devise_name.getText().toString();
				devise_password_text = devise_password.getText().toString();
				devise_repassword_text = devise_repassword.getText().toString();

				if (!devise_password_text.equals(devise_repassword_text)) {
					device_error
							.setText("Please check that the two passwords matches each others.");
					return;
				}
				if (devise_name_text.equals("")
						|| devise_password_text.equals("")) {
					device_error.setText("Please fill the above data.");
					return;
				}
				device_error.setText("");

				registerDeviceWebService();
			}
		});

	}

	public void registerDeviceWebService() {
		String android_id = getMacAddress();
		String userID = String.valueOf(getLoggedInUserID());

		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://192.168.43.162:8080/fmd/webService/device/register/"
				+ devise_name_text + "/" + devise_password_text + "/"
				+ android_id + "/ANDROID/" + userID, null,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						try {
							JSONObject obj = new JSONObject(response);

							if (obj.getString("status").equals("Success")) {
								addedSuccessfully();
								navigatetoHomeActivity();
							} else if (obj.getString("status").contains(
									"MacAddressNotNniqe")) {
								device_error
										.setText("You cannot add this device as it already used by anothr person.");
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

	public void addedSuccessfully() {
		SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile",
				MODE_PRIVATE).edit();
		editor.putString("devise_added", "true");
		editor.commit();
	}

}
