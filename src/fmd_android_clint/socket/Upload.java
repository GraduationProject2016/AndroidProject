package fmd_android_clint.socket;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import fmd_android_clint.common.JsonHandler;
import fmd_android_clint.socket.dto.Acknowledgement;
import fmd_android_clint.socket.dto.FilePart;
import fmd_android_clint.socket.dto.MessageDto;

public class Upload implements Runnable {

	public String addr;
	public int port;
	public Socket socket;
	public FileInputStream In;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public File file;
	MessageDto msg;
	boolean delete;

	public Upload(String addr, int port, File filepath, MessageDto m) {
		super();
		delete = false;
		init(addr, port, filepath, m);
	}

	public Upload(String addr, int port, File filepath, MessageDto m,
			boolean delete) {
		super();
		this.delete = delete;
		init(addr, port, filepath, m);
	}

	void init(String addr, int port, File filepath, MessageDto m) {
		try {
			msg = m;
			file = filepath;
			socket = new Socket(InetAddress.getByName(addr), port);
			In = new FileInputStream(filepath);

		} catch (Exception ex) {
		}
	}

	@Override
	public void run() {
		try {
			int start = 1;
			if (start == 1) {
				oos = new ObjectOutputStream(socket.getOutputStream());
				oos.flush();
				ois = new ObjectInputStream(socket.getInputStream());
				oos.writeObject(JsonHandler.getMessageDtoJson(msg));
			}

			if (start == 1)
				operation();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void operation() {
		int nFail = 0;
		int p = 0;
		try {

			byte[] buffer = new byte[1024];
			int count;
			while ((count = In.read(buffer)) >= 0) {
				FilePart filePart = new FilePart(buffer, p, count);
				oos.writeObject(filePart.toJsonString());
				oos.flush();
				do {
					Acknowledgement a = Acknowledgement
							.toAcknowledgement((String) ois.readObject());

					if (a.isNagativeAcknowlegment()) {
						nFail++;
						oos.writeObject(filePart.toJsonString());
						oos.flush();
					} else {
						p++;
						break;
					}
				} while (true);
			}
			oos.flush();

			cleanUp();

			if (delete)
				file.delete();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void cleanUp() throws IOException {
		if (oos != null)
			oos.close();

		if (ois != null)
			ois.close();

		if (In != null) {
			In.close();
		}

		if (socket != null) {
			socket.close();
		}
	}

}