package fmd_android_clint.socket.dto;

import java.io.Serializable;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Acknowledgement implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Acknowledgement positiveAckonwledgement = null;

	private static Acknowledgement nagativeAckonwledgement = null;

	private boolean state;

	public Acknowledgement() {
	}

	private Acknowledgement(boolean state) {
		this.state = state;
	}

	public boolean isPositiveAcknowlegment() {
		return state == true;
	}

	public boolean isNagativeAcknowlegment() {
		return state != true;
	}

	public static Acknowledgement getPositiveAcknowledgement() {
		if (positiveAckonwledgement == null)
			positiveAckonwledgement = new Acknowledgement(true);
		return positiveAckonwledgement;
	}

	public static Acknowledgement getNagativeAcknowledgement() {
		if (nagativeAckonwledgement == null)
			nagativeAckonwledgement = new Acknowledgement(false);
		return nagativeAckonwledgement;
	}

	public String toJsonString() {
		return Json.createObjectBuilder().add("state", state).build().toString();
	}

	public static Acknowledgement toAcknowledgement(String json) {
		JsonReader reader = Json.createReader(new StringReader(json));
		JsonObject jsonObject = reader.readObject();
		reader.close();
		return jsonObject.getBoolean("state") ? getPositiveAcknowledgement() : getNagativeAcknowledgement();
	}

	@Override
	public String toString() {
		return "Acknowledgement [state = " + state + " ]";
	}

}
