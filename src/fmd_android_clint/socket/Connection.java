package fmd_android_clint.socket;

public class Connection {

	public int userID;
	public int deviceID;
	// public SocketClient client;
	public Thread clientThread;

	public Connection() {
		// try {
		// userID = BaseActivity.getLoggedInUserID();
		// deviceID = CommonUtil.getDeviceID();
		//
		// client = new SocketClient();
		// clientThread = new Thread(client);
		// clientThread.start();
		// } catch (Exception ex) {
		// }
	}

	public void signIn() {
		// try {
		// MessageDto msg = new MessageDto(MessageDto.CLIENT_TO_SERVER);
		// msg.setDeviceId(deviceID);
		// msg.setUserId(userID);
		// msg.setContent("sign_in");
		// client.send(JsonHandler.getMessageDtoJson(msg));
		// System.out.println(JsonHandler.getMessageDtoJson(msg));
		// } catch (Exception ex) {
		// }
	}

	public void signOut() {
		// try {
		// MessageDto msg = new MessageDto(MessageDto.CLIENT_TO_SERVER);
		// msg.setDeviceId(deviceID);
		// msg.setUserId(userID);
		// msg.setContent("sign_out");
		// client.send(JsonHandler.getMessageDtoJson(msg));
		// System.out.println(JsonHandler.getMessageDtoJson(msg));
		// } catch (Exception ex) {
		// }
	}
}
