package portal.service.impl;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import portal.domain.impl.FeedbackOption;
import portal.domain.impl.User;
import portal.domain.impl.Vote;
import portal.domain.impl.VoteStat;
import portal.domain.impl.WebItem;
import portal.repository.FeedbackOptionDAO;
import portal.repository.VoteDAO;
import portal.security.context.SecurityContextFacade;
import portal.service.VoteService;
import portal.service.WebItemService;
import portal.web.context.RequestContextFacade;

@Service
@Transactional(rollbackFor = Exception.class)
public class VoteServiceImpl implements VoteService {
	@Autowired
	private WebItemService webItemService;

	@Autowired
	private VoteDAO voteDao;

	@Autowired
	private FeedbackOptionDAO feedbackOptionDao;

	@Autowired
	private SecurityContextFacade securityContext;

	@Autowired
	@Qualifier("requestContext")
	private RequestContextFacade requestContext;

	@Override
	public FeedbackOption getFeedbackOptionById(long feedbackOptionId) {
		return feedbackOptionDao.getEntityById(FeedbackOption.class,
				feedbackOptionId);
	}

	@Override
	public Collection<VoteStat> getVoteStatsForWebItem(long webItemId) {
		VoteStat voteStat;

		Collection<VoteStat> voteStats = new HashSet<VoteStat>();
		WebItem webItem = webItemService.getWebItemById(webItemId);
		Collection<FeedbackOption> feedbackOptions = webItem.getWebItemType()
				.getFeedbackType().getFeedbackOptions();

		for (FeedbackOption feedbackOption : feedbackOptions) {
			voteStat = voteDao.getVoteStat(webItem, feedbackOption);

			if (voteStat != null) {
				voteStats.add(voteStat);
			} else {
				voteStat = new VoteStat();

				voteStat.setFeedbackOption(feedbackOption);
				voteStat.setWebItem(webItem);
				voteStat.setVoteCount(0);
				voteStats.add(voteStat);
			}
		}
		return voteStats;
	}

	@Override
	public Vote insertVote(Vote vote) {
		if (!this.isVoted(vote.getWebItem())){
			vote = voteDao.insertEntity(vote);
			updateVoteStat(vote);
		}
		return vote;
	}

	@Override
	public boolean isVoted(WebItem webItem) {
		if (securityContext.isAnonymousUser()) {
			return this.ipVotedForWebItem(webItem, requestContext.ipAddress());
		} else {
			return this.userVotedForWebItem(webItem, securityContext.getLoggedInUser());
		}

	}

	private void updateVoteStat(Vote vote) {
		VoteStat voteStat = voteDao.getVoteStat(vote.getWebItem(),
				vote.getFeedbackOption());

		if (voteStat == null) {
			voteStat = new VoteStat();
			voteStat.setFeedbackOption(vote.getFeedbackOption());
			voteStat.setWebItem(vote.getWebItem());
			voteStat.setVoteCount(1);

			voteDao.insertEntity(voteStat);
		} else {
			voteStat.setVoteCount(voteStat.getVoteCount() + 1);
			voteDao.updateEntity(voteStat);
		}
	}

	@Override
	public boolean userVotedForWebItem(WebItem webItem, User user) {
		return voteDao.userVotedForWebItem(webItem, user);
	}

	@Override
	public Vote vote(long webItemId, long feedbackOptionId) {
		WebItem webItem = webItemService.getWebItemById(webItemId);
		FeedbackOption feedbackOption = this.getFeedbackOptionById(feedbackOptionId);

		Vote vote = new Vote();
		vote.setCreatedBy(securityContext.getLoggedInUser());
		vote.setIpAddress(requestContext.ipAddress());
		vote.setFeedbackOption(feedbackOption);
		vote.setWebItem(webItem);

		return this.insertVote(vote);
	}

	@Override
	public boolean ipVotedForWebItem(WebItem webItem, String ip) {
		return voteDao.ipVotedForWebItem(webItem, ip);
	}
}
