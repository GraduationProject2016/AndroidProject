package fmd_android_clint.filesystem;

import org.json.JSONException;
import org.json.JSONObject;

public class FMDDirectory {

	public String name;
	public long size;

	public FMDDirectory(String name, long size) {
		super();
		this.name = name;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		ob.put("size", size);
		return ob;
	}

	@Override
	public String toString() {
		return "FMDDirectory [name=" + name + ", size=" + size + "]";
	}

}