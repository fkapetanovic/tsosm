package portal.test.integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.domain.impl.Authority;
import portal.domain.impl.User;
import portal.repository.UserDAO;
import portal.service.UserService;
import portal.test.integration.AbstractIntegrationTest;
import portal.test.integration.TestEntities;

public class UserServiceIntegrationTest extends AbstractIntegrationTest {
	@Autowired
	private UserDAO userDao;

	@Autowired
	private UserService userService;

	private User loggedInUser;
	private Authority authority1;
	private Authority authority2;

	@BeforeMethod
	public void setUp() {

		loggedInUser = userService.loadUserByUsername(TestEntities.TEST_USER_1);
		authority1 = userService.getAuthority(TestEntities.ROLE_1);
		authority2 = userService.getAuthority(TestEntities.ROLE_2);
	}

	@Test
	public void insertUser() {
		User user = TestEntities.createUser1();

		user.addAuthority(authority1);
		user.setCreatedBy(loggedInUser);

		User insertedUser = userService.insertUser(user);

		Assert.assertEquals(insertedUser.geteMailAddress(), user.geteMailAddress());
		Assert.assertEquals(insertedUser.getPassword(), user.getPassword());
		Assert.assertEquals(insertedUser.getUsername(), user.getUsername());
		Assert.assertEquals(insertedUser.getCreatedBy(), user.getCreatedBy());
		Assert.assertEquals(insertedUser.isAccountNonExpired(), user.isAccountNonExpired());
		Assert.assertEquals(insertedUser.isAccountNonLocked(), user.isAccountNonLocked());
		Assert.assertEquals(insertedUser.isEnabled(), user.isEnabled());
		Assert.assertEquals(insertedUser.isDeleted(), false);
		Assert.assertEquals(insertedUser.isCredentialsNonExpired(), user.isCredentialsNonExpired());
		Assert.assertEquals(insertedUser.getAuthorities().size(), user.getAuthorities().size());

		Assert.assertEquals(insertedUser.getAuthorities().iterator().next()
				.getAuthority(), user.getAuthorities().iterator().next()
				.getAuthority());
	}

	@Test
	public void updateUser() {
		User user = TestEntities.createUser1();

		user.addAuthority(authority1);
		user.setCreatedBy(loggedInUser);

		userService.insertUser(user);

		User insertedUser = userService.loadUserByUsername(user.getUsername());

		insertedUser.setEnabled(false);
		insertedUser.setAccountNonExpired(false);
		insertedUser.setAccountNonLocked(false);
		insertedUser.setUpdatedBy(loggedInUser);
		insertedUser.setCredentialsNonExpired(false);
		insertedUser.seteMailAddress("new e-mail address");

		insertedUser.addAuthority(authority2);

		User updatedUser = userService.updateUser(insertedUser);

		Assert.assertEquals(updatedUser.geteMailAddress(), "new e-mail address");
		Assert.assertEquals(updatedUser.isAccountNonExpired(), false);
		Assert.assertEquals(updatedUser.isAccountNonLocked(), false);
		Assert.assertEquals(updatedUser.isCredentialsNonExpired(), false);
		Assert.assertEquals(updatedUser.isEnabled(), false);
		Assert.assertEquals(updatedUser.getAuthorities().size(), 2);

		updatedUser.clearAuthorities();

		updatedUser = userService.updateUser(updatedUser);

		Assert.assertEquals(updatedUser.getAuthorities().size(), 0);
	}

	@Test(expectedExceptions = UsernameNotFoundException.class)
	public void deleteUser() {
		User user = TestEntities.createUser1();

		user.addAuthority(authority1);
		user.setCreatedBy(loggedInUser);

		user = userService.insertUser(user);

		userService.deleteUser(user);

		user = userService.loadUserByUsername(user.getUsername());
	}

	@Test
	public void getUserByUsernameTest() {
		User user = userService.loadUserByUsername(TestEntities.TEST_USER_1);

		Assert.assertEquals(user.getUsername(), TestEntities.TEST_USER_1);
	}

	@Test
	public void getAutorityByAuthorityNameTest() {
		Authority authority = userService.getAuthority(TestEntities.ROLE_1);

		Assert.assertEquals(authority.getAuthority(), TestEntities.ROLE_1);
	}

	@Test
	public void emailExistsTest() {
		Assert.assertTrue(userService.emailExists(loggedInUser.geteMailAddress()));
		Assert.assertFalse(userService.emailExists("nothingtoseehere@mail.com"));
	}

	@Test
	public void getUserByUsernameAndActivationCodeTest(){
		User user1 = userDao.getUserByUserNameAndActivationCode(loggedInUser.getUsername(),
				loggedInUser.getActivationCode());
		Assert.assertNotNull(user1);

		User user2 = userDao.getUserByUserNameAndActivationCode("nothing", "here");
		Assert.assertNull(user2);
	}

	@Test
	public void getUserByUsernameAndPassCodeTest(){
		User user1 = userDao.getUserByUserNameAndPasswordChangeCode(loggedInUser.getUsername(),
			loggedInUser.getPasswordChangeCode());

		Assert.assertNotNull(user1);

		User user2 = userDao.getUserByUserNameAndPasswordChangeCode("nothing", "here");
		Assert.assertNull(user2);
	}
}
