package fmd_android_clint.filesystem;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Operation {

	public Operation() {

	}

	public static boolean removeDirectory(String path) {
		File directory = new File(path);
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (null != files) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						removeDirectory(files[i].getAbsolutePath());
					} else {
						files[i].delete();
					}
				}
			}
		}
		return (directory.delete());
	}

	public static boolean renameDirectory(String path, String newName) {
		try {
			File file = new File(path);
			File file2 = new File(file.getParent() + "/" + newName);

			boolean success = file.renameTo(file2);

			if (!success)
				return false;

			return true;

		} catch (Exception e) {
			return false;
		}
	}

	public static boolean createNewDirectory(String path, String name) {
		File directory = new File(path + "/" + name);

		if (directory.exists())
			return false;

		return directory.mkdir();

	}

	public static boolean createNewFile(String path, String name)
			throws IOException {
		File directory = new File(path + "/" + name);

		if (directory.exists())
			return false;

		return directory.createNewFile();

	}

	public static JSONObject getAndroidDeviceInfo() throws JSONException {
		JSONObject ob = new JSONObject();
		ob.put("os_version", android.os.Build.VERSION.RELEASE);
		ob.put("os_api_level", android.os.Build.VERSION.SDK_INT);
		ob.put("device", android.os.Build.DEVICE);
		ob.put("model", android.os.Build.MODEL);
		ob.put("product", android.os.Build.PRODUCT);
		ob.put("brand", android.os.Build.BRAND);
		return ob;
	}

	public static JSONObject getAndroidPathJson(String path)
			throws JSONException, IOException {
		ComputerFilesSystem obj = new ComputerFilesSystem(path);
		return obj.toJson();
	}

	public static JSONObject getDefaultStorageContentJson()
			throws JSONException, IOException {

		JSONObject object = new JSONObject();
		List<FMDPartion> partions = FSUtility.getAndroidPartions();

		JSONArray ps = new JSONArray();
		for (int i = 0; i < partions.size(); i++) {
			ps.put(i, partions.get(i).toJson());
		}
		object.put("partions", ps);
		object.put("numOfPartions", partions.size());

		return object;
	}

}
