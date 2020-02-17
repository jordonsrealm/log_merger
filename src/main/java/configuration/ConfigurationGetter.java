package configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import components.MainWindow;


public class ConfigurationGetter {

	private static final Logger logger = LoggerFactory.getLogger(MainWindow.class);
	String result = "";
	InputStream inputStream;
	private String applicationName;
	private Integer windowWidth;
	private Integer windowHeight;
	
	
	public ConfigurationGetter() {
		try {
			getPropValues();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Unable to parse properties file");
		}
	}
	
	private ConfigurationGetter getPropValues() throws IOException {
		 
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			Date time = new Date(System.currentTimeMillis());
 
			// get the property value and print it out
			applicationName = prop.getProperty("application_name");
			windowWidth = Integer.parseInt(prop.getProperty("window_width"));
			windowHeight = Integer.parseInt(prop.getProperty("window_height"));
 
			result = "Property List = " + applicationName + ", " + windowWidth + ", " + windowHeight;
			System.out.println(result + "\nProgram Ran on " + time);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		
		return this;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public Integer getWindowWidth() {
		return windowWidth;
	}

	public void setWindowWidth(Integer windowWidth) {
		this.windowWidth = windowWidth;
	}

	public Integer getWindowHeight() {
		return windowHeight;
	}

	public void setWindowHeight(Integer windowHeight) {
		this.windowHeight = windowHeight;
	}
	
}
