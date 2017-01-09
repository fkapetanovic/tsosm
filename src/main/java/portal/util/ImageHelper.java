package portal.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageHelper {
	public static byte[] BufferedImageToByte(BufferedImage theImage) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 	byte[] imageInByte=  new byte[0];

	 	ImageIO.write(theImage, "png", baos);
	 	baos.flush();
	 	imageInByte = baos.toByteArray();
	 	baos.close();
	 	return imageInByte;
	 }
}
