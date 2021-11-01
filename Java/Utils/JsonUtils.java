package monitora.qa.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonUtils {

	public static void writeJsonFile(String filePath, JSONObject obj){
		try (FileWriter file = new FileWriter(filePath)) {
			file.write(obj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static JSONObject parseJSONFile(String filePath) {
		JSONParser jsonParser = new JSONParser();
		FileReader reader = null;
		try {
			reader = new FileReader(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Object rawData = null;
		try {
			rawData = jsonParser.parse(reader);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return (JSONObject) rawData;
	}

	public static JSONObject parseJSONString(String JSONString) {
		JSONParser parser = new JSONParser();
		JSONObject json = null;
		try {
			json = (JSONObject) parser.parse(JSONString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return json;
	}

	public static String getStringValueFromJson(String attrName, JSONObject json){
		return (String) json.get(attrName);
	}

	public static double getLongAndDoubleAsDouble(String key, JSONObject jsonObject){
		Object value = jsonObject.get(key);
		if(value instanceof Long){
			return ((Long) value).doubleValue();
		}
		return (double) value;
	}

	public JSONObject insertStringValueIntoJsonObject(String key, Object value, JSONObject jsonObject){
		return (JSONObject) jsonObject.put(key, value);
	}

	public static JSONArray getJsonArrayFromJsonObject(String arrayName, JSONObject object) {
		return (JSONArray) object.get(arrayName);
	}
}
