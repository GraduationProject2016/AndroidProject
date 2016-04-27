package fmd_android_clint.common;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class FromJasonToArrayList {

	public static <T> List<T> jsonToList(String jsonString,
			final Class<T> objectClass) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		List<T> navigation = objectMapper.readValue(
				jsonString,
				objectMapper.getTypeFactory().constructCollectionType(
						List.class, objectClass));
		return navigation;
	}

}
// http://localhost:8080/fmd/webService/DeviceLocations/{deviceId}
// http://localhost:8080/fmd/webService/userDevices/{userId}