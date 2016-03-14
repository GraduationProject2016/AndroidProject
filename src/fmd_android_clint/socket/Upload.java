package fmd_android_clint.socket;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import android.util.Log;

import fmd_android_clint.common.JsonHandler;
import fmd_android_clint.socket.dto.MessageDto;

public class Upload implements Runnable {

	public String addr;
	public int port;
	public Socket socket;
	public FileInputStream In;
	public OutputStream Out;
	public File file;
	MessageDto msg;

	public Upload(String addr, int port, File filepath, MessageDto m) {
		super();
		try {
			msg = m;
			file = filepath;
			socket = new Socket(InetAddress.getByName(addr), port);
			Out = socket.getOutputStream();
			In = new FileInputStream(filepath);
		} catch (Exception ex) {
			System.out.println("Exception [Upload : Upload(...)]");
		}
	}

	@Override
	public void run() {
		int p = 0;
		try {
			ObjectOutputStream streamOut = null;
			streamOut = new ObjectOutputStream(socket.getOutputStream());
			streamOut.flush();
			streamOut.writeObject(JsonHandler.getMessageDtoJson(msg));
			streamOut.flush();
			Thread.sleep(1000);
			byte[] buffer = new byte[1024];
			int count ;

			while ((count = In.read(buffer)) >= 0) {
				Out.write(buffer, 0, count);
				p++;

				Thread.sleep(200);
				// Log.d("f2sh", count+"");
			}
			Out.flush();
			
			if (streamOut != null)
				streamOut.close();

			if (In != null) {
				In.close();
			}
			if (Out != null) {
				Out.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (Exception ex) {
			System.out.println("Exception [Upload : run()]");
			ex.printStackTrace();
			Log.d("f2sh", ex.toString());
		}
		Log.d("ppppppppppp", p + "");
	}

}