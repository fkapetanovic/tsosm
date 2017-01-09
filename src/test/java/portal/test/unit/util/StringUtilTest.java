package portal.test.unit.util;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import portal.util.StringUtil;

public class StringUtilTest {
	@DataProvider(name="validNullEmptyWhiteSpaceData")
	public Object[][] validNullEmptyWhiteSpaceProvider(){
		return new Object[][]{{""},{"           "},{" "},{null}};
	}

	@DataProvider(name="invalidNullEmptyWhiteSpaceData")
	public Object[][] invalidNullEmptyWhiteSpaceProvider(){
		return new Object[][]{{"      xxx        "},{"           xx"},
				{"xx  "},{"  x xx x  "}};
	}

	@DataProvider(name="validNullEmptyData")
	public Object[][] validNullEmptyProvider(){
		return new Object[][]{{""},{null}};
	}

	@DataProvider(name="invalidNullEmptyData")
	public Object[][] invalidNullEmptyProvider(){
		return new Object[][]{{"             "},{"           "},
				{"xx  "},{"  x xx x  "}};
	}

	@Test (dataProvider="validNullEmptyWhiteSpaceData")
	public void isNullEmptyOrAllWhiteSpaceValidDataTest (String s){
		Assert.assertTrue(StringUtil.isNullEmptyOrAllWhiteSpace(s));
	}

	@Test (dataProvider="invalidNullEmptyWhiteSpaceData")
	public void isNullEmptyOrAllWhiteSpaceInvalidDataTest (String s){
		Assert.assertFalse(StringUtil.isNullEmptyOrAllWhiteSpace(s));
	}

	@Test (dataProvider="validNullEmptyData")
	public void isNullOrEmptyValidDataTest (String s){
		Assert.assertTrue(StringUtil.isNullOrEmpty(s));
	}

	@Test (dataProvider="invalidNullEmptyData")
	public void isNullOrEmptyInvalidDataTest (String s){
		Assert.assertFalse(StringUtil.isNullOrEmpty(s));
	}
}
