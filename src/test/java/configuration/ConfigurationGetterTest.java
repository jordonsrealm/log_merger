package configuration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import logmerger.frame.configuration.ConfigurationGetter;


public class ConfigurationGetterTest {

	@Test
	public void testGettingConfigPropertiesSuccess() {
		ConfigurationGetter getter = ConfigurationGetter.instance();
		assertEquals("FILE MERGER", getter.getApplicationName());
	}
}
