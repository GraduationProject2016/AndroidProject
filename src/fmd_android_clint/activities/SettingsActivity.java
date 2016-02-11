package fmd_android_clint.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.findmydevice.R;

import fmd_android_clint.common.BaseActivity;

public class SettingsActivity extends BaseActivity {

	EditText hostname_input;
	TextView error_text;

	Button save;
	String hostNameText = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		hostname_input = (EditText) findViewById(R.id.hostname);
		save = (Button) findViewById(R.id.save);
		error_text = (TextView) findViewById(R.id.error_msg);
		hostname_input.setText("http://192.168.43.162:8080");

		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				hostNameText = hostname_input.getText().toString();

				if (hostNameText.equals("")) {
					error_text.setText("Please fill host Name Field.");
					return;
				}
				error_text.setText("");
				saveHostName(hostNameText);
				Toast.makeText(getApplicationContext(), "Saved Successfully!",
						Toast.LENGTH_LONG).show();
			}
		});

	}

	public void saveHostName(String hostName) {
		SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile",
				MODE_PRIVATE).edit();
		editor.putString("host_name", hostName);
		editor.commit();
	}

}
