package portal.repository;

import portal.domain.impl.*;

/**
 * Data Access Object interface providing persistence operations.
 *
 */
public interface VoteDAO extends DAO{

	public VoteStat getVoteStat(WebItem webItem, FeedbackOption feedbackOption);

	public boolean userVotedForWebItem(WebItem webItem, User user);

	public boolean ipVotedForWebItem(WebItem webItem, String ip);
}
