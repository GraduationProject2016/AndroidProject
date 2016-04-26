package fmd_android_clint.socket.dto;

import java.io.Serializable;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class FilePart implements Serializable {

	private static final long serialVersionUID = 1L;

	private int partNum;
	private byte[] part;
	private int count;

	public FilePart(byte[] part, int partNum, int count) {
		this.part = part;
		this.partNum = partNum;
		this.count = count;
	}

	public String toJsonString() {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		for (int i : part)
			jsonArrayBuilder.add(i);
		return Json.createObjectBuilder().add("partNum", partNum).add("count", count).add("part", jsonArrayBuilder)
				.build().toString();
	}

	public static FilePart toFilePart(String json) {
		JsonReader reader = Json.createReader(new StringReader(json));
		JsonObject jsonObject = reader.readObject();
		reader.close();
		JsonArray jsonArray = jsonObject.getJsonArray("part");
		byte[] arr = (jsonArray.size() == 0) ? null : new byte[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			arr[i] = (byte) jsonArray.getInt(i);
		}
		return new FilePart(arr, jsonObject.getInt("partNum"), jsonObject.getInt("count"));
	}

	@Override
	public String toString() {
		return "FilePart [partNum = " + partNum + ", partLenght = " + part.length + ", count = " + count + " ]";
	}

	public int getPartNum() {
		return partNum;
	}

	public byte[] getPart() {
		return part;
	}

	public int getCount() {
		return count;
	}

}
