package portal.util.webItemMapper;

import java.util.ArrayList;

import portal.domain.impl.WebItem;

public abstract class WebItemMapper {

	public WebItemMapper(String url) {
	}
	
	abstract public WebItem getWebItem();
	
	abstract public ArrayList<String> getImageUrls();
	
}
