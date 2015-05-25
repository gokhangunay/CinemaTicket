package cinema.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
	
	Properties properties = new Properties();
	
	public PropertyReader() throws IOException{
		FileInputStream input = new FileInputStream("app.properties");
		properties.load(input);
	}

	public String getDBURL(){
		return (String) properties.get("db.url");
	}
	public String getDBUser(){
		return (String) properties.get("db.user");

	}
	public String getDBPassword(){
		return (String) properties.get("db.pass");
	}
	
}
