package sockergroda.enums;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum Images {
	ICON_1024x1024("icons/1024x1024/sockergroda.png"),
	ICON_32x32("icons/32x32/sockergroda.png"),
	ICON_16x16("icons/16x16/sockergroda.png"),
	
	VALID_ICON_16x16("icons/16x16/valid.png"),
	INVALID_ICON_16x16("icons/16x16/invalid.png"),
	AD_SAMPLE("ad_sample.png"),
	PLUS_16x16("icons/16x16/plus.png"),
	MAGN_GLASS_16x16("icons/16x16/magnifying_glass.png"),
	
	KEY_16x16("icons/16x16/password.png"),
	REPOSITORY_16x16("icons/16x16/repository.png"),
	ISSUE_16x16("icons/16x16/bug.png"),
	RESET_CONFIG_16x16("icons/16x16/reset_config.png"),
	UPDATE_16x16("icons/16x16/update.png"),
	ABOUT_16x16("icons/16x16/about.png"),
	CREATE_16x16("icons/16x16/create.png"),
	INSPECT_16x16("icons/16x16/inspect.png"),
	FREETEXT_16x16("icons/16x16/freetext.png"),
	EXPIRATION_16x16("icons/16x16/expiration.png"),
	ID_16x16("icons/16x16/id.png");
	
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
