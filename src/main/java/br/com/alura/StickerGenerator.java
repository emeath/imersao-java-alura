package br.com.alura;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StickerGenerator {

	public void create() throws IOException {
		
		// reads image
		BufferedImage originalImage = ImageIO.read(new File("src/main/resources/input/movie.jpg"));		
		
		// create new image in memory with transparency and new size
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		int newHeight = height + 200;
		BufferedImage newImage = new BufferedImage(width, newHeight, BufferedImage.TRANSLUCENT);
		
		// copies the original image to new image (in memory)
		Graphics2D graphics = (Graphics2D) newImage.getGraphics();
		graphics.drawImage(originalImage, 0, 0, null);
		
		// write new image on file
		File outputImageFile = new File("src/main/resources/output/movie-sticker.png");
		// Create output dir if it doesn't exists
		String createdStickersFolderPath = outputImageFile.getParent();
		boolean mkdir = new File(createdStickersFolderPath).mkdir();
		ImageIO.write(newImage, "png", outputImageFile);
		
	}
	
	public static void main(String[] args) throws IOException {
		var generator = new StickerGenerator();
		generator.create();
	}
}
