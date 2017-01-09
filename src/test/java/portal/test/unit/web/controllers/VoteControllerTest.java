package portal.test.unit.web.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.HashSet;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.domain.impl.VoteStat;
import portal.service.VoteService;
import portal.web.controllers.VoteController;

public class VoteControllerTest {
	@Autowired
	@InjectMocks
	private VoteController controller;

	@Mock
	private VoteService voteService;

	@BeforeMethod
	public void setUpMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void handleVoteForWebItemRequest() {
		long feedbackOptionId = 1;
		long webItemId = 2;
		Collection<VoteStat> voteStats = new HashSet<VoteStat>();
		when(voteService.getVoteStatsForWebItem(webItemId)).thenReturn(voteStats);

		Collection<VoteStat> result = controller.handleVoteForWebItemRequest(webItemId, feedbackOptionId);

		verify(voteService).vote(webItemId, feedbackOptionId);
		verify(voteService).getVoteStatsForWebItem(webItemId);
		Assert.assertEquals(result, voteStats);
	}
}
