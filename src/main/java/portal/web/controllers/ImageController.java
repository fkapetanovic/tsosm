package portal.web.controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import portal.domain.impl.WebItemImage;
import portal.service.WebItemService;

@Controller
@RequestMapping("/image")
public class ImageController {
	@Autowired
  private WebItemService webItemService;

  @RequestMapping(method = RequestMethod.GET, value ="/{id}")
	public ResponseEntity<byte[]>  getWebItemImage(@PathVariable int id) throws IOException {
		WebItemImage webItemImage =webItemService.getWebItemImageById(id);
		BufferedImage theImage=webItemImage.getImage();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(webItemImage.getMimeType()));

		return new ResponseEntity<byte[]>(
			portal.util.ImageHelper.BufferedImageToByte(theImage),
			headers,
			HttpStatus.CREATED
		);
	}
}
