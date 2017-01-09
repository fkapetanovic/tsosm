package portal.util.webItemMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.http.MediaType;

import portal.util.webItemMapper.mappers.BasicWebItemMapper;
import portal.util.webItemMapper.mappers.GeneralHtmlWebItemMapper;
import portal.util.webItemMapper.mappers.ImageWebItemMapper;
import portal.util.webItemMapper.mappers.YouTubeMapper;

public class WebItemMapperFactory {
	public static WebItemMapper getMapper(String urlString) throws IOException,
			URISyntaxException {

		MediaType mediaType = getMImeTypeFromUri(urlString);

		if (mediaType.compareTo(MediaType.TEXT_HTML) >= 0) {
			URL url = new URL(urlString);
			if (url.getHost().contains("youtube.com")) {
				return new YouTubeMapper(urlString);
			} else {
				return new GeneralHtmlWebItemMapper(urlString);
			}
		} else if (mediaType.compareTo(MediaType.IMAGE_GIF) >= 0
				|| mediaType.compareTo(MediaType.IMAGE_JPEG) >= 0
				|| mediaType.compareTo(MediaType.IMAGE_PNG) >= 0) {
			return new ImageWebItemMapper(urlString);
		} else {
			return new BasicWebItemMapper(urlString);
		}
	}

	private static MediaType getMImeTypeFromUri(String uri) throws IOException {
		URL url = new URL(uri);
		URLConnection uc = null;

		uc = url.openConnection();
		String mimeType = uc.getContentType();

		if (mimeType == null) {
			mimeType = URLConnection.guessContentTypeFromName(url.getFile());
		}

		return MediaType.valueOf(mimeType);
	}
}
