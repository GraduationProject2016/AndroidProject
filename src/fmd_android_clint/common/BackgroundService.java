package fmd_android_clint.common;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import fmd_android_clint.socket.Connection;

public class BackgroundService extends Service {

	@Override
	public void onCreate() {
		// Toast.makeText(this, "MyAlarmService.onCreate() ", Toast.LENGTH_LONG)
		// .show();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG)
				.show();
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);

		// Toast.makeText(this, "MyAlarmService start",
		// Toast.LENGTH_LONG).show();
		new Thread(new Runnable() {
			@Override
			public void run() {

				SharedPreferences prefs = getSharedPreferences("MyPrefsFile",
						MODE_PRIVATE);
				int device_id = prefs.getInt("device_id", 0);
				int user_id = prefs.getInt("user_id", 0);
				String ip = prefs.getString("server_ip", "");

				Connection con = new Connection(user_id, device_id, ip);

				while (true) {
					Log.d("hema", "try to connect ... ");
					try {
						if (!con.isConnected()) {
							saveConnectionStatus("Status : Not Connected");
							con = new Connection(user_id, device_id, ip);
						}
						saveConnectionStatus("Status : Connected");
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		return START_STICKY;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG)
				.show();
		return super.onUnbind(intent);
	}

	public void saveConnectionStatus(String status) {
		SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile",
				MODE_PRIVATE).edit();
		editor.putString("connection_status", status);
		editor.commit();
	}
}