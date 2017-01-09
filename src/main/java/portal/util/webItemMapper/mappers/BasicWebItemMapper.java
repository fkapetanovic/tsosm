package portal.util.webItemMapper.mappers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import portal.domain.impl.WebItem;
import portal.util.webItemMapper.WebItemMapper;

public class BasicWebItemMapper extends WebItemMapper {
	protected WebItem webItem;

	public BasicWebItemMapper(String url) throws URISyntaxException {
		super(url);
	 	webItem= new WebItem();
		webItem.setSourceName(getDomainName(url));
		webItem.setSourceURL(url);
	}

	public WebItem getWebItem() {
		return webItem;
	}

	public  ArrayList<String> getImageUrls() {
		return new ArrayList<String>();
	}

	protected  String getDomainName(String url) throws URISyntaxException {
	    URI uri = new URI(url);
	    String domain = uri.getHost();
	    return domain;
	}
}
