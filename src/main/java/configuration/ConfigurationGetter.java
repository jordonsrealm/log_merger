package configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfigurationGetter {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationGetter.class);
	private String applicationName;
	private Integer windowWidth;
	private Integer windowHeight;
	private String appIconName;
	private String highlightHexColor;
	
	private static final String CONFIG_PROPERTIES_FILENAME = "config.properties";
	
	class Constants{
		private Constants() {}
		
		static final String APPLICATION_NAME = "application_name";
		static final String WINDOW_WIDTH = "window_width";
		static final String WINDOW_HEIGHT = "window_height";
		static final String APP_ICON_NAME = "app_icon_name";
		static final String HIGHLIGHT_HEX_COLOR = "highlight_hex_color";
	}
	
	public ConfigurationGetter() {
		
		try {
			getPropValues();
		} catch (IOException e) {
			logger.error("Unable to parse properties file");
		}
	}
	
	private ConfigurationGetter getPropValues() throws IOException {
		 
		InputStream inputStream = null;
		
		try {
			Properties prop = new Properties();
 
			inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_PROPERTIES_FILENAME);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + CONFIG_PROPERTIES_FILENAME + "' not found in the classpath");
			}
 
			Date time = new Date(System.currentTimeMillis());
 
			// get the property value and print it out
			applicationName = prop.getProperty(Constants.APPLICATION_NAME);
			windowWidth = Integer.parseInt(prop.getProperty(Constants.WINDOW_WIDTH));
			windowHeight = Integer.parseInt(prop.getProperty(Constants.WINDOW_HEIGHT));
			appIconName = prop.getProperty(Constants.APP_ICON_NAME);
			highlightHexColor = prop.getProperty(Constants.HIGHLIGHT_HEX_COLOR);
 
			String result = "Property List = " + applicationName + ", " + windowWidth + ", " + windowHeight + ", " + appIconName + "," + highlightHexColor;
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

	public String getAppIconName() {
		return appIconName;
	}

	public void setAppIconName(String appIconName) {
		this.appIconName = appIconName;
	}

	public String getHighlightHexColor() {
		return highlightHexColor;
	}

	public void setHighlightHexColor(String highlightHexColor) {
		this.highlightHexColor = highlightHexColor;
	}
	
}
