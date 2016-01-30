package fmd_android_clint.filesystem;

import org.json.JSONException;
import org.json.JSONObject;

public class FMDFile {
	public String name;
	public String type;
	public long size;

	public FMDFile(String name, String type, long size) {
		super();
		this.name = name;
		this.type = type;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public JSONObject toJson() throws JSONException {
		JSONObject ob = new JSONObject();
		ob.put("name", name);
		ob.put("type", String.valueOf(type));
		ob.put("size", size);
		return ob;
	}

	@Override
	public String toString() {
		return "FMDFile [name=" + name + ", type=" + type + ", size=" + size + "]";
	}

}
