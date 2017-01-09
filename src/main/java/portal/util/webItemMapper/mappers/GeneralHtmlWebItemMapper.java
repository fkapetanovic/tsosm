package portal.util.webItemMapper.mappers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

public class GeneralHtmlWebItemMapper extends BasicWebItemMapper {
	private ArrayList<String> imageUrls=new ArrayList<String>();

	@Override
	public ArrayList<String> getImageUrls() {
		return imageUrls;
	}

	public GeneralHtmlWebItemMapper(String url) throws URISyntaxException {
		super(url);
		Document doc = null;

		try {
			 doc = Jsoup.connect(url).userAgent("Mozilla").get();
		}
		catch (IOException ex) {
			this.webItem=null;
			this.imageUrls=null;
			return;
		}

		this.webItem.setTitle(doc.title());

		getImageElements(url, doc);
		this.webItem.setText(parseFirstParagraph(doc));
	}

	private void getImageElements(String url, Document doc) throws URISyntaxException {
		Elements imageElements=doc.getElementsByTag("img");

		for(Element imageElement: imageElements) {
			String imageUrl=imageElement.absUrl("src");
			imageUrls.add(imageUrl);
		}
	}

	private String parseFirstParagraph(Document doc) {
		Element mainDiv=null;
		int maxNumberOfP=0;
		int currentNumberOfP=0;

		Elements divs = doc.getElementsByTag("div");

		for (Element div:divs) {
			currentNumberOfP=div.getElementsByTag("p").size();

			if (maxNumberOfP<currentNumberOfP) {
				maxNumberOfP=currentNumberOfP;
				mainDiv=div;
			}
		}

		if (mainDiv!=null) {
			return	mainDiv.getElementsByTag("p").first().text();
		} else {
			return "";
		}
	}
}
