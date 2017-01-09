package portal.test.unit.util;

import java.util.HashSet;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

import portal.config.AppPropKeys;
import portal.util.Helper;

public class PortalHelperTest {

	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final String DLM = APP_PROPERTIES.getProperty(AppPropKeys.LOGICAL_OR_DELIMITER);

	@Test
	public void convertDelimitedStringToLongHashSetTest_IgnoreDuplicates() {
		String s = "1" + DLM + "2" + DLM + "2" + DLM;
		HashSet<Long> set = Helper.convertDelimitedStringToLongHashSet(s, DLM);

		Assert.assertTrue(set.contains(1L));
		Assert.assertTrue(set.contains(2L));
		Assert.assertEquals(set.size(), 2);
	}

	@Test
	public void convertDelimitedStringToLongHashSetTest_EmptyString() {
		HashSet<Long> set = Helper.convertDelimitedStringToLongHashSet("", DLM);
		Assert.assertEquals(set.size(), 0);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void convertDelimitedStringToLongHashSetTest_NullString() {
		Helper.convertDelimitedStringToLongHashSet(null, DLM);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void convertDelimitedStringToLongHashSetTest_NullDelimiter() {
		String s = "1" + DLM + "2" + DLM + "3" + DLM;
		Helper.convertDelimitedStringToLongHashSet(s, null);
	}

	@Test(expectedExceptions = NumberFormatException.class)
	public void convertDelimitedStringToLongHashSetTest_NaN() {
		String s = "1" + DLM + "Not-A-Number" + DLM + "3" + DLM;
		Helper.convertDelimitedStringToLongHashSet(s, DLM);
	}

	@Test(expectedExceptions = NumberFormatException.class)
	public void convertDelimitedStringToLongHashSetTest_MalformedString() {
		String s = "1" + DLM + "2" + DLM + DLM + "3" + DLM;
		Helper.convertDelimitedStringToLongHashSet(s, DLM);
	}

	@Test(expectedExceptions = NumberFormatException.class)
	public void convertDelimitedStringToLongHashSetTest_IncorrectDelimiter() {
		String s = "1" + DLM + "2" + DLM  + "3" + DLM;
		Helper.convertDelimitedStringToLongHashSet(s, "-");
	}

	@Test(expectedExceptions = NumberFormatException.class)
	public void convertDelimitedStringToLongHashSetTest_RandomData() {
		Helper.convertDelimitedStringToLongHashSet("xxxfffffwwwwwwxxxx", "ssssszzzzz");
	}
}
