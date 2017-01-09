package portal.repository.jdo;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import portal.domain.impl.*;
import portal.repository.VoteDAO;

@Repository
public class JdoVoteDAO extends JdoDAO implements VoteDAO {
	@Override
	public VoteStat getVoteStat(WebItem webItem, FeedbackOption feedbackOption) {
		String filter = "webItem == p1 && feedbackOption == p2";
		String parameters = "WebItem p1, FeedbackOption p2";
		String imports = "import portal.domain.impl.WebItem; "
				+ "import portal.domain.impl.FeedbackOption;";
		String ordering = "";

		Object[] values = new Object[2];

		values[0] = webItem;
		values[1] = feedbackOption;

		Collection<VoteStat> voteStats = getEntities(VoteStat.class, filter,
				parameters, ordering, imports, values);

		if (voteStats.isEmpty()) {
			return null;
		}

		return voteStats.iterator().next();
	}

	@Override
	public boolean userVotedForWebItem(WebItem webItem, User user) {
		String filter = "webItem == p1 && createdBy == p2";
		String parameters = "WebItem p1, User p2";
		String imports = "import portal.domain.impl.WebItem; "
				+ "import portal.domain.impl.User;";

		String ordering = "";

		Object[] values = new Object[2];

		values[0] = webItem;
		values[1] = user;

		Collection<Vote> votes = getEntities(Vote.class, filter,
				parameters, ordering, imports, values);

		if (votes.isEmpty()){
			return false;
		} else {
			return true;
		}
	}

	public boolean ipVotedForWebItem(WebItem webItem, String ip) {
		String filter = "webItem == p1 && ipAddress == p2";
		String parameters = "WebItem p1, String p2";
		String imports = "import portal.domain.impl.WebItem; "
				+ "import portal.domain.impl.User;";

		String ordering = "";

		Object[] values = new Object[2];

		values[0] = webItem;
		values[1] = ip;

		Collection<Vote> votes = getEntities(Vote.class, filter,
				parameters, ordering, imports, values);

		if (votes.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}
}
