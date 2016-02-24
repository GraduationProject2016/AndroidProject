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

}