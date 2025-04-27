//Code link: https://github.com/anaygoyal09/BioHazardProject
//Arsh Abhinkar, Anay Goyal
//BiohazardMurderOfGeneBenidict.java
//4/12/25

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import java.awt.GridLayout;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;	
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class BiohazardMurderOfGeneBenidict
{
	public static void main(String[] args)
	{
		BiohazardMurderOfGeneBenidict bmogb = new BiohazardMurderOfGeneBenidict();
		bmogb.createFrame();
	}

	public void createFrame()
	{
		JFrame frame = new JFrame("Biohazard: The Murder of Gene Benidict");
		frame.setSize(1000, 800);				
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		BiohazardMurderOfGeneBenidictHolder bmogbh = new BiohazardMurderOfGeneBenidictHolder();
		frame.getContentPane().add(bmogbh);
		frame.setVisible(true);
	}
}

class BiohazardMurderOfGeneBenidictHolder extends JPanel
{
	public BiohazardMurderOfGeneBenidictHolder()
	{
		setBackground(Color.BLACK);
		CardLayout cards = new CardLayout();
		setLayout(cards);

		firstCard title = new firstCard(this, cards);
		secondCard report = new secondCard(this, cards);

		add(title, "title");
		add(report, "report");
	}

	class firstCard extends JPanel implements MouseListener, MouseMotionListener
	{
		Image policeLights, titleImage, startButton;
		Timer black, showTitle, fadeTimer, checkTime, hoverTimer;
		boolean changeBlack, showImage, startReady, entered, hoveringStart, clickingStart;
		float titleTransparency, startTransparency;
		int startX, startY, startW, startH, targetW, targetH;
		BiohazardMurderOfGeneBenidictHolder panelCards;
		CardLayout cards;
		double scale = 1.0;
		boolean pulseOut;
		double minScale = 0.95;
		double maxScale = 1.05;
		double hoverScale = 1.1;
		double clickScale = 0.9;
		double breathingSpeed = 0.003;
		double snapSpeed = 0.02;


		public firstCard(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn)
		{
			addMouseListener(this);
			addMouseMotionListener(this);
			panelCards = panelCardsIn;
			cards = cardsIn;
			hoveringStart = clickingStart = pulseOut = false;
			startX = 375;
			startY = 660;
			startW = 250;
			startH = 75;
			targetW = 250;
			targetH = 75;
			startHoverAnimation();
			retrieveImage();
			ImageIcon storeGif = new ImageIcon("policeLightsAni.gif");        
			policeLights = storeGif.getImage();
			blackScreen();
		}

		public void blackScreen()
		{
			timerBlackHandler tbh = new timerBlackHandler();
			showTitleTimerHandler stth = new showTitleTimerHandler();
			checkTimeTimerHandler ctth = new checkTimeTimerHandler();
			changeBlack = showImage = startReady = entered = false;
			titleTransparency = startTransparency = 0f;
			black = new Timer(3500, tbh);
			showTitle = new Timer(2000, stth);
			checkTime = new Timer(3000, ctth);
			black.start();
		}

		public void retrieveImage()
		{
			try
			{
				titleImage = ImageIO.read(new File("titlePicture.jpg"));
				startButton = ImageIO.read(new File("start.jpg"));
			}

			catch(IOException e)
			{
				System.err.println("\n\n\nERROR IN RETREIVING IMAGE\n\n\n");
				e.printStackTrace();
			}
		}

		public void startHoverAnimation()
		{
			hoverTimerHandler hth = new hoverTimerHandler();
			hoverTimer = new Timer(30, hth);
			hoverTimer.start();
		}

		class hoverTimerHandler implements ActionListener
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        double tolerance = 0.005;

		        if(!hoveringStart && !clickingStart)
		        {
		            if(pulseOut)
		            {
		                scale += breathingSpeed;
		                
		                if(scale >= maxScale)
		                
		                	pulseOut = false;
		            }
		            
		            else
		            {
		                scale -= breathingSpeed;
		                
		                if(scale <= minScale)
		                	pulseOut = true;
		            }
		        }

		        else if(hoveringStart && !clickingStart)
		        {
		            if(Math.abs(scale - hoverScale) > tolerance)
		                scale += (hoverScale - scale) * 0.1;

		            else
		                scale = hoverScale;
		        }

		        else if (clickingStart)
		        {
		            if(Math.abs(scale - clickScale) > tolerance)
		                scale += (clickScale - scale) * 0.1;
		            
		            else
		                scale = clickScale;
		        }

		        targetW = (int)(startW * scale);
		        targetH = (int)(startH * scale);

		        repaint();
		    }
		}

		class checkTimeTimerHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				startReady = true;
				titleTransparency = 0f;
				fadeTimer.restart();
				startFadeIn();
				repaint();
			}
		}

		class timerBlackHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				changeBlack = true;
				repaint();
				black.stop();
				showTitle.start();
			}
		}

		class showTitleTimerHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				showImage = true;
				repaint();
				showTitle.stop();
				startFadeIn();
			}
		}

		class fadeTimerHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!startReady)
				{
					titleTransparency += 0.05f;
					
					if(titleTransparency >= 1f)
					{
						titleTransparency = 1f;
						fadeTimer.stop();
						startReady = true;
						startFadeIn();
					}
				} 

				else
				{
					startTransparency += 0.05f;

					if(startTransparency >= 1f)
					{
						startTransparency = 1f;
						fadeTimer.stop();
					}
				}
				repaint();
			}
		}

		public void startFadeIn()
		{
			fadeTimerHandler fth = new fadeTimerHandler();
			fadeTimer = new Timer(150, fth);
			fadeTimer.start();
		}

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			g.drawImage(policeLights, -100, 0, 1300, 875, this);

			if(changeBlack)
				g.fillRect(0, 0, 1000, 800);

			if(showImage && changeBlack)
			{
				Graphics2D g2d = (Graphics2D)(g);

				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, titleTransparency));
				g2d.drawImage(titleImage, 100, 40, 800, 600, this);

				if(startReady)
				{
					int drawX = startX - ((targetW - startW) / 2);
					int drawY = startY - ((targetH - startH) / 2);

					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, startTransparency));
					g2d.drawImage(startButton, drawX, drawY, targetW, targetH, this);

					if(clickingStart)
					{
						g2d.setColor(new Color(0, 0, 0, 80));
						g2d.fillRect(drawX, drawY, targetW, targetH);
					}
				}
			}
		}

		public void mouseMoved(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= startX && x <= (startX + targetW) && y >= startY && y <= (startY + targetH))
				hoveringStart = true;

			else
				hoveringStart = false;
		}

		public void mousePressed(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= startX && x <= (startX + targetW) && y >= startY && y <= (startY + targetH))
			{
				clickingStart = true;
				repaint();
			}
		}

		public void mouseReleased(MouseEvent e)
		{
			clickingStart = false;
			repaint();
		}

		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if(x >= startX && x <= (startX + targetW) && y >= startY && y <= (startY + targetH))
				cards.show(panelCards, "report");
		}
		
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseDragged(MouseEvent e) {}
	}

	public class secondCard extends JPanel {
	    BiohazardMurderOfGeneBenidictHolder panelCards;
	    CardLayout cards;
	    private JButton startButton;

	    public secondCard(BiohazardMurderOfGeneBenidictHolder panelCardsIn, CardLayout cardsIn) {
	        panelCards = panelCardsIn;
	        cards = cardsIn;

	        // Main vertical panel for image and button
	        JPanel contentPanel = new JPanel();
	        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
	        contentPanel.setBackground(Color.WHITE);

	        // Load and scale the long image
	        ImageIcon imageIcon = new ImageIcon("longImage.jpg");
	        Image originalImage = imageIcon.getImage();
	        Image scaledImage = originalImage.getScaledInstance(800, 1000, Image.SCALE_SMOOTH); // <-- Adjust size here
	        ImageIcon scaledIcon = new ImageIcon(scaledImage);

	        JLabel imageLabel = new JLabel(scaledIcon);

	        // Wrapper to center image
	        JPanel imageWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
	        imageWrapper.setBackground(Color.WHITE);
	        imageWrapper.add(imageLabel);

	        contentPanel.add(imageWrapper);

	        // Scale start.jpg image
	        ImageIcon originalIcon = new ImageIcon("start.jpg");
	        Image startOriginalImage = originalIcon.getImage();
	        Image startScaledImage = startOriginalImage.getScaledInstance(120, 50, Image.SCALE_SMOOTH);
	        ImageIcon startScaledIcon = new ImageIcon(startScaledImage);

	        // Start button
	        startButton = new JButton(startScaledIcon);
	        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	        startButton.setBorderPainted(false);
	        startButton.setContentAreaFilled(false);
	        startButton.setFocusPainted(false);

	        startButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                cards.show(panelCards, "third"); // Replace with your actual next card name
	            }
	        });

	        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	        contentPanel.add(startButton);
	        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

	        JScrollPane scrollPane = new JScrollPane(contentPanel);
	        scrollPane.setPreferredSize(new Dimension(800, 600));

	        setLayout(new BorderLayout());
	        add(scrollPane, BorderLayout.CENTER);
	    }
	}
}

