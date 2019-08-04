package com.mr.draw;

import java.awt.*;

/*
 * simple painting display frame
 */
@SuppressWarnings("serial")
public class DrawPictureCanvas extends Canvas
{
	/*
	 * create the image that display in the canvas
	 */
	private Image image = null;

	/*
	 * set the image displayed
	 *
	 * @param image
	 */
	public void setImage(Image image)
	{
		this.image = image;
	}

	@Override
	public void paint(Graphics g)
	{
		g.drawImage(image, 0, 0, null);
	}

	@Override
	public void update(Graphics g)
	{
		paint(g);
	}
}
