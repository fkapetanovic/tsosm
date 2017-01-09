package portal.test.integration;

import java.util.Date;

import portal.domain.impl.Log;
import portal.domain.impl.Tag;
import portal.domain.impl.User;
import portal.domain.impl.WebItem;

public class TestEntities {

	public static String TEST_USER_1 = "user";
	public static String TEST_USER_2 = "superuser";
	public static String TEST_USER_3 = "anonymousUser";

	public static String WEB_ITEM_TYPE_1 = "Pic";
	public static String WEB_ITEM_TYPE_2 = "Video";

	public static String ROLE_1 = "ROLE_USER";
	public static String ROLE_2 = "ROLE_SUPER_USER";

	public static Tag createPrimaryTag1() {

		Tag tag = new Tag();

		tag.setDescription("Primary tag 1 description");
		tag.setIconPath("test/tags/primarytag1.jpg");
		tag.setIsPrimary(true);
		tag.setName("Primary tag 1");
		tag.setPosition(1);

		return tag;
	}

	public static Tag createPrimaryTag2() {

		Tag tag = new Tag();

		tag.setDescription("Primary tag 2 description");
		tag.setIconPath("test/tags/primarytag2.jpg");
		tag.setIsPrimary(true);
		tag.setName("Primary tag 2");
		tag.setPosition(2);

		return tag;
	}

	public static Tag createTag1() {

		Tag tag = new Tag();

		tag.setDescription("Tag 1 description");
		tag.setIconPath("test/tags/tag1.jpg");
		tag.setIsPrimary(false);
		tag.setName("Tag 1");
		tag.setPosition(3);

		return tag;
	}

	public static Tag createTag2() {

		Tag tag = new Tag();

		tag.setDescription("Tag 2 description");
		tag.setIconPath("test/tags/tag2.jpg");
		tag.setIsPrimary(false);
		tag.setName("Tag 2");
		tag.setPosition(4);

		return tag;
	}

	public static Tag createTag3() {

		Tag tag = new Tag();

		tag.setDescription("Tag 3 description");
		tag.setIconPath("test/tags/tag3.jpg");
		tag.setIsPrimary(false);
		tag.setName("Tag 3");
		tag.setPosition(5);

		return tag;
	}

	public static Tag createTag4() {

		Tag tag = new Tag();

		tag.setDescription("Tag 4 description");
		tag.setIconPath("test/tags/tag4.jpg");
		tag.setIsPrimary(false);
		tag.setName("Tag 4");
		tag.setPosition(6);

		return tag;
	}

	public static WebItem createFeaturedWebItem1() {

		WebItem webItem = new WebItem();

		webItem.setFeatured(true);
		webItem.setSourceName("test name 1");
		webItem.setSourceURL("www.test1.com");
		webItem.setText("Test text 1");
		webItem.setTitle("Test title 1");
		webItem.setWebItemDate(new Date());

		return webItem;
	}

	public static WebItem createFeaturedWebItem2() {

		WebItem webItem = new WebItem();

		webItem.setFeatured(true);
		webItem.setSourceName("test name 2");
		webItem.setSourceURL("www.test2.com");
		webItem.setText("Test text 2");
		webItem.setTitle("Test title 2");
		webItem.setWebItemDate(new Date());

		return webItem;
	}

	public static WebItem createFeaturedWebItem3() {

		WebItem webItem = new WebItem();

		webItem.setFeatured(true);
		webItem.setSourceName("test name 3");
		webItem.setSourceURL("www.test3.com");
		webItem.setText("Test text 3");
		webItem.setTitle("Test title 3");
		webItem.setWebItemDate(new Date());

		return webItem;
	}

	public static WebItem createWebItem1() {

		WebItem webItem = new WebItem();

		webItem.setFeatured(false);
		webItem.setSourceName("test name 4");
		webItem.setSourceURL("www.test4.com");
		webItem.setText("Test text 4");
		webItem.setTitle("Test title 4");
		webItem.setWebItemDate(new Date());

		return webItem;
	}

	public static WebItem createWebItem2() {

		WebItem webItem = new WebItem();

		webItem.setFeatured(false);
		webItem.setSourceName("test name 5");
		webItem.setSourceURL("www.test5.com");
		webItem.setText("Test text 5");
		webItem.setTitle("Test title 5");
		webItem.setWebItemDate(new Date());

		return webItem;
	}

	public static WebItem createWebItem3() {

		WebItem webItem = new WebItem();

		webItem.setFeatured(false);
		webItem.setSourceName("test name 6");
		webItem.setSourceURL("www.test6.com");
		webItem.setText("Test text 6");
		webItem.setTitle("Test title 6");
		webItem.setWebItemDate(new Date());

		return webItem;
	}

	public static Log createLog1() {

		Log log = new Log();

		log.setAdditionalInfo("Additonal info 1");
		log.setIpAddress("127.0.0.1");
		log.setOperation("Operation 1");
		log.setText("Text 1");

		return log;
	}

	public static Log createLog2() {

		Log log = new Log();

		log.setAdditionalInfo("Additonal info 2");
		log.setIpAddress("127.0.0.1");
		log.setOperation("Operation 2");
		log.setText("Text 2");

		return log;
	}

	public static User createUser1() {

		User user = new User();

		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.seteMailAddress("mail@example.com");
		user.setActivationCode("5a1d1d2bcac94e06bb5b31a952583129");
		user.setPasswordChangeCode("5a1d1d2bcac94e06bb5b31a952583122");
		user.setEnabled(true);
		user.setPassword("pswduser1");
		user.setUsername("user_1");

		return user;
	}
}
