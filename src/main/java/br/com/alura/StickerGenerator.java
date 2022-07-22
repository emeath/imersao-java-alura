package br.com.alura;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;

public class StickerGenerator {

	public void create(InputStream inputStream, String stickerFileName, String stickerCaptionName) throws IOException {
		
		// reads image
		// get image from disk
		// InputStream inputStream = 
		//					new FileInputStream(new File("src/main/resources/input/movie.jpg"));
		// get image from web datastream
		// InputStream inputStream = 
		//					new URL("https://image.tmdb.org/t/p/w500/bnfTPTTytrZZ9Aw6hoOQdojiaKo.jpg").openStream();
		BufferedImage originalImage = ImageIO.read(inputStream);		
		
		// create new image in memory with transparency and new size
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		int newHeight = height + 200;
		BufferedImage newImage = new BufferedImage(width, newHeight, BufferedImage.TRANSLUCENT);
		
		// copies the original image to new image (in memory)
		Graphics2D graphics = (Graphics2D) newImage.getGraphics();
		graphics.drawImage(originalImage, 0, 0, null);
		
		// configure font
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 100);
		graphics.setFont(font);
		graphics.setColor(Color.YELLOW);
		
		// write phrase on new image
		String stickerLegend = stickerCaptionName;
		int stringWidth = graphics.getFontMetrics().stringWidth(stickerLegend);
		int centeringPosition = Math.round((width - stringWidth)/2);
		graphics.drawString(stickerLegend, centeringPosition, newHeight - 100);
		
		// Challange: put personal picture based on tv show rating
		if (Double.parseDouble(stickerCaptionName) >= 7) {
			InputStream inputStreamThumbsUp = new FileInputStream(new File("src/main/resources/input/thumbsUp.png"));
			BufferedImage thumbsUpImage = ImageIO.read(inputStreamThumbsUp);
			graphics.drawImage(thumbsUpImage, 250, newHeight - 250, null);
		} else if (Double.parseDouble(stickerCaptionName) <= 3.5) { 
			InputStream inputStreamThumbsDown = new FileInputStream(new File("src/main/resources/input/thumbsDown.png"));
			BufferedImage thumbsUpImage = ImageIO.read(inputStreamThumbsDown);
			graphics.drawImage(thumbsUpImage, 250, newHeight - 250, null);
		}
		
		// write new image on file
		File outputImageFile = new File("src/main/resources/output/" + stickerFileName);
		// Create output dir if it doesn't exists
		String createdStickersFolderPath = outputImageFile.getParent();
		@SuppressWarnings("unused")
		boolean mkdir = new File(createdStickersFolderPath).mkdir();
		ImageIO.write(newImage, "png", outputImageFile);
		
	}
}
