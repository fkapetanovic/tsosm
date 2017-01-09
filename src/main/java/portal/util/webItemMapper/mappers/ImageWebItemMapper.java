package portal.util.webItemMapper.mappers;

import java.net.URISyntaxException;

import portal.domain.impl.WebItemImage;

public class ImageWebItemMapper extends BasicWebItemMapper {

	public ImageWebItemMapper(String url) throws URISyntaxException {
		super(url);
		webItem.setImage(new WebItemImage());
		webItem.getImage().setSourceURL(url);
	}
}
