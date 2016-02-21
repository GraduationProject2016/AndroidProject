package fmd_android_clint.socket.dto;

import java.util.Arrays;

public class Command {

	private String command;

	private String[] parms;

	public Command(String command, String[] parms) {
		this.command = command;
		this.parms = parms;
	}

	public String getCommand() {
		return command;
	}

	public String[] getParms() {
		return parms;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public void setParms(String[] parms) {
		this.parms = parms;
	}

	@Override
	public String toString() {
		return "CommandConstant [command=" + command + ", parms=" + Arrays.toString(parms) + "]";
	}
}
