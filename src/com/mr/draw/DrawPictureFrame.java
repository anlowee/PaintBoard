package com.mr.draw;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import com.mr.util.*;

/*
 * Draw the main frame
 */
@SuppressWarnings("serial")
public class DrawPictureFrame extends JFrame implements FrameGetShape
{
	/*
	 * create a image colored by 8bits BGR
	 */
	BufferedImage image = new BufferedImage(570, 390, BufferedImage.TYPE_INT_BGR);
	Graphics gs = image.getGraphics();
	Graphics2D g = (Graphics2D) gs;
	DrawPictureCanvas canvas = new DrawPictureCanvas();
	Color foreColor = Color.BLACK;
	Color backgroundColor = Color.WHITE;
	/*
	 * mouse event proceed progress
	 */
	int x = -1;
	int y = -1;
	boolean rubber = false;
	/*
	 * create tool bar
	 */
	private JToolBar toolBar;
	private JButton eraserButton;
	private JToggleButton strokeButton1;
	private JToggleButton strokeButton2;
	private JToggleButton strokeButton3;
	private JButton backgroundButton;
	private JButton foregroundButton;
	private JButton clearButton;
	private JButton saveButton;
	private JButton shapeButton;
	/*
	 * Initialize draw shape
	 */
	boolean drawShape = false;
	Shapes shape;
	/*
	 * Initialize menu
	 */
	private JMenuItem strokeMenuItem1;
	private JMenuItem strokeMenuItem2;
	private JMenuItem strokeMenuItem3;
	private JMenuItem clearMenuItem;
	private JMenuItem foregroundMenuItem;
	private JMenuItem backgroundMenuItem;
	private JMenuItem eraserMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem saveMenuItem;
	/*
	 * Initialize water mark
	 */
	private JMenuItem watermarkMenuItem;
	private String watermark = "";
	
	/*
	 * proceed progress
	 */
	private void init()
	{
		/*
		 * set canvas and foreground
		 */
		g.setColor(backgroundColor);
		g.fillRect(0, 0, 570, 390);
		g.setColor(foreColor);
		canvas.setImage(image);
		getContentPane().add(canvas);

		/*
		 * set tool bar position
		 */
		toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		/*
		 * initialize tool bar's button
		 */
		saveButton = new JButton();
		saveButton.setToolTipText("Save");
		saveButton.setIcon(new ImageIcon("src/img/icon/save.png"));
		toolBar.add(saveButton);
		toolBar.addSeparator();

		strokeButton1 = new JToggleButton();
		strokeButton1.setToolTipText("Slim");
		strokeButton1.setIcon(new ImageIcon("src/img/icon/slim.png"));
		// initialize as slim line
		strokeButton1.setSelected(true);
		toolBar.add(strokeButton1);

		strokeButton2 = new JToggleButton();
		strokeButton2.setToolTipText("Bold");
		strokeButton2.setIcon(new ImageIcon("src/img/icon/bold.png"));
		toolBar.add(strokeButton2);

		strokeButton3 = new JToggleButton();
		strokeButton3.setToolTipText("Coarse");
		strokeButton3.setIcon(new ImageIcon("src/img/icon/coarse.png"));
		toolBar.add(strokeButton3);
		toolBar.addSeparator();
		// promise that there's at most one button selected
		ButtonGroup strokeGroup = new ButtonGroup();
		strokeGroup.add(strokeButton1);
		strokeGroup.add(strokeButton2);
		strokeGroup.add(strokeButton3);

		backgroundButton = new JButton();
		backgroundButton.setToolTipText("BGC");
		backgroundButton.setIcon(new ImageIcon("src/img/icon/bgc.png"));
		toolBar.add(backgroundButton);
		
		foregroundButton = new JButton();
		foregroundButton.setToolTipText("FGC");
		foregroundButton.setIcon(new ImageIcon("src/img/icon/fgc.png"));
		toolBar.add(foregroundButton);
		toolBar.addSeparator();

		shapeButton = new JButton();
		shapeButton.setToolTipText("Shape");
		shapeButton.setIcon(new ImageIcon("src/img/icon/shape.png"));
		toolBar.add(shapeButton);
		toolBar.addSeparator();

		clearButton = new JButton();
		clearButton.setToolTipText("Clear");
		clearButton.setIcon(new ImageIcon("src/img/icon/clear.png"));
		toolBar.add(clearButton);
		
		eraserButton = new JButton();
		eraserButton.setToolTipText("Eraser");
		eraserButton.setIcon(new ImageIcon("src/img/icon/eraser.png"));
		toolBar.add(eraserButton);

		/*
		 * Initialize menu button
		 */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// system
		JMenu systemMenu = new JMenu("System");
		menuBar.add(systemMenu);

		saveMenuItem = new JMenuItem("Save");
		systemMenu.add(saveMenuItem);
		systemMenu.addSeparator();
		exitMenuItem = new JMenuItem("Exit");
		systemMenu.add(exitMenuItem);
		systemMenu.addSeparator();
		watermarkMenuItem = new JMenuItem("Set WM");
		systemMenu.add(watermarkMenuItem);

		// stroke
		JMenu strokeMenu = new JMenu("Stroke");
		menuBar.add(strokeMenu);

		strokeMenuItem1 = new JMenuItem("Slim");
		strokeMenu.add(strokeMenuItem1);
		strokeMenuItem2 = new JMenuItem("Bold");
		strokeMenu.add(strokeMenuItem2);
		strokeMenuItem3 = new JMenuItem("Coarse");
		strokeMenu.add(strokeMenuItem3);

		// color
		JMenu colorMenu = new JMenu("Color");
		menuBar.add(colorMenu);

		foregroundMenuItem = new JMenuItem("FGC");
		colorMenu.add(foregroundMenuItem);
		backgroundMenuItem = new JMenuItem("BGC");
		colorMenu.add(backgroundMenuItem);

		// edit
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);

		eraserMenuItem = new JMenuItem("Eraser");
		editMenu.add(eraserMenuItem);
		clearMenuItem = new JMenuItem("Clear");
		editMenu.add(clearMenuItem);
	}

	/*
	 * add mouse listener
	 */
	private void addListener()
	{
		/*
		 *  add mouse listener for canvas
		 */
		canvas.addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(final MouseEvent e)
			{
				if (x > 0 && y > 0)
				{
					if (rubber)
					{
						g.setColor(backgroundColor);
						g.fillRect(x, y, 10, 10);
					} else
					{
						g.drawLine(x, y, e.getX(), e.getY());
					}
				}
				x = e.getX();
				y = e.getY();
				canvas.repaint();
			}
			
			public void mouseMoved(final MouseEvent e)
			{
				if (rubber)
				{
					//set cursor's form
					Toolkit kit = Toolkit.getDefaultToolkit();
					Image img = kit.createImage("src/img/icon/cursor_eraser.png");
					
					//create a eraser cursor
					Cursor c = kit.createCustomCursor(img, new Point(0, 0), "eraser");
					setCursor(c);
				} else 
				{
					setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				}
			}
		});
		
		/*
		 * add tool bar's mouse motion listener
		 */
		toolBar.addMouseMotionListener(new MouseMotionAdapter() 
		{
			public void mouseMoved(final MouseEvent e)
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		
		canvas.addMouseListener(new MouseAdapter()
		{
			public void mouseReleased(final MouseEvent e)
			{
				/*
				 * recover the position
				 */
				x = -1;
				y = -1;
			}
			
			public void mousePressed(MouseEvent e)
			{
				if (drawShape)
				{
					switch (shape.getType())
					{
					case Shapes.YUAN:
						int yuanX = e.getX() - shape.getWidth() / 2;
						int yuanY = e.getY() - shape.getHeigth() / 2;
						
						Ellipse2D yuan = new Ellipse2D.Double(yuanX, yuanY, shape.getWidth(), shape.getHeigth());
						g.draw(yuan);
						break;
					case Shapes.FANG:
						int fangX = e.getX() - shape.getWidth() / 2;
						int fangY = e.getY() - shape.getHeigth() / 2;
						
						Rectangle2D fang = new Rectangle2D.Double(fangX, fangY, shape.getWidth(), shape.getHeigth());
						g.draw(fang);
						break;
					}
					canvas.repaint();
					drawShape = false;
				}
			}
		});

		/*
		 * add button clicked event
		 * 
		 * Tool bar's button event
		 * 
		 * strokeButton event
		 */
		strokeButton1.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs);
			}
		});
		
		strokeButton2.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				BasicStroke bs = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs);
			}
		});
		
		strokeButton3.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent e)
			{
				BasicStroke bs = new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs);
			}
		});
		
		/*
		 * BGC&FGC button event
		 */
		backgroundButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent e)
			{
				Color bgColor = JColorChooser.showDialog(DrawPictureFrame.this, "Color Chooser", Color.CYAN);
				if (bgColor != null)
				{
					backgroundColor = bgColor;
				}
				//instead the button's color with chosen color
				backgroundButton.setBackground(backgroundColor);
				g.setColor(backgroundColor);
				g.fillRect(0, 0, 570, 390);
				g.setColor(foreColor);
				canvas.repaint();
			}
		});
		
		foregroundButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent e)
			{
				Color fColor = JColorChooser.showDialog(DrawPictureFrame.this, "Color Chooser", Color.CYAN);
				if (fColor != null)
				{
					foreColor = fColor;
				}
				//instead the button's color with chosen color
				foregroundButton.setBackground(foreColor);
				g.setColor(foreColor);
			}
		});
		
		/*
		 * clear button event
		 */
		clearButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent e)
			{
				g.setColor(backgroundColor);
				g.fillRect(0, 0, 570, 390);
				g.setColor(foreColor);
				canvas.repaint();
			}
		});
		
		/*
		 * eraser button event
		 */
		eraserButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent e)
			{
				if (eraserButton.getText().equals("Eraser"))
				{
					rubber = true;
					eraserButton.setText("Stroke");
				} else
				{
					rubber = false;
					eraserButton.setText("Eraser");
					g.setColor(foreColor);
				}
			}
		});
		
		/*
		 * shape button event
		 */
		shapeButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent e)
			{
				ShapeWindow shapeWindow = new ShapeWindow(DrawPictureFrame.this);
				int shapeButtonWidth = shapeButton.getWidth();
				int shapeWindowWidth = shapeWindow.getWidth();
				int shapeButtonX = shapeButton.getX();
				int shapeButtonY = shapeButton.getY();
				
				int shapeWindowX = getX() + shapeButtonX - (shapeWindowWidth - shapeButtonWidth) / 2;
				int shapeWindowY = getY() + shapeButtonY + 80;
				
				shapeWindow.setLocation(shapeWindowX, shapeWindowY);
				shapeWindow.setVisible(true);
			}
		});
		
		/*
		 * Save button event
		 */
		saveButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent e)
			{
				addWatermark();
				DrawImageUtil.saveImage(DrawPictureFrame.this, image);
			}
		});
		
		/*
		 * Menu bar's button event
		 *
		 * stroke button event
		 */
		strokeMenuItem1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent e)
			{
				BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs);
				strokeButton1.setSelected(true);
			}
		});
		
		strokeMenuItem2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent e)
			{
				BasicStroke bs =new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs);
				strokeButton2.setSelected(true);
			}
		});
		
		strokeMenuItem3.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				BasicStroke bs = new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs);
				strokeButton3.setSelected(true);
			}
		});
		
		/*
		 * exit button event
		 */
		exitMenuItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		/*
		 * eraser button event
		 */
		eraserMenuItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent e)
			{
				if (eraserButton.getText().equals("Eraser"))
				{
					rubber = true;
					eraserButton.setText("Stroke");
					eraserMenuItem.setText("Stroke");
				} else
				{
					rubber = false;
					eraserButton.setText("Eraser");
					eraserMenuItem.setText("Eraser");
					g.setColor(foreColor);
				}
			}
		});
		
		/*
		 * clear button event
		 */
		clearMenuItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent e)
			{
				g.setColor(backgroundColor);
				g.fillRect(0, 0, 570, 390);
				g.setColor(foreColor);
				canvas.repaint();
			}
		});
		
		/*
		 * FGC button event
		 */
		foregroundMenuItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent e)
			{
				Color fColor = JColorChooser.showDialog(DrawPictureFrame.this, "Color Chooser", Color.CYAN);
				if (fColor != null)
				{
					foreColor = fColor;
				}
				//instead the button's color with chosen color
				foregroundButton.setBackground(foreColor);
				g.setColor(foreColor);
			}
		});
		
		/*
		 * BGC button event
		 */
		backgroundMenuItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent e)
			{
				Color bgColor = JColorChooser.showDialog(DrawPictureFrame.this, "Color Chooser", Color.CYAN);
				if (bgColor != null)
				{
					backgroundColor = bgColor;
				}
				//instead the button's color with chosen color
				backgroundButton.setBackground(backgroundColor);
				g.setColor(backgroundColor);
				g.fillRect(0, 0, 570, 390);
				g.setColor(foreColor);
				canvas.repaint();
			}	
		});
		
		/*
		 * save button event
		 */
		saveMenuItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent e)
			{
				addWatermark();
				DrawImageUtil.saveImage(DrawPictureFrame.this, image);
			}
		});
		
		/*
		 * watermark button event
		 */
		watermarkMenuItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent e)
			{
				watermark = JOptionPane.showInputDialog(DrawPictureFrame.this, "What's your watermark?");
				if (null == watermark)
				{
					watermark = "";
				} else 
				{
					setTitle("painting----with watermark: " + watermark);
				}
			}
		});
	}
	
	/*
	 * realize the implement
	 */
	public void getShape(Shapes shape)
	{
		this.shape = shape;
		drawShape = true;
	}

	/*
	 * construct method
	 */
	public DrawPictureFrame()
	{
		setResizable(false);
		setTitle("painting----with watermark: " + watermark);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 574, 460);

		init();
		addListener();
	}

	/*
	 * main running method
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		DrawPictureFrame frame = new DrawPictureFrame();
		frame.setVisible(true);
	}
	
	public void addWatermark() 
	{
		if (!"".equals(watermark.trim()))
		{
			g.rotate(Math.toRadians(-30));
			Font font = new Font("Times New Roman", Font.BOLD, 72);
			g.setFont(font);
			g.setColor(Color.GRAY);
			AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.4f);
			g.setComposite(alpha);
			g.drawString(watermark, 150, 500);
			canvas.repaint();
			
			g.rotate(Math.toRadians(30));
			alpha = AlphaComposite.SrcOver.derive(1f);
			g.setComposite(alpha);
			g.setColor(foreColor);
		}
	}
}
