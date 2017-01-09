package portal.test.unit.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.domain.impl.DataEntity;
import portal.domain.impl.FeedbackOption;
import portal.domain.impl.FeedbackType;
import portal.domain.impl.User;
import portal.domain.impl.Vote;
import portal.domain.impl.VoteStat;
import portal.domain.impl.WebItem;
import portal.domain.impl.WebItemType;
import portal.repository.FeedbackOptionDAO;
import portal.repository.VoteDAO;
import portal.security.context.SecurityContextFacade;
import portal.service.WebItemService;
import portal.service.impl.VoteServiceImpl;
import portal.web.context.RequestContextFacade;

public class VoteServiceTest {
	@Autowired
	@InjectMocks
	private VoteServiceImpl voteService;

	@Mock
	private WebItemService webItemService;

	@Mock
	private VoteDAO voteDao;

	@Mock
	private FeedbackOptionDAO feedbackOptionDao;

	@Mock
	private SecurityContextFacade securityContext;

	@Mock
	private RequestContextFacade requestContext;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getVoteStatsForWebItemFeedBackOptionTwoVotesTest() {
		Long webItemId = 1L;
		WebItem webItem = new WebItem();
		WebItemType webItemType = new WebItemType();
		FeedbackType feedbackType = new FeedbackType();
		FeedbackOption feedbackOption = new FeedbackOption();
		VoteStat voteStat = new VoteStat();
		voteStat.setVoteCount(2);

		InOrder inOrder = inOrder(webItemService, voteDao);

		feedbackType.getFeedbackOptions().add(feedbackOption);
		webItemType.setFeedbackType(feedbackType);
		webItem.setWebItemType(webItemType);

		when(webItemService.getWebItemById(webItemId)).thenReturn(webItem);
		when(voteDao.getVoteStat(webItem, feedbackOption)).thenReturn(voteStat);

		Collection<VoteStat> result = voteService.getVoteStatsForWebItem(webItemId);

		inOrder.verify(webItemService).getWebItemById(webItemId);
		inOrder.verify(voteDao).getVoteStat(webItem, feedbackOption);

		for (VoteStat item : result){
			if (item.getFeedbackOption() == feedbackOption){
				Assert.assertEquals(item.getVoteCount(), 2);
			}
		}
	}

	@Test
	public void getVoteStatsForWebItemFeedBackOptionZeroVotesTest() {
		Long webItemId = 1L;
		WebItem webItem = new WebItem();
		WebItemType webItemType = new WebItemType();
		FeedbackType feedbackType = new FeedbackType();
		FeedbackOption feedbackOption = new FeedbackOption();

		InOrder inOrder = inOrder(webItemService, voteDao);

		feedbackType.getFeedbackOptions().add(feedbackOption);
		webItemType.setFeedbackType(feedbackType);
		webItem.setWebItemType(webItemType);

		when(webItemService.getWebItemById(webItemId)).thenReturn(webItem);
		when(voteDao.getVoteStat(webItem, feedbackOption)).thenReturn(null);

		Collection<VoteStat> result = voteService.getVoteStatsForWebItem(webItemId);

		inOrder.verify(webItemService).getWebItemById(webItemId);
		inOrder.verify(voteDao).getVoteStat(webItem, feedbackOption);

		for (VoteStat item : result){
			if (item.getFeedbackOption() == feedbackOption){
				Assert.assertEquals(item.getVoteCount(), 0);
			}
		}
	}

	@Test
	public void getFeedbackOptionByIdTest() {
		voteService.getFeedbackOptionById(1L);
		verify(feedbackOptionDao).getEntityById(FeedbackOption.class, 1L);
	}

	@Test
	public void insertVoteUserAlreadyVotedForWebItemTest(){
		Vote vote = new Vote();
		WebItem webItem = new WebItem();
		User user = new User();
		FeedbackOption feedbackOption = new FeedbackOption();
		vote.setFeedbackOption(feedbackOption);
		vote.setWebItem(webItem);
		vote.setCreatedBy(user);

		when(voteDao.userVotedForWebItem(vote.getWebItem(), vote.getCreatedBy())).thenReturn(true);
		voteService.insertVote(vote);

		verify(voteDao, times(0)).insertEntity(any(DataEntity.class));
		verify(voteDao, times(0)).updateEntity(any(DataEntity.class));
		verify(voteDao).userVotedForWebItem(vote.getWebItem(), vote.getCreatedBy());
	}

	@Test
	public void insertVoteUserVotedNotForWebItemStatExistsNotTest(){
		Vote vote = new Vote();
		FeedbackOption feedbackOption = new FeedbackOption();
		WebItem webItem = new WebItem();
		User user = new User();
		vote.setFeedbackOption(feedbackOption);
		vote.setWebItem(webItem);
		vote.setCreatedBy(user);

		when(voteDao.userVotedForWebItem(vote.getWebItem(), vote.getCreatedBy())).thenReturn(false);
		when(voteDao.getVoteStat(webItem, feedbackOption)).thenReturn(null);
		when(voteDao.insertEntity(vote)).thenReturn(vote);

		InOrder inOrder = inOrder(voteDao);
		ArgumentCaptor<VoteStat> argument = ArgumentCaptor.forClass(VoteStat.class);

		voteService.insertVote(vote);

		inOrder.verify(voteDao).userVotedForWebItem(vote.getWebItem(), vote.getCreatedBy());
		inOrder.verify(voteDao).insertEntity(vote);
		inOrder.verify(voteDao).insertEntity(argument.capture());
		Assert.assertEquals(argument.getValue().getVoteCount(), 1);
		Assert.assertEquals(argument.getValue().getFeedbackOption(), feedbackOption);
		Assert.assertEquals(argument.getValue().getWebItem(), webItem);
	}

	@Test
	public void insertVoteUserVotedNotForWebItemStatExistsTest(){
		Vote vote = new Vote();
		FeedbackOption feedbackOption = new FeedbackOption();
		WebItem webItem = new WebItem();
		User user = new User();
		VoteStat voteStat = new VoteStat();

		vote.setFeedbackOption(feedbackOption);
		vote.setWebItem(webItem);
		vote.setCreatedBy(user);
		voteStat.setVoteCount(2);
		voteStat.setFeedbackOption(feedbackOption);
		voteStat.setWebItem(webItem);

		when(voteDao.userVotedForWebItem(vote.getWebItem(), vote.getCreatedBy())).thenReturn(false);
		when(voteDao.getVoteStat(webItem, feedbackOption)).thenReturn(voteStat);
		when(voteDao.insertEntity(vote)).thenReturn(vote);

		InOrder inOrder = inOrder(voteDao);
		ArgumentCaptor<VoteStat> argument = ArgumentCaptor.forClass(VoteStat.class);

		voteService.insertVote(vote);

		inOrder.verify(voteDao).userVotedForWebItem(vote.getWebItem(), vote.getCreatedBy());
		inOrder.verify(voteDao).insertEntity(vote);
		inOrder.verify(voteDao).updateEntity(argument.capture());
		Assert.assertEquals(argument.getValue().getVoteCount(), 3);
		Assert.assertEquals(argument.getValue().getFeedbackOption(), feedbackOption);
		Assert.assertEquals(argument.getValue().getWebItem(), webItem);
	}

	@Test
	public void voteTest(){
		long webItemId = 1L;
		long feedbackOptionId = 2L;
		String ipAddress = "127.0.0.1";
		VoteStat voteStat = new VoteStat();
		User user = new User();
		WebItem webItem = new WebItem();
		FeedbackOption feedbackOption = new FeedbackOption();
		Vote vote = new Vote();
		vote.setWebItem(webItem);
		vote.setFeedbackOption(feedbackOption);

		when(webItemService.getWebItemById(webItemId)).thenReturn(webItem);
		when(feedbackOptionDao.getEntityById(FeedbackOption.class, feedbackOptionId)).thenReturn(feedbackOption);
		when(securityContext.getLoggedInUser()).thenReturn(user);
		when(requestContext.ipAddress()).thenReturn(ipAddress);
		when(voteDao.insertEntity(any(DataEntity.class))).thenReturn(vote);
		when(voteDao.getVoteStat(webItem, feedbackOption)).thenReturn(voteStat);

		ArgumentCaptor<Vote> argument = ArgumentCaptor.forClass(Vote.class);

		voteService.vote(webItemId, feedbackOptionId);

		verify(webItemService).getWebItemById(webItemId);
		verify(feedbackOptionDao).getEntityById(FeedbackOption.class, feedbackOptionId);
		verify(securityContext).getLoggedInUser();
		verify(requestContext).ipAddress();
		verify(voteDao).insertEntity(argument.capture());
		Assert.assertEquals(argument.getValue().getIpAddress(), ipAddress);
		Assert.assertEquals(argument.getValue().getFeedbackOption(), feedbackOption);
		Assert.assertEquals(argument.getValue().getWebItem(), webItem);
		Assert.assertEquals(argument.getValue().getCreatedBy(), user);
	}
}
