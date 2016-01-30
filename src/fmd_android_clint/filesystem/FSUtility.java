package fmd_android_clint.filesystem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

public class FSUtility {

	public static List<FMDPartion> getAndroidPartions() {
		List<FMDPartion> partions = new ArrayList<FMDPartion>();

		String name;
		String path;
		long totalSpace;
		long usableSpace;

		File folder = new File("/storage");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (!listOfFiles[i].isHidden()
					&& !listOfFiles[i].getName().contains("UsbDrive")) {
				name = listOfFiles[i].getName();
				path = listOfFiles[i].getAbsolutePath();
				totalSpace = listOfFiles[i].getTotalSpace();
				usableSpace = listOfFiles[i].getUsableSpace();

				partions.add(new FMDPartion(name, path, totalSpace, usableSpace));
			}
		}
		return partions;
	}

	public static String getStoragePath() {
		File extStore = Environment.getExternalStorageDirectory();
		return extStore.getAbsolutePath();
	}

	// file size
	// double bytes = file.length();
	// double kilobytes = (bytes / 1024);
	// double megabytes = (kilobytes / 1024);
	// double gigabytes = (megabytes / 1024);
	// double terabytes = (gigabytes / 1024);
	// double petabytes = (terabytes / 1024);
	// double exabytes = (petabytes / 1024);
	// double zettabytes = (exabytes / 1024);
	// double yottabytes = (zettabytes / 1024);
}