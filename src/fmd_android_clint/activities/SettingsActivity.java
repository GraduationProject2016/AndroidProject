package fmd_android_clint.activities;

import java.io.IOException;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.findmydevice.R;

import fmd_android_clint.common.BaseActivity;
import fmd_android_clint.socket.Connection;

public class SettingsActivity extends BaseActivity {

	EditText hostname_input;
	TextView error_text, connection_status;

	Button save, connectToServer;
	String hostNameText = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		hostname_input = (EditText) findViewById(R.id.hostname);
		save = (Button) findViewById(R.id.save);
		connectToServer = (Button) findViewById(R.id.server);
		error_text = (TextView) findViewById(R.id.error_msg);
		connection_status = (TextView) findViewById(R.id.connection_status);

		hostname_input.setText(getServerIP());
		connection_status.setText(getConnectionStatus());

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				hostNameText = hostname_input.getText().toString();

				if (hostNameText.equals("")) {
					error_text.setVisibility(View.VISIBLE);
					error_text.setText("Please fill host Name Field.");
					return;
				}
				error_text.setText("");
				error_text.setVisibility(View.GONE);
				saveHostName(hostNameText);
				Toast.makeText(getApplicationContext(), "Saved Successfully!",
						Toast.LENGTH_LONG).show();
			}
		});

		connectToServer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					connect();
				} catch (IOException e) {
					Toast.makeText(getApplicationContext(),
							"Please Check Connection!", Toast.LENGTH_LONG)
							.show();
				} catch (InterruptedException e) {
					Toast.makeText(getApplicationContext(),
							"Please Check Connection!", Toast.LENGTH_LONG)
							.show();
				}

			}
		});
	}

	public void connect() throws IOException, InterruptedException {

		Connection con = new Connection(getLoggedInUserID(), getDeviceID(),
				getServerIP());

		if (!con.isConnected()) {
			connection_status.setText("Status : Not Connected");
			saveConnectionStatus("Status : Not Connected");
		} else {
			connection_status.setText("Status : Connected");
			saveConnectionStatus("Status : Connected");
		}

	}
}
