package fmd_android_clint.filesystem;

import org.json.JSONException;
import org.json.JSONObject;

public class FMDPartion {

	public String name;
	public String path;
	public long totalSpace;
	public long usableSpace;

	public FMDPartion(String name, String path, long totalSpace, long usableSpace) {
		super();
		this.name = name;
		this.path = path;
		this.totalSpace = totalSpace;
		this.usableSpace = usableSpace;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTotalSpace() {
		return totalSpace;
	}

	public void setTotalSpace(long totalSpace) {
		this.totalSpace = totalSpace;
	}

	public long getUsableSpace() {
		return usableSpace;
	}

	public void setUsableSpace(long usableSpace) {
		this.usableSpace = usableSpace;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public JSONObject toJson() throws JSONException {
		JSONObject ob = new JSONObject();
		ob.put("name", name);
		ob.put("path", path);
		ob.put("totalSpace", totalSpace);
		ob.put("usableSpace", usableSpace);
		return ob;
	}

	@Override
	public String toString() {
		return "FMDPartion [name=" + name + ", totalSpace=" + totalSpace + ", usableSpace=" + usableSpace + "]";
	}

}
