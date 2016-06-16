package fmd_android_clint.socket;

import android.util.Log;
import fmd_android_clint.common.JsonHandler;
import fmd_android_clint.socket.dto.MessageDto;

public class Connection {

	public int userID;
	public int deviceID;
	public SocketClient client;
	public Thread clientThread;

	public Connection(int userID_, int deviceID_, String serverIP) {
		try {

			userID = userID_;
			deviceID = deviceID_;

			client = new SocketClient(userID, deviceID, serverIP);
			clientThread = new Thread(client);
			clientThread.start();
			signIn();
		} catch (Exception ex) {
			Log.d("my appppp", Log.getStackTraceString(ex));
		}
	}

	public boolean isConnected() {
		if (client != null && client.is_connected) {
			return true;
		}
		return false;
	}

	public boolean signIn() {
		MessageDto msg = null;
		try {
			msg = new MessageDto(MessageDto.CLIENT_TO_SERVER);
			msg.setDeviceId(deviceID);
			msg.setUserId(userID);
			msg.setContent("sign_in");
			client.send(JsonHandler.getMessageDtoJson(msg));
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	public void signOut() {
		try {
			MessageDto msg = new MessageDto(MessageDto.CLIENT_TO_SERVER);
			msg.setDeviceId(deviceID);
			msg.setUserId(userID);
			msg.setContent("sign_out");
			client.send(JsonHandler.getMessageDtoJson(msg));
			System.out.println(JsonHandler.getMessageDtoJson(msg));
		} catch (Exception ex) {
		}
	}
}
