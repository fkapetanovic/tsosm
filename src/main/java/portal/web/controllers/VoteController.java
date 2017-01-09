package portal.web.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import portal.domain.impl.VoteStat;
import portal.service.VoteService;
import portal.service.WebItemService;

@Controller
public class VoteController {
	@Autowired
	private VoteService voteService;

	@Autowired
	private WebItemService webItemService;

	@RequestMapping(method = RequestMethod.GET, value = "/home/vote/webItem")
	public @ResponseBody
	Collection<VoteStat> handleVoteForWebItemRequest(
			@RequestParam long webItemId, @RequestParam long feedbackOptionId) {

		voteService.vote(webItemId, feedbackOptionId);
		return voteService.getVoteStatsForWebItem(webItemId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/home/vote/webItemVoteStats")
	public @ResponseBody
	Collection<VoteStat> handleGetVoteStateForWebItemRequest(@RequestParam long webItemId) {
		return voteService.getVoteStatsForWebItem(webItemId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/home/vote/isVoted")
	public @ResponseBody
	boolean handleIsVotedRequest(@RequestParam long webItemId) {
		return voteService.isVoted(webItemService.getWebItemById(webItemId));
	}
}
