$(document).ready(function() {
	getWebItems();
	$('.tags img').click(tagClicked);
	$('.subTags').on('click', 'img', subtagClicked);
	$('.selectedTag').on('click', 'img', selectedTagClicked);
	$('.tagLink').click(tagLinkClicked);
	$('.wiType').click(wiTypeClicked);
	var feed = $('.feed');
	feed.on('click', '.webItem .captionTop img', captionTagClicked);
	feed.on('click', '.webItem .captionTop a', captionWITypeClicked);
	feed.on('click', '.webItem .content', openLink);
	feed.on('click', '.webItem .source', openLink);
	feed.on('click', '.webItem .osm', osm);
	feed.on('click', '.viewMore #viewMore', viewMore);
	$('#searchButton').click(searchClicked);
	$('#resetButton').click(resetSearch);

	$.currentRequest = {
		tagIdGroups : "",
		webItemTypeIds : "",
		page : 1
	};

	feed.on({
		mouseenter : function() {
			$('.captionTop', this).stop().animate({
				top : '0px'
			}, {
				queue : false,
				duration : SLIDER_TRANSITION_DURATION
			});
			$('.unmarked', this).stop().animate({
				'border-width' : '6px'
			}, {
				queue : false,
				duration : SLIDER_TRANSITION_DURATION
			});

		},
		mouseleave : function() {
			$('.captionTop', this).stop().animate({
				top : '-60px'
			}, {
				queue : false,
				duration : SLIDER_TRANSITION_DURATION
			});
			$('.unmarked', this).stop().animate({
				'border-width' : '0px'
			}, {
				queue : false,
				duration : SLIDER_TRANSITION_DURATION
			});

		}
	}, '.webItem');

});

selectedPrimaryTagId = 0;
selectedTags = [ 0, 0, 0, 0, 0 ];
tagLinks = [ false, false, false, false ];
selectedWITypes = [];
LOGICAL_AND_DELIMITER = "-";
LOGICAL_OR_DELIMITER = "_";
PAGE_SIZE = 20;
LINK_OPACITY = 0.1;
FADE_IN_TRANSITION_DURATION = 400;
SLIDER_TRANSITION_DURATION = 500;

function resetSearch() {

	$(".wiTypes").children('a').each(function(index, value) {
		$(this).removeClass();
		$(this).addClass('wiType');
	});

	$("div.selectedTag").empty();

	$("img.tagLink").css("opacity", LINK_OPACITY);

	selectedTags.length = 0;
	tagLinks.length = 0;
	selectedWITypes.length = 0;

	selectedTags = [ 0, 0, 0, 0, 0 ];
	tagLinks = [ false, false, false, false ];
}


function captionTagClicked() {

	$.currentRequest.tagIdGroups = $(this).attr("oid") + LOGICAL_OR_DELIMITER;
	$.currentRequest.webItemTypeIds = '';
	$.currentRequest.page = 1;
	rewindScroll();	

	$.getJSON("/home/searchWebItems", {
		tagIdGroups : $.currentRequest.tagIdGroups,
		webItemTypeIds : $.currentRequest.webItemTypeIds,
		page : $.currentRequest.page
	}, function(data) {
		renderFeed(data);
	});
}

function captionWITypeClicked() {

	$.currentRequest.tagIdGroups = "";
	$.currentRequest.webItemTypeIds = $(this).attr("oid")
			+ LOGICAL_OR_DELIMITER;
	$.currentRequest.page = 1;
	rewindScroll();	

	$.getJSON("/home/searchWebItems", {
		tagIdGroups : $.currentRequest.tagIdGroups,
		webItemTypeIds : $.currentRequest.webItemTypeIds,
		page : $.currentRequest.page
	}, function(data) {
		renderFeed(data);
	});
}

function searchClicked() {
	$.currentRequest.tagIdGroups = "";
	var indexOfLastElement = selectedTags.length;
	rewindScroll();	

	for ( var i in selectedTags) {
		if (selectedTags[i] != 0 && i != indexOfLastElement) {
			$.currentRequest.tagIdGroups += selectedTags[i];
			if (tagLinks[i] == true) {
				$.currentRequest.tagIdGroups += LOGICAL_AND_DELIMITER;
			} else {
				$.currentRequest.tagIdGroups += LOGICAL_OR_DELIMITER;
			}
		} else {
			if (i == indexOfLastElement) {
				$.currentRequest.tagIdGroups += selectedTags[i];
				$.currentRequest.tagIdGroups += LOGICAL_OR_DELIMITER;
			}
			break;
		}
	}

	$.currentRequest.webItemTypeIds = "";

	for ( var i in selectedWITypes) {
		$.currentRequest.webItemTypeIds += selectedWITypes[i]
				+ LOGICAL_OR_DELIMITER;
	}

	$.currentRequest.page = 1;

	$.getJSON("/home/searchWebItems", {
		tagIdGroups : $.currentRequest.tagIdGroups,
		webItemTypeIds : $.currentRequest.webItemTypeIds,
		page : $.currentRequest.page
	}, function(data) {
		renderFeed(data);
	});	
}

function getWebItems() {

	var $loadingDiv = $("<div class='loading'>Loading feed"
			+ "<img src='/resources/pics/home/loading.gif'></img></div>");

	$('#feed').append($loadingDiv);
	$.getJSON("/home/getWebItems", {
		page : 1
	}, function(data) {
		renderFeed(data);
	});
}

function getBelongingTags(tagId) {

	$.getJSON("/home/getBelongingTags", {
		tagId : tagId
	}, function(data) {
		renderSubtags(data);
	});
}

function tagClicked() {

	var tagId = $(this).attr("oid");

	if (selectedPrimaryTagId == tagId) {
		addTagToPanel($(this).attr("src"), tagId);
	} else {
		getBelongingTags(tagId);
		selectedPrimaryTagId = tagId;
	}
}

function subtagClicked() {

	addTagToPanel($(this).attr("src"), $(this).attr("oid"));
}

function addTagToPanel(imageSrc, tagOid) {

	var index = getIndexOfNextEmptySlot();

	if (index >= 0) {
		var tag = $('<div><img src="' + imageSrc + '" /></div>');
		tag.hide();
		$('#tagSlot' + index).append(tag);
		tag.fadeIn(FADE_IN_TRANSITION_DURATION);

		selectedTags[index] = tagOid;
	}
}

function renderSubtags(data) {

	var tags = data;
	var html = '';

	for (i in tags) {
		var tag = tags[i];
		html = html + "<img oid='" + tag.id + "' src='/resources/"
				+ tag.iconPath + "' title='" + tag.name + "'></img>";
	}

	$('#sub').empty();
	$('#sub').append(html);
}

function selectedTagClicked() {

	var tagIndex = parseInt($(this).parent().parent().attr("index"));
	var arrayElementType = getArrayElementType(tagIndex);

	if (arrayElementType == "head") {
		tagLinks[tagIndex] = false;
		$('#tagLink' + tagIndex + ' img').css('opacity', LINK_OPACITY);
	} else if (arrayElementType == "tail") {
		tagLinks[tagIndex - 1] = false;
		$('#tagLink' + tagIndex - 1 + ' img').removeClass();
		$('#tagLink' + tagIndex - 1 + ' img').css('opacity', LINK_OPACITY);
	}

	shiftElementsToTheLeft(tagIndex);
}

function shiftElementsToTheLeft(index) {

	shiftTagsToTheLeft(index);
	shiftTagLinksToTheLeft(index);
}

function shiftTagsToTheLeft(index) {

	var indexOfNextElement = 0;
	var indexOfLastElement = selectedTags.length - 1;

	for ( var i = index; i <= indexOfLastElement; i++) {

		if (!(i == indexOfLastElement)) {
			indexOfNextElement = i + 1;
			selectedTags[i] = selectedTags[indexOfNextElement];
			$('#tagSlot' + i).html('');
			$('#tagSlot' + i).html($('#tagSlot' + indexOfNextElement).html());
		} else {
			selectedTags[i] = 0;
			$('#tagSlot' + i).html('');
		}
	}
}

function shiftTagLinksToTheLeft(index) {

	var indexOfNextElement = 0;
	var indexOfLastElement = tagLinks.length - 1;

	for ( var i = index - 1; i <= indexOfLastElement; i++) {

		if (!(i == indexOfLastElement)) {
			indexOfNextElement = i + 1;
			tagLinks[i] = tagLinks[indexOfNextElement];
			$('#tagLink' + i + " img").css('opacity',
					$('#tagLink' + indexOfNextElement + " img").css('opacity'));
		} else {
			tagLinks[i] = false;
			$('#tagLink' + i + ' img').css('opacity', LINK_OPACITY);
		}
	}
}

function getArrayElementType(index) {

	var result;
	var isFirstElement = (index == 0);
	var isLastElement = ((selectedTags.Length - 1) == index);
	var isLinkedToTheRight = (!isLastElement && tagLinks[index] == true);
	var isLinkedToTheLeft = (!isFirstElement && tagLinks[index - 1] == true);

	if (isLinkedToTheRight && !isLinkedToTheLeft) {
		result = "head";
	} else if (!isLinkedToTheRight && isLinkedToTheLeft) {
		result = "tail";
	} else {
		result = "other";
	}

	return result;
}

function tagLinkClicked() {

	var index = $(this).parent().attr("index");
	var linkSelected = tagLinks[index];

	if (!linkSelected) {

		if (selectedTags[index] > 0 && selectedTags[parseInt(index) + 1] > 0) {
			$(this).stop().animate({
				opacity : '1'
			}, FADE_IN_TRANSITION_DURATION);
			tagLinks[index] = true;
		}
	} else {
		$(this).stop().animate({
			opacity : LINK_OPACITY
		}, FADE_IN_TRANSITION_DURATION);
		tagLinks[index] = false;
	}
}

function getIndexOfNextEmptySlot() {
	for ( var i = 0; i < selectedTags.length; i++) {
		if (selectedTags[i] == 0) {
			return i;
		}
	}
	return -1;
}

function wiTypeClicked() {

	if ($(this).attr('class') == 'wiType') {
		$(this).removeClass();
		$(this).addClass('wiType wiTypeSelected');
		addWITypeToSelectedWITypes($(this).attr('oid'));
	} else {
		$(this).removeClass();
		$(this).addClass('wiType');
		removeWITypeFromSelectedWITypes($(this).attr('oid'));
	}
}

function addWITypeToSelectedWITypes(oid) {

	selectedWITypes.push(oid);
}

function removeWITypeFromSelectedWITypes(oid) {

	for ( var i in selectedWITypes) {
		if (selectedWITypes[i] == oid) {
			selectedWITypes.splice(i, 1);
			break;
		}
	}
}

function sortById(a, b) {
	return b.id - a.id;
}

function renderFeed(webItems) {

	var webItem;
	var tags;
	var feedbackType;
	var html = '';
	var oid;
	var wiType;
	webItems = webItems.sort(sortById);

	for (i in webItems) {

		webItem = webItems[i];
		tags = webItem.tags;
		oid = webItem.id;
		wiType = webItem.webItemType;
		feedbackType = webItem.webItemType.feedbackType;
		date = new Date(webItem.webItemDate).toLocaleDateString();

		html = html + "<div oid='" + oid + "' class='webItem panel "
				+ wiType.name.toLowerCase() + "'  url='" + webItem.sourceURL
				+ "'><div class='captionTop caption'>"
				+ addTagsAndWITypeToToCaption(tags, wiType) + "</div>";

		html = html + "<div class='header'><p>" + webItem.title + "</p></div>";
		html = html + "<div class='content'>" + createMedia(webItem)
				+ "<div class='text'>" + webItem.text + "</div></div>";
		html = html + "<div class='footer'><p class='source'>"
				+ webItem.sourceName + "</p>";
		html += addCorner(feedbackType, oid);

		html += "</div>";
		html = html + "</div>";
	}

	if ($.currentRequest.page == 1) {
		$('#feed').empty();
	}
	;

	$('#feed').append(html);
	$(".viewMore").remove();
	$("#nothingFoundMsg").remove();

	if (webItems.length >= PAGE_SIZE) {
		displayViewMore();
	} else if (webItems.length == 0) {
		displayNothingFoundMessage();
	}
}

function displayViewMore() {

	var $viewMore = $("<div class='viewMore'><button id='viewMore'>"
			+ "More...</button></div>");
	$("#feed").append($viewMore);
}

function displayNothingFoundMessage() {

	if ($('#feed').html() == '') {
		var $nothingFound = $("<div class='panel infoMessage' id='nothingFoundMsg'>"
				+ "Nothing was found.</div>");
		$("#feed").append($nothingFound);
	}
}

function addTagsAndWITypeToToCaption(tags, wiType) {

	var html = "<div class='wiTypeCaption'><a oid='" + wiType.id + "'>"
			+ wiType.name + "</a></div>";

	tags.sort(function(a, b) {
		return a["name"] > b["name"];
	});

	for (i in tags) {
		var tag = tags[i];
		html = html + "<img oid='" + tag.id + "' src='/resources/"
				+ tag.iconPath + "' title='" + tag.name + "'></img>";
	}
	return html;
}

function createMedia(webItem) {

	var html = "<div class='media'>";

	if (webItem.sourceName.toLowerCase().indexOf("youtube.com") != -1) {
		html = html + generateYouTubeEmbedCode(webItem.sourceURL);
	} else if (webItem.sourceName.toLowerCase().indexOf("vimeo.com") != -1) {
		html = html + generateVimeoEmbedCode(webItem.sourceURL);
	} else if (webItem.image != null) {
		html = html + "<img src='webitem/image/" + webItem.id + "'></img>";
	}
	html = html + "</div>";

	return html;
}

function generateYouTubeEmbedCode(sourceURL) {

	var videoId = sourceURL.substr(sourceURL.indexOf("v=") + 2, 11);

	var embedCode = "<iframe src='http://www.youtube.com/embed/" + videoId
			+ "?wmode=transparent' frameborder='0' allowfullscreen></iframe>";
	return embedCode;
}

function generateVimeoEmbedCode(sourceURL) {

	var videoIdLength = sourceURL.length - sourceURL.indexOf("vimeo.com/" + 10);

	var videoId = sourceURL.substr(sourceURL.indexOf("vimeo.com/") + 10,
			videoIdLength);

	var embedCode = "<iframe src='http://player.vimeo.com/video/"
			+ videoId
			+ "?title=0&amp;byline=0&amp;portrait=0' frameborder='0' webkitAllowFullScreen"
			+ " mozallowfullscreen allowFullScreen></iframe> ";
	return embedCode;
}

function addFeedbackOptionsToCaption(feedbackType, webItemId) {

	var html = "<div class='voteTable'><table cellspacing='0'  cellpadding='0'><tr>";
	var feedbackOption;

	for (i in feedbackType.feedbackOptions) {
		feedbackOption = feedbackType.feedbackOptions[i];
		html = html + "<td class='feedbackOption'><img oid='"
				+ feedbackOption.id + "' src='/resources/"
				+ feedbackOption.iconPath + "' webItemId='" + webItemId
				+ "'></img></td>";
	}

	html = html + '</tr><tr>';

	for (i in feedbackType.feedbackOptions) {
		feedbackOption = feedbackType.feedbackOptions[i];
		html = html + "<td class='voteResults' position='"
				+ feedbackOption.position + "'></td>";
	}

	html = html + '</tr></table></div>';

	return html;
}

function openLink() {

	var webItemId = $(this).parent().attr('oid');

	$.post("/home/webItemOpened", {
		webItemId : webItemId
	}, function(data) {
	});

	var url = $(this).parent().attr('url');

	if (url == null) {
		url = $(this).parent().parent().attr('url');
	}

	window.open(url, '_blank');
	window.focus();
	return false;
}

function osm() {
	var webItemId = $(this).attr('webItemId');
	var feedbackOptionId = $(this).attr('feedbackOptionId');
	var thisWebItem = $(this);
	if ($(this).parent().find(".unmarked").length) {
		$.getJSON('/home/vote/webItem', {
			feedbackOptionId : feedbackOptionId,
			webItemId : webItemId
		}, function(data) {
			for ( var i in data) {
				var voteStat = data[i];
				thisWebItem.parent().append(renderMarked(voteStat));
				thisWebItem.parent().find(".unmarked").remove();
				thisWebItem.remove();
			}

		});
	}
}

function renderMarked(voteStat) {
	html = "<div class='marked'><p>" + voteStat.voteCount + "</p></div>";
	return html;

}
function viewMore() {

	$.currentRequest.page++;

	$.getJSON("/home/searchWebItems", {
		tagIdGroups : $.currentRequest.tagIdGroups,
		webItemTypeIds : $.currentRequest.webItemTypeIds,
		page : $.currentRequest.page
	}, function(data) {
		renderFeed(data);
	});
}

function addCorner(feedbackType, oid) {

	html = "<p class='osm'  webItemId='" + oid + "' feedbackOptionId='"
			+ feedbackType.feedbackOptions[0].id
			+ "'><img src='/resources/pics/home/star2.png' />"
			+ "</p><div class='unmarked'></div>";

	return html;
}

function rewindScroll () {
	$("html, body").animate({scrollTop : 0}, "slow");
}
