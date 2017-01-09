package portal.test.unit.validation;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import portal.config.AppPropKeys;
import portal.util.Helper;
import portal.validation.InputCheck;

public class InputCheckTest {
	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final String AND_DLM = APP_PROPERTIES.getProperty(AppPropKeys.LOGICAL_AND_DELIMITER);
	private final String OR_DLM = APP_PROPERTIES.getProperty(AppPropKeys.LOGICAL_OR_DELIMITER);

	@DataProvider(name="validUserNames")
	public Object[][] validUserNameProvider() {
		return new Object[][]{{"faruk-kapetanovic"},{"faruk_kapetanovic"},{"K-FaRuK_80"},
				{"k-900000-ss_0"}, {"k-s-2-1_22_aa_9"}};
	}

	@DataProvider(name="invalidUserNames")
	public Object[][] invalidUserNameProvider() {
		return new Object[][]{{"_faruk-kapetanovic"},{"8faruk_kapetanovic"},
				{"k-FaRuK_80_"},{" k-900000-ss_0"},{"''k-900000-ss_0"},{"$lk-900000-ss_0"},
				{"ssssssssssssssssssssssss"},{"k--900000-ss_0"}, {"             "}};
	}

	@DataProvider(name="reservedUserNames")
	public Object[][] reservedUserNamesProvider() {
		return new Object[][]{{"admin"},{"Admin"},
				{"aDmIn"},{"ADMINISTRATOR"},{"ROOt"},{"sysadmin"},
				{"systemadmin"},{"sYstem"}, {"systemadministrator"}, {"sysadministrator"}};
	}

	@DataProvider(name="validPasswords")
	public Object[][] validPasswordProvider() {
		return new Object[][]{{"        "},{"''#443;1``"},{"sss'  '11"},
				{"ss=s=sfsdkljf)- --1`"}, {"abcdefgh234"}};
	}

	@DataProvider(name="invalidPasswords")
	public Object[][] invalidPasswordProvider() {
		return new Object[][]{{"  "},{"''#44"},{"sss' "},
				{"ss=s=s"}, {"short"}};
	}

	@DataProvider(name="validEmails")
	public Object[][] validEmailProvider() {
		return new Object[][]{{"kapetanaovic.faruk@gmail.com"},{"USER@MAIL.COM"},
				{"929user@hotmail.com"}, {"dEfaulT@mail.co.ba"}, {"user_1980@mail.address"}};
	}

	@DataProvider(name="invalidEmails")
	public Object[][] invalidEmailsProvider() {
		return new Object[][]{{"_info@mail.com"},{"user @hotmail.com"},{"us''@hotmail.com"},
				{"userhotmail.com"},{"user@hotmail.c"},{"use1`~@mail.com"},
				{"q7eqhks#$%^&^&*([] }{  "}, {"             "}};
	}

	@DataProvider(name="safeIpAddresses")
	public Object[][] safeIdAddressProvider() {
		return new Object[][]{{"192.222.1.1"},{"1050:0000:0000:0000:0005:0600:300c:326b"}};
	}

	@DataProvider(name="unsafeIpAddresses")
	public Object[][] unsafeIdAddressProvider() {
		return new Object[][]{{"        "},{"''#443;1``"},{"sss'  '11"},
				{"192.222.1.1''"}, {"''"},
				{"1050:0000:0000:0000:0005:0600:300c:326b'"},
				{"''1050:0000:0000:0000:0005:0600:300c:326b"}};
	}

	@DataProvider(name="validGeneratedCodes")
	public Object[][] validGeneratedCodeProvider() {
		return new Object[][]{{"5a1d1d2bcac94e06bb5b31a952583122"},
				{"5a1d1d2bcac94e06bb5b31a952583122bbb"},
				{"aaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbb"}};
	}

	@DataProvider(name="invalidGeneratedCodes")
	public Object[][] invalidGeneratedCodeProvider() {
		return new Object[][]{{"5a1d1d2-bcac94e06bb5-b31a952583122"},
				{" 5a1d1d2bcac94e06bb5b31a952583122bbb "},
				{"' sssss"}, {"                 "},
				{"5a1d1d2bcac94e06kk5b31a952583122bbb"},
				{"5a1d1d2bcac94e06bb5b31a952583122bbb "},
				{"/5a1d1d2bcac94e06bb5b31a952583122bbb/"}};
	}

	@Test(dataProvider="validUserNames")
	public void isValidUsernameDataOKTest(String s) {
		Assert.assertTrue(InputCheck.isValidUserName(s));
	}

	@Test(dataProvider="invalidUserNames")
	public void isValidUsernameDataNotOKTest(String s) {
		Assert.assertFalse(InputCheck.isValidUserName(s));
	}

	@Test(dataProvider="reservedUserNames")
	public void isReservedUsername(String s) {
		Assert.assertTrue(InputCheck.isReservedUserName(s));
	}

	@Test(dataProvider="validPasswords")
	public void isValidPswdDataOKTest(String s) {
		Assert.assertTrue(InputCheck.isValidPassword(s));
	}

	@Test(dataProvider="invalidPasswords")
	public void isValidPswdDataNotOKTest(String s) {
		Assert.assertFalse(InputCheck.isValidPassword(s));
	}

	@Test(dataProvider="validEmails")
	public void isValidEmailDataOKTest(String s) {
		Assert.assertTrue(InputCheck.isValidEmailAddress(s));
	}

	@Test(dataProvider="invalidEmails")
	public void isValidEmailDataNotOKTest(String s) {
		Assert.assertFalse(InputCheck.isValidEmailAddress(s));
	}

	@Test(dataProvider="safeIpAddresses")
	public void isIpAddressSafeOKData(String s){
		Assert.assertTrue(InputCheck.isSafeIpAddress(s));
	}

	@Test(dataProvider="unsafeIpAddresses")
	public void isIpAddressSafeNotOkData(String s){
		Assert.assertFalse(InputCheck.isSafeIpAddress(s));
	}

	@Test(dataProvider="validGeneratedCodes")
	public void isValidGeneratedCodeOKData(String s){
		Assert.assertTrue(InputCheck.isValidGeneratedCode(s));
	}

	@Test(dataProvider="invalidGeneratedCodes")
	public void isValidGeneratedCodeNotOKData(String s){
		Assert.assertFalse(InputCheck.isValidGeneratedCode(s));
	}

	@Test
	public void isValidStringOfTagIdGroups_InputOK() {
		String input = "1" + AND_DLM + "2" + OR_DLM + "3" + OR_DLM + "4" + OR_DLM + "5" + OR_DLM;
		boolean result = InputCheck.isValidStringOfTagIdGroups(input);
		Assert.assertEquals(result, true);
	}

	@Test
	public void isValidStringOfTagIdGroups_TooManyTags() {
		String input = "1" + AND_DLM + "2" + OR_DLM + "3" + OR_DLM + "4" + OR_DLM + "5" + OR_DLM + "6" + OR_DLM;
		boolean result = InputCheck.isValidStringOfTagIdGroups(input);
		Assert.assertEquals(result, false);
	}

	@Test
	public void isValidStringOfTagIdGroups_MalformedInput() {
		String input = "xxxxyyyyyyyyyyyy!!!!!!!!!";
		boolean result = InputCheck.isValidStringOfTagIdGroups(input);
		Assert.assertEquals(result, false);
	}

	@Test
	public void isValidStringOfWebItemTypeIds_InputOK() {
		String input = "1" + OR_DLM + "2" + OR_DLM + "3" + OR_DLM + "4"
				+ OR_DLM + "5" + OR_DLM + "6" + OR_DLM + "7" + OR_DLM + "8"
				+ OR_DLM;

		boolean result = InputCheck.isValidStringOfWebItemTypeIds(input);
		Assert.assertEquals(result, true);
	}

	@Test
	public void isValidStringOfWebItemTypeIds_TooManyWebItemTypes() {
		String input = "1" + OR_DLM + "2" + OR_DLM + "3" + OR_DLM + "4"
				+ OR_DLM + "5" + OR_DLM + "6" + OR_DLM + "7" + OR_DLM + "8"
				+ OR_DLM + "9" + OR_DLM;

		boolean result = InputCheck.isValidStringOfWebItemTypeIds(input);
		Assert.assertEquals(result, false);
	}

	@Test
	public void isValidStringOfWebItemTypeIds_MalformedInput() {
		String input = "xxxxyyyyyyyyyyyy!!!!!!!!!";
		boolean result = InputCheck.isValidStringOfWebItemTypeIds(input);
		Assert.assertEquals(result, false);
	}
}
