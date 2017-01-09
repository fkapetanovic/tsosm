package portal.test.integration.service;

import portal.test.integration.AbstractIntegrationTest;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.domain.impl.FeedbackOption;
import portal.domain.impl.User;
import portal.domain.impl.Vote;
import portal.domain.impl.VoteStat;
import portal.domain.impl.WebItem;
import portal.domain.impl.WebItemType;
import portal.service.UserService;
import portal.service.VoteService;
import portal.service.WebItemService;
import portal.service.WebItemTypeService;
import portal.test.integration.TestEntities;

public class VoteServiceIntegrationTest extends AbstractIntegrationTest {
	@Autowired
	private UserService userService;

	@Autowired
	private VoteService voteService;

	@Autowired
	private WebItemService webItemService;

	@Autowired
	private WebItemTypeService webItemTypeService;

	private User user;
	private User user2;
	private User user3;
	private WebItem webItem;
	private WebItemType webItemType;
	private FeedbackOption feedbackOption1;
	private FeedbackOption feedbackOption2;

	@BeforeMethod
	public void setUp() {
		user = userService.loadUserByUsername(TestEntities.TEST_USER_1);
		user2 = userService.loadUserByUsername(TestEntities.TEST_USER_2);
		user3 = userService.loadUserByUsername(TestEntities.TEST_USER_3);

		webItemType = webItemTypeService.getWebItemTypeByName(TestEntities.WEB_ITEM_TYPE_1);

		webItem = TestEntities.createWebItem1();
		webItem.setCreatedBy(user);
		webItem.setWebItemType(webItemType);

		webItem = webItemService.insertWebItem(webItem);

		Iterator<FeedbackOption> it = webItem.getWebItemType()
				.getFeedbackType().getFeedbackOptions().iterator();

		feedbackOption1 = it.next();
		feedbackOption2 = it.next();
	}

	@Test
	public void insertVoteTest() {
		Vote vote = new Vote();

		vote.setCreatedBy(user);
		vote.setFeedbackOption(feedbackOption1);
		vote.setWebItem(webItem);
		vote.setIpAddress("127.0.0.1");

		Vote insertedVote = voteService.insertVote(vote);

		Assert.assertEquals(insertedVote.getIpAddress(), vote.getIpAddress());
		Assert.assertEquals(insertedVote.getCreatedBy(), vote.getCreatedBy());
		Assert.assertEquals(insertedVote.getFeedbackOption(), vote.getFeedbackOption());
		Assert.assertEquals(insertedVote.getWebItem(), vote.getWebItem());

		Collection<VoteStat> voteStats = voteService
				.getVoteStatsForWebItem(webItem.getId());

		Assert.assertEquals(voteStats.size(), 6);

		for (VoteStat voteStat : voteStats) {

			if (voteStat.getFeedbackOption().getName()
					.equals(feedbackOption1.getName())) {
				Assert.assertEquals(voteStat.getVoteCount(), 1);
			} else {
				Assert.assertEquals(voteStat.getVoteCount(), 0);
			}
		}
	}

	@Test
	public void getFeedbackOptionByIdTest() {
		long feedbackOptionId = feedbackOption1.getId();

		FeedbackOption result = voteService
				.getFeedbackOptionById(feedbackOptionId);

		Assert.assertEquals(result.getId(), feedbackOptionId);
	}

	@Test
	public void insertVotesTest() {
		Vote vote1 = new Vote();

		vote1.setCreatedBy(user);
		vote1.setFeedbackOption(feedbackOption1);
		vote1.setWebItem(webItem);
		vote1.setIpAddress("127.0.0.1");

		Vote vote2 = new Vote();

		vote2.setCreatedBy(user2);
		vote2.setFeedbackOption(feedbackOption2);
		vote2.setWebItem(webItem);
		vote2.setIpAddress("127.0.0.1");

		Vote vote3 = new Vote();

		vote3.setCreatedBy(user3);
		vote3.setFeedbackOption(feedbackOption1);
		vote3.setWebItem(webItem);
		vote3.setIpAddress("127.0.0.1");

		voteService.insertVote(vote1);
		voteService.insertVote(vote2);
		voteService.insertVote(vote3);
		voteService.insertVote(vote2);
		voteService.insertVote(vote1);
		voteService.insertVote(vote1);
		voteService.insertVote(vote3);

		Collection<VoteStat> voteStats = voteService.getVoteStatsForWebItem(webItem.getId());

		Assert.assertEquals(voteStats.size(), 6);

		for (VoteStat voteStat : voteStats) {

			if (voteStat.getFeedbackOption().getName().equals(feedbackOption1.getName())) {
				Assert.assertEquals(voteStat.getVoteCount(), 2);
			} else if (voteStat.getFeedbackOption().getName()
					.equals(feedbackOption2.getName())) {
				Assert.assertEquals(voteStat.getVoteCount(), 1);
			}
		}
	}

	@Test
	public void userVotedForWebItemTest() {
		Vote vote1 = new Vote();

		vote1.setCreatedBy(user);
		vote1.setFeedbackOption(feedbackOption1);
		vote1.setWebItem(webItem);
		vote1.setIpAddress("127.0.0.1");

		voteService.insertVote(vote1);

		Assert.assertTrue(voteService.userVotedForWebItem(webItem, user));
		Assert.assertFalse(voteService.userVotedForWebItem(webItem, user2));
	}
}
