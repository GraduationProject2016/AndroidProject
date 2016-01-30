package fmd_android_clint.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
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
import com.loopj.android.http.RequestParams;

import fmd_android_clint.common.BaseActivity;

public class SignInActivity extends BaseActivity {

	EditText login_input, login_password;
	TextView signin_error_text;

	Button signInBtn, signUpBtn;
	String userLoginInput = "", userLoginPassword = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);

		login_input = (EditText) findViewById(R.id.logininput);
		login_password = (EditText) findViewById(R.id.loginpassword);
		signInBtn = (Button) findViewById(R.id.signinbtn);
		signUpBtn = (Button) findViewById(R.id.signupbtn);
		signin_error_text = (TextView) findViewById(R.id.login_error);

		signUpBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(SignInActivity.this,
						SignUpActivity.class);
				startActivity(in);
			}
		});

		signInBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userLoginInput = login_input.getText().toString();
				userLoginPassword = login_password.getText().toString();

				if (userLoginInput.equals("") || userLoginPassword.equals("")) {
					signin_error_text.setText("Please fill login Data.");
					return;
				}
				signin_error_text.setText("");

				boolean is_email = isEmail(userLoginInput);

				RequestParams params = new RequestParams();
				if (is_email)
					params.put("email", userLoginInput);
				else
					params.put("username", userLoginInput);

				params.put("password", userLoginPassword);

				loginWithWebService(is_email);
			}
		});

	}

	private boolean isEmail(String input) {
		int atIndex = input.indexOf('@');

		if (atIndex == -1)
			return false;

		int lastDotIndex = input.lastIndexOf('.');

		return atIndex < lastDotIndex && lastDotIndex - atIndex != 1
				&& lastDotIndex != input.length() - 1 && atIndex != 0;
	}

	/**
	 * Method that performs RESTful webservice invocations
	 * 
	 * @param params
	 */
	public void loginWithWebService(boolean is_email) {
		String login_by = "";
		if (is_email)
			login_by = "email";
		else
			login_by = "username";

		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://192.168.43.162:8080/fmd/webService/user/login/"
				+ login_by + "/" + userLoginInput + "/" + userLoginPassword,
				null, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						try {
							JSONObject obj = new JSONObject(response);
							if (obj.getString("status").equals("Success")
									&& obj.getBoolean("active") == true) {
								loginSuccessfully(Integer.valueOf(obj
										.getInt("id")));
								Toast.makeText(getApplicationContext(),
										"You are successfully logged in!",
										Toast.LENGTH_LONG).show();
								navigatetoHomeActivity();
							} else if (obj.getString("status")
									.equals("Success")
									&& obj.getBoolean("active") == false) {
								signin_error_text
										.setText("Please Activate your account, check your mail.");
							} else if (obj.getString("status").equals("fail")) {
								signin_error_text
										.setText("login fail , error in username or password.");
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

	public void loginSuccessfully(Integer user_id) {
		SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile",
				MODE_PRIVATE).edit();
		editor.putString("logged_in_user", "true");
		editor.putInt("user_id", user_id);
		editor.commit();
	}

}
