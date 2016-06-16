package fmd_android_clint.socket.entity;

import java.io.Serializable;
import java.util.Date;

public class Device implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private User user;

	private String name;

	private String password;

	private String macAddress;

	private Date lastActiveDate;

	private Boolean online;

	private Boolean type;

	private String content;

	private Boolean active;

	private String status;

	private Integer responceTime;

	private Integer VideoRecordTime;

	private Integer audioRecordTime;

	public Device() {

	}

	public Device(User user, String macAddress) {
		super();
		this.user = user;
		this.macAddress = macAddress;
	}

	public Integer getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public Date getLastActiveDate() {
		return lastActiveDate;
	}

	public Boolean getOnline() {
		return online;
	}

	public Boolean getType() {
		return type;
	}

	public Boolean getActive() {
		return active;
	}

	public String getStatus() {
		return status;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public void setLastActiveDate(Date lastActiveDate) {
		this.lastActiveDate = lastActiveDate;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public void setType(Boolean type) {
		this.type = type;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getResponceTime() {
		return responceTime;
	}

	public void setResponceTime(Integer responceTime) {
		this.responceTime = responceTime;
	}

	public Integer getVideoRecordTime() {
		return VideoRecordTime;
	}

	public void setVideoRecordTime(Integer videoRecordTime) {
		VideoRecordTime = videoRecordTime;
	}

	public Integer getAudioRecordTime() {
		return audioRecordTime;
	}

	public void setAudioRecordTime(Integer audioRecordTime) {
		this.audioRecordTime = audioRecordTime;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", user=" + user + ", name=" + name
				+ ", password=" + password + ", macAddress=" + macAddress
				+ ", lastActiveDate=" + lastActiveDate + ", online=" + online
				+ ", type=" + type + ", content=" + content + ", active="
				+ active + ", status=" + status + "]";
	}

}
