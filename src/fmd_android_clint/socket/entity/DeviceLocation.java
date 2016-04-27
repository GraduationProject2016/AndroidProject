package fmd_android_clint.socket.entity;

import java.util.Date;

public class DeviceLocation {

	private Integer id;
	private Device device;
	private String latitude;
	private String longitude;
	private Date takeIn;

	public DeviceLocation() {
	}

	public String toDisplayForm() {
		return latitude + "," + longitude;
	}

	public Integer getId() {
		return id;
	}

	public Device getDevice() {
		return device;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public Date getTakeIn() {
		return takeIn;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setTakeIn(Date takeIn) {
		this.takeIn = takeIn;
	}

}
