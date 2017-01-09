package portal.util.webItemMapper.mappers;

import java.io.IOException;
import java.net.URISyntaxException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import portal.service.WebItemTypeService;

public class YouTubeMapper extends BasicWebItemMapper {
	public YouTubeMapper(String url) throws URISyntaxException {
		super(url);
		Document doc = null;

		try {
			 doc = Jsoup.connect(url).userAgent("Mozilla").get();
		}
		catch (IOException ex)
		{
			this.webItem=null;
			return;
		}

		this.webItem.setTitle(doc.title());
		this.webItem.setText(doc.getElementById("eow-description").text());
	}
}
