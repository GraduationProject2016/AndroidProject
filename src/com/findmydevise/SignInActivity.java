package com.findmydevise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends Activity {

	EditText login_username, login_password;
	Button signInBtn, signUpBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);

		login_username = (EditText) findViewById(R.id.loginusername);
		login_password = (EditText) findViewById(R.id.loginpassword);
		signInBtn = (Button) findViewById(R.id.signinbtn);
		signUpBtn = (Button) findViewById(R.id.signupbtn);

		signUpBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(SignInActivity.this, SignUpActivity.class);
				startActivity(in);
			}
		});

		signInBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// write code here
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
