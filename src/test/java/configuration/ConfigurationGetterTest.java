package configuration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class ConfigurationGetterTest {

	@Test
	public void testGettingConfigPropertiesSuccess() {
		ConfigurationGetter getter = new ConfigurationGetter();
		assertEquals("FILE MERGER", getter.getApplicationName());
	}
}
