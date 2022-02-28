package logmerger.frame.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfigurationGetter {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationGetter.class);
	private static ConfigurationGetter configurationGetter;
	private String applicationName;
	private String appIconFileName;
	private Integer configWindowW;
	private Integer configWindowH;
	private String highlightHexColor;
	private boolean useLookAndFeel;
	
	private static final String CONFIG_PROPERTIES_FILENAME = "config.properties";
	
	class Constants{
		private Constants() {}
		
		static final String APPLICATION_NAME = "application_name";
		static final String WINDOW_WIDTH = "window_width";
		static final String WINDOW_HEIGHT = "window_height";
		static final String APP_ICON_FILE_NAME = "app_icon_file_name";
		static final String HIGHLIGHT_HEX_COLOR = "highlight_hex_color";
		static final String USE_LOOK_AND_FEEL = "useLookAndFeel";
	}
	
	private ConfigurationGetter() {
		
		try {
			getPropValues();
		} catch (IOException e) {
			logger.error("Unable to parse properties file");
		}
	}
	
	public static ConfigurationGetter instance() {
		if(configurationGetter == null) {
			configurationGetter = new ConfigurationGetter();
		}
		
		return configurationGetter;
	}
	
	private void getPropValues() throws IOException {
		 
		InputStream inputStream = null;
		
		try {
			Properties prop = new Properties();
 
			inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_PROPERTIES_FILENAME);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				logger.debug("Unable to load properties file: " + CONFIG_PROPERTIES_FILENAME);
				throw new FileNotFoundException("property file '" + CONFIG_PROPERTIES_FILENAME + "' not found in the classpath");
			}
 
			// get the property value and print it out
			applicationName = prop.getProperty(Constants.APPLICATION_NAME);
			configWindowW = Integer.parseInt(prop.getProperty(Constants.WINDOW_WIDTH));
			configWindowH = Integer.parseInt(prop.getProperty(Constants.WINDOW_HEIGHT));
			appIconFileName = prop.getProperty(Constants.APP_ICON_FILE_NAME);
			highlightHexColor = prop.getProperty(Constants.HIGHLIGHT_HEX_COLOR);
			useLookAndFeel = Boolean.getBoolean(prop.getProperty(Constants.USE_LOOK_AND_FEEL, "false"));
 
			String propertyList = String.join(",", appIconFileName, 
												   configWindowW.toString(), 
												   configWindowH.toString(), 
												   appIconFileName, 
												   highlightHexColor, 
												   Boolean.toString(useLookAndFeel));
			
			logger.info("Current Property List: " + propertyList);
		} catch (Exception e) {
			logger.error("Unable to get properties after opening properties file: " + e);
		} finally {
			if(inputStream != null) {
				logger.info("Closing properties file");
				inputStream.close();
			}
		}
	}
	
	public Integer getConfigWindowW() {
		return configWindowW;
	}

	public void setConfigWindowW(Integer configWindowW) {
		this.configWindowW = configWindowW;
	}

	public Integer getConfigWindowH() {
		return configWindowH;
	}

	public void setConfigWindowH(Integer configWindowH) {
		this.configWindowH = configWindowH;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	public String getAppIconFileName() {
		return appIconFileName;
	}

	public void setAppIconFileName(String appIconFileName) {
		this.appIconFileName = appIconFileName;
	}

	public String getHighlightHexColor() {
		return highlightHexColor;
	}

	public void setHighlightHexColor(String highlightHexColor) {
		this.highlightHexColor = highlightHexColor;
	}

	public boolean useLookAndFeel() {
		return useLookAndFeel;
	}

	public void setUseLookAndFeel(boolean useLookAndFeel) {
		this.useLookAndFeel = useLookAndFeel;
	}
}
