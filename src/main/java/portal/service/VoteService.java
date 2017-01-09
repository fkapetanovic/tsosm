package portal.service;

import java.util.Collection;
import portal.domain.impl.FeedbackOption;

import portal.domain.impl.User;
import portal.domain.impl.Vote;
import portal.domain.impl.VoteStat;
import portal.domain.impl.WebItem;

/**
 * "Service" interface for managing votes.
 *
 */
public interface VoteService {

	/**
	 * Retrieves a feedback option by its ID.
	 *
	 * @param feedbackOptionId
	 * @return a feedback option with the given ID or throws an exception if no such
	 *         entity.
	 */
	public FeedbackOption getFeedbackOptionById(long feedbackOptionId);

	/**
	 * Inserts a vote into the persistent store.
	 *
	 * @param vote
	 * @return the inserted vote.
	 */
	public Vote insertVote(Vote vote);

	/**
	 * Votes for a web item.
	 *
	 * @param webItemId
	 * @param feedbackOptionId
	 *            the selected feedback option.
	 * @return the inserted vote.
	 */
	public Vote vote(long webItemId, long feedbackOptionId);

	/**
	 * Retrieves voting stats for a particular web item.
	 *
	 * @param webItemId
	 * @return a collection of vote stats.
	 */
	public Collection<VoteStat> getVoteStatsForWebItem(long webItemId);

	/**
	 * Checks if the user had already voted for this web item.
	 *
	 * @param webItem
	 * @param user
	 *            logged in user.
	 * @return true if the user had already voted for the given web item, otherwise
	 *         false.
	 */
	public boolean userVotedForWebItem(WebItem webItem, User user);

	/**
	 *
	 * Checks if a vote was already passed for a certain web item from the given IP address.
	 *
	 * @param webItem
	 * @param ip
	 * 			IP address
	 * @return true if a vote was already passed from the given IP, otherwise
	 *         false.
	 */
	public boolean ipVotedForWebItem(WebItem webItem, String ip);

	/**
	 *
	 * Checks to see if voting for this item is possible for the logged in user.
	 *
	 * @param webItem
	 * 			Web Item
	 * @return true if voting is possible, otherwise false.
	 */
	public boolean isVoted(WebItem webItem);
}
