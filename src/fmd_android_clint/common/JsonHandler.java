package fmd_android_clint.common;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;

import fmd_android_clint.socket.dto.Command;
import fmd_android_clint.socket.dto.MessageDto;

public class JsonHandler {

	public static JsonObject getJsonObjec(String str) {
		JsonReader reader = Json.createReader(new StringReader(str));
		JsonObject jsonObject = reader.readObject();
		reader.close();
		return jsonObject;
	}

	public static MessageDto getMessageDtoObject(String str) {
		JsonObject jsonObject = getJsonObjec(str);
		MessageDto messageDto = new MessageDto(
				jsonObject.getBoolean(Constants.MESSAGE_TYPE));
		messageDto.setContent(jsonObject.getString(Constants.MESSAGE_CONTENT));
		messageDto.setDeviceId(jsonObject.getInt(Constants.MESSAGE_DEVICE));
		messageDto.setUserId(jsonObject.getInt(Constants.MESSAGE_USER));
		return messageDto;
	}

	public static String getMessageDtoJson(MessageDto messageDto) {
		JsonObject jsonObject = Json.createObjectBuilder()
				.add(Constants.MESSAGE_TYPE, messageDto.isMessageType())
				.add(Constants.MESSAGE_CONTENT, messageDto.getContent())
				.add(Constants.MESSAGE_DEVICE, messageDto.getDeviceId())
				.add(Constants.MESSAGE_USER, messageDto.getUserId()).build();
		return jsonObject.toString();
	}

	public static Command getCommandObject(String str) {
		JsonObject jsonObject = getJsonObjec(str);
		JsonArray jsonArray = jsonObject.getJsonArray(Constants.COMAND_PARMS);
		String[] arr = (jsonArray.size() == 0) ? null : new String[jsonArray
				.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			arr[i] = jsonArray.getString(i);
		}
		Command command = new Command(
				jsonObject.getString(Constants.COMAND_COMMAND), arr);
		return command;
	}

	public static String getCommandJson(Command command) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		if (command.getParms() != null) {
			for (String str : command.getParms()) {
				jsonArrayBuilder.add(str);
			}
		}
		JsonObject jsonObject = Json.createObjectBuilder()
				.add(Constants.COMAND_COMMAND, command.getCommand())
				.add(Constants.COMAND_PARMS, jsonArrayBuilder).build();
		return jsonObject.toString();
	}
}

// Tutorial
// http://www.java2s.com/Tutorials/Java/JSON/0100__JSON_Java.htm