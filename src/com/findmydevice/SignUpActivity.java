package com.findmydevice;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends Activity {

	EditText signup_name_text, signup_email_text, signup_mobilenumber_text,
			signup_username_text, signup_password_text,
			signup_password_confirmation_text;
	TextView signup_error_text;
	Button signUpBtn;

	String signup_name, signup_email, signup_mobilenumber, signup_username,
			signup_password, signup_password_confirmation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		signup_name_text = (EditText) findViewById(R.id.signup_name);
		signup_username_text = (EditText) findViewById(R.id.signup_username);
		signup_email_text = (EditText) findViewById(R.id.signup_email);
		signup_mobilenumber_text = (EditText) findViewById(R.id.signup_mobilenumber);
		signup_password_text = (EditText) findViewById(R.id.signup_password);
		signup_password_confirmation_text = (EditText) findViewById(R.id.signup_password_confirmation);

		signup_error_text = (TextView) findViewById(R.id.signup_error);
		signUpBtn = (Button) findViewById(R.id.signupbtn);

		signUpBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				signup_name = signup_name_text.getText().toString();
				signup_username = signup_username_text.getText().toString();
				signup_email = signup_email_text.getText().toString();
				signup_mobilenumber = signup_mobilenumber_text.getText()
						.toString();
				signup_password = signup_password_text.getText().toString();
				signup_password_confirmation = signup_password_confirmation_text
						.getText().toString();

				if (!signup_password.equals(signup_password_confirmation)) {
					signup_error_text
							.setText("Please check that the two passwords matches each others.");
					return;
				}

				if (signup_name.equals("") || signup_username.equals("")
						|| signup_email.equals("")
						|| signup_mobilenumber.equals("")
						|| signup_password.equals("")
						|| signup_password_confirmation.equals("")) {
					signup_error_text.setText("Please fill sign up data.");
					return;
				}

				signupWithWebService();
			}
		});

	}

	/**
	 * Method that performs RESTful webservice invocations
	 * 
	 * @param params
	 */
	public void signupWithWebService() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://192.168.43.162:8080/fmd/webService/user/signup/"
				+ signup_name + "/" + signup_username + "/" + signup_email
				+ "/" + signup_mobilenumber + "/" + signup_password, null,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						try {
							JSONObject obj = new JSONObject(response);
							if (obj.getString("status").equals("Success")) {
								Toast.makeText(getApplicationContext(),
										"You are successfully Signed up!",
										Toast.LENGTH_LONG).show();
								navigatetoHomeActivity();
							} else if (obj.getString("status").contains(
									"EmailNotUniqe")) {
								signup_error_text
										.setText("Please change email, it is used by another person.");
							} else if (obj.getString("status").contains(
									"UsernameNotUniqe")) {
								signup_error_text
										.setText("Please change username, it is used by another person.");
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

	public void navigatetoHomeActivity() {
		Intent homeIntent = new Intent(getApplicationContext(),
				HomeActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
