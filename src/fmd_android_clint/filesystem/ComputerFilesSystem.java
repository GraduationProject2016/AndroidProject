package fmd_android_clint.filesystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.webkit.MimeTypeMap;

public class ComputerFilesSystem {

	public static String path;
	public static List<FMDFile> files;
	public static List<FMDDirectory> directories;
	public int numOfFiles;
	public int numOfFolders;

	public ComputerFilesSystem() {
		files = new ArrayList<FMDFile>();
		directories = new ArrayList<FMDDirectory>();
	}

	public ComputerFilesSystem(String path_) throws IOException {
		path = path_;
		files = new ArrayList<FMDFile>();
		directories = new ArrayList<FMDDirectory>();

		ls();
	}

	public static void ls() throws IOException {
		String fileName = null, fileType = null;
		long fileSize;

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (!listOfFiles[i].isHidden()
					&& !listOfFiles[i].getName().contains("UsbDrive")) {
				if (listOfFiles[i].isFile()) {
					fileName = listOfFiles[i].getName();
					fileSize = listOfFiles[i].length();

					String extension = MimeTypeMap
							.getFileExtensionFromUrl(listOfFiles[i].getPath());
					if (extension != null) {
						fileType = MimeTypeMap.getSingleton()
								.getMimeTypeFromExtension(extension);
					}
					files.add(new FMDFile(fileName, fileType, fileSize));
				} else if (listOfFiles[i].isDirectory()) {
					fileName = listOfFiles[i].getName();
					fileSize = listOfFiles[i].length();
					directories.add(new FMDDirectory(fileName, fileSize));
				}
			}
		}
	}

	public JSONObject toJson() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("path", path);
		object.put("numoffiles", files.size());
		object.put("numoffolders", directories.size());

		JSONArray filesArray = new JSONArray();
		for (int i = 0; i < files.size(); i++) {
			filesArray.put(i, files.get(i).toJson());
		}
		object.put("files", filesArray);

		JSONArray foldersArray = new JSONArray();
		for (int i = 0; i < directories.size(); i++) {
			foldersArray.put(i, directories.get(i).toJson());
		}
		object.put("folders", foldersArray);
		return object;
	}

}
