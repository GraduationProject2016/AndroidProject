package com.findmydevice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends Activity {

	EditText signup_name, signup_email, signup_mobilenumber, signup_username,
			signup_password, signup_password_confirmation;
	Button signUpBtn, signInBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		signup_name = (EditText) findViewById(R.id.signup_name);
		signup_username = (EditText) findViewById(R.id.signup_username);
		signup_email = (EditText) findViewById(R.id.signup_email);
		signup_mobilenumber = (EditText) findViewById(R.id.signup_mobilenumber);
		signup_password = (EditText) findViewById(R.id.signup_password);
		signup_password_confirmation = (EditText) findViewById(R.id.signup_password_confirmation);

		signUpBtn = (Button) findViewById(R.id.signupbtn);
		signInBtn = (Button) findViewById(R.id.signinbtn);

		signUpBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// write code here
			}
		});

		signInBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(SignUpActivity.this, SignInActivity.class);
				startActivity(in);
			}
		});
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
