package sockergroda.enums;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum Images {
	ICON_1024x1024("icons/1024x1024/sockergroda.png"),
	ICON_512x512("icons/512x512/sockergroda.png"),
	ICON_256x256("icons/256x256/sockergroda.png"),
	ICON_64x64("icons/64x64/sockergroda.png"),
	ICON_32x32("icons/32x32/sockergroda.png"),
	ICON_16x16("icons/16x16/sockergroda.png"),
	
	AD_SAMPLE("ad_sample.png"),
	PLUS_16x16("icons/16x16/plus.png"),
	MAGN_GLASS_16x16("icons/16x16/magnifying_glass.png"),
	INIT_BANNER("sockergroda_banner.png"),
	INIT_BACKGROUND("init_background.png"),
	
	KEY_16x16("icons/16x16/password.png"),
	REPOSITORY_16x16("icons/16x16/repository.png"),
	ISSUE_16x16("icons/16x16/bug.png"),
	CONFIG_16x16("icons/16x16/config.png"),
	RESET_CONFIG_16x16("icons/16x16/reset_config.png"),
	EXPORT_CONFIG_16x16("icons/16x16/export_config.png"),
	IMPORT_CONFIG_16x16("icons/16x16/import_config.png"),
	UPDATE_16x16("icons/16x16/update.png"),
	ABOUT_16x16("icons/16x16/about.png"),
	CREATE_16x16("icons/16x16/create.png"),
	INSPECT_16x16("icons/16x16/inspect.png"),
	FREETEXT_16x16("icons/16x16/freetext.png"),
	EXPIRATION_16x16("icons/16x16/expiration.png"),
	ID_16x16("icons/16x16/id.png"),
	TAG_16x16("icons/16x16/tag.png"),
	SAVED_SECRETS_16x16("icons/16x16/saved_secrets.png"),
	BELL_16x16("icons/16x16/bell.png"),
	COPY_16x16("icons/16x16/copy.png"),
	UPDATE_SUCCESSFUL_16x16("icons/16x16/update_successful.png"),
	UPDATE_UNSUCCESSFUL_16x16("icons/16x16/update_unsuccessful.png"),
	CORRECT_16x16("icons/16x16/correct.png"),
	INCORRECT_16x16("icons/16x16/incorrect.png"),
	EYE_16x16("icons/16x16/eye.png"),
	TROUBLESHOOT_CONNECTION_16x16("icons/16x16/troubleshoot_connection.png"),
	DESTROY_16x16("icons/16x16/bomb.png"),
	DELETE_LOCALLY_16x16("icons/16x16/delete_locally.png"),
	RESTART_16x16("icons/16x16/restart.png"),
	MODIFY_API_SERVER_16x16("icons/16x16/modify_api_server.png"),
	MALICIOUS_16x16("icons/16x16/malicious.png"),
	MODERATOR_WAND_16x16("icons/16x16/moderator_wand.png"),
	REFRESH_16x16("icons/16x16/refresh.png"),
	GLOBE_16x16("icons/16x16/globe.png"),
	THUMBS_UP_16x16("icons/16x16/thumbs_up.png"),
	EXIT_16x16("icons/16x16/exit.png"),
	CHANGELOG_16x16("icons/16x16/changelog.png");
	
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
