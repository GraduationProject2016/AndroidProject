package fmd_android_clint.common;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import fmd_android_clint.socket.Connection;

public class BackgroundService extends Service {

	@Override
	public void onCreate() {
		Toast.makeText(this, "MyAlarmService.onCreate() ", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG)
				.show();
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "MyAlarmService start", Toast.LENGTH_LONG).show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				Connection con = new Connection(1, 2, "192.168.43.162");
				con.signIn();

			}
		}).start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG)
				.show();
		return super.onUnbind(intent);
	}

}