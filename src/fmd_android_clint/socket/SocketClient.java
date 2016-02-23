package fmd_android_clint.socket;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import fmd_android_clint.common.CommandConstant;
import fmd_android_clint.common.Constants;
import fmd_android_clint.common.JsonHandler;
import fmd_android_clint.filesystem.Operation;
import fmd_android_clint.socket.dto.Command;
import fmd_android_clint.socket.dto.MessageDto;

public class SocketClient implements Runnable {

	public int port;
	public String serverAddr;
	public Socket socket;

	public ObjectInputStream In;
	public ObjectOutputStream Out;

	public String username;
	public String password;

	public int userID;
	public int deviceID;
	public boolean is_connected;

	public SocketClient(int userID_, int deviceID_, String serverIP) {

		userID = userID_;
		deviceID = deviceID_;
		is_connected = false;

		this.serverAddr = serverIP;
		this.port = 13000;
		try {
			InetAddress serverAddress = InetAddress.getByName(serverAddr);

			socket = new Socket(serverAddress, port);

		} catch (UnknownHostException e) {
			// Log.d("hema", Log.getStackTraceString(e));
		} catch (IOException e) {
			// Log.d("hema", Log.getStackTraceString(e));
		}

		try {
			Out = new ObjectOutputStream(socket.getOutputStream());
			Out.flush();
			In = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// Log.d("myapp", Log.getStackTraceString(new Exception()));
		}

	}

	@Override
	public void run() {
		boolean keepRunning = true;
		Scanner in = new Scanner(System.in);

		while (keepRunning) {
			is_connected = true;
			try {
				MessageDto msg = JsonHandler.getMessageDtoObject((String) In
						.readObject());
				Command command = JsonHandler
						.getCommandObject(msg.getContent());

				MessageDto result = new MessageDto(Constants.CLIENT_TO_SERVER);
				result.setUserId(msg.getUserId());
				String stringCommand = command.getCommand();
				String[] parms = command.getParms();
				if (stringCommand.equals(CommandConstant.androidHomeJson)) {
					result.setContent(Operation.getDefaultStorageContentJson()
							.toString());
				} else if (stringCommand
						.equals(CommandConstant.androidPathJson)) {
					result.setContent(Operation.getAndroidPathJson(parms[0])
							.toString());
				} else if (stringCommand
						.equals(CommandConstant.createNewDirectory)) {
					result.setContent(Operation.createNewDirectory(parms[0],
							parms[1]) ? "true" : "false");
				} else if (stringCommand.equals(CommandConstant.createNewFile)) {
					result.setContent(Operation.createNewFile(parms[0],
							parms[1]) ? "true" : "false");
				} else if (stringCommand
						.equals(CommandConstant.getAndroidDeviceInfo)) {
					result.setContent(Operation.getAndroidDeviceInfo()
							.toString());
				} else if (stringCommand
						.equals(CommandConstant.removeDirectory)) {
					result.setContent(Operation.removeDirectory(parms[0]) ? "true"
							: "false");
				} else if (stringCommand
						.equals(CommandConstant.renameDirectory)) {
					result.setContent(Operation.renameDirectory(parms[0],
							parms[1]) ? "true" : "false");
				} else if (stringCommand.equals(CommandConstant.filetransfer)) {
					result.setContent("true");

					Command com = new Command(Constants.FIlE_TRANSFARE + "",
							new String[] { parms[0], parms[1] });
					MessageDto m = new MessageDto(MessageDto.CLIENT_TO_SERVER);

					m.setContent(JsonHandler.getCommandJson(com));
					m.setUserId(userID);
					m.setDeviceId(deviceID);

					Thread t = new Thread(new Upload(serverAddr, port,
							new File(parms[1] + "\\" + parms[0]), m));
					t.start();
				}
				send(JsonHandler.getMessageDtoJson(result));
			} catch (Exception ex) {
				keepRunning = false;
				is_connected = false;
				System.out.println("Exception SocketClient run()");
				ex.printStackTrace();
			}
		}
	}

	public void send(String msg) {
		try {
			Out.writeObject(msg);
			Out.flush();
		} catch (IOException ex) {
		}
	}

	public void closeThread(Thread t) {
		t = null;
	}
}
