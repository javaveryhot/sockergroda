package sockergroda.enums;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum Images {
	ICON_1024x1024("sockergroda_1024x1024.png"),
	ICON_32x32("sockergroda_32x32.png"),
	ICON_16x16("sockergroda_16x16.png"),
	VALID_ICON_16x16("valid_16x16.png"),
	INVALID_ICON_16x16("invalid_16x16.png"),
	AD_SAMPLE("ad_sample.png"),
	PLUS_16x16("plus_16x16.png"),
	MAGN_GLASS_16x16("magn_glass_16x16.png");
	
	Image image;
	private Images(String imageLocation) {
		try {
			this.image = ImageIO.read(getClass().getResource("/resources/" + imageLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Image getImage() {
		return this.image;
	}
}
