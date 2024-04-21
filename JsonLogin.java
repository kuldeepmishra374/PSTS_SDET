package framework;

import java.io.File;
import java.nio.file.Files;

import constants.SystemVariables;

import org.json.simple.JSONObject;

import com.jayway.jsonpath.JsonPath;

public class JsonLogin extends FrameworkUtil{
	public JsonLogin() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	JSONObject loginUsers;
	static String json;
	public static String Uname,pwd;
	public File file =null;
	public void validUser() throws Exception
	{
		  try {
			  String dataFileName = SystemVariables.MAIN_RESOURCES+"Loginuser.json";
			  file = new File(dataFileName);
			  json = new String(Files.readAllBytes(file.toPath()));
			  Uname = JsonPath.read(json, "$.validUser.username");
			  pwd = JsonPath.read(json, "$.validUser.password");
		  } 
		  catch(Exception e) {
			  e.printStackTrace();
			  throw e;
		  }
		  
	}
	public void invalidUser() throws Exception
	{
		try {
		  String dataFileName = SystemVariables.MAIN_RESOURCES+"Loginuser.json";
		  file = new File(dataFileName);
		  json = new String(Files.readAllBytes(file.toPath()));
		  Uname = JsonPath.read(json, "$.invalidUser.username");
		  pwd = JsonPath.read(json, "$.invalidUser.password");
	  } 
	  catch(Exception e) {
		  e.printStackTrace();
		  throw e;
	  }
		
	}
	public void invalidPassword() throws Exception
	{
		try {
		  String dataFileName = SystemVariables.MAIN_RESOURCES+"Loginuser.json";
		  file = new File(dataFileName);
		  json = new String(Files.readAllBytes(file.toPath()));
		  Uname = JsonPath.read(json, "$.invalidPassword.username");
		  pwd = JsonPath.read(json, "$.invalidPassword.password");
	  } 
	  catch(Exception e) {
		  e.printStackTrace();
		  throw e;
	  }
		
	}
	
	
}
