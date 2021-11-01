package utils;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageComponent extends JPanel {
	private Image displayImage;
	
	public ImageComponent(Image image) {
		super();
		this.displayImage = image;
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(this.displayImage, 0, 0, null);
		super.paintComponents(g);
	}
}
