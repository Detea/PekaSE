import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FramesDialog extends JDialog {
	private int y = 16;
	
	private BufferedImage buffer;
	
	public FramesDialog(PK2Sprite sprFile) {
		int h = 32;
	
		if (sprFile != null) {
			h = sprFile.frameHeight + 16;
			
			int fx = sprFile.frameX, fy = sprFile.frameY;
		    for (int i = 0; i < sprFile.frames; i++) {
		    	if (fx + sprFile.frameWidth > 640 - 64) {
		    		fy += (sprFile.frameHeight * 2) + 24;
		    		fx = sprFile.frameX;
		    	}
		    	
		    	fx += sprFile.frameWidth + 3;
			}
			
		    h += fy + sprFile.frameHeight + 32;
		    
			buffer = new BufferedImage(640 - 64, h, BufferedImage.TYPE_INT_ARGB);
			
			Graphics g = buffer.getGraphics();
			
			fx = 0;
			fy = 0;
			for (int i = 0; i < sprFile.frames; i++) {
		    	if (fx + sprFile.frameWidth > 640 - 64) {
		    		fy += sprFile.frameHeight + 32;
		    		fx = sprFile.frameX;
		    	}
		    	
		    	g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 14));
				
				g.drawString("" + (i + 1), (fx + (sprFile.frameWidth / 2)) - 8, fy + 24);
		    	g.drawImage(sprFile.frameList[i], fx, fy + 24, null);
		    	
		    	fx += sprFile.frameWidth + 3;
		    }
		}
		
		JPanel p = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g.drawImage(buffer, 0, 0, null);
			}
		};
		
		p.setPreferredSize(new Dimension(640, h));
		
		JScrollPane sp = new JScrollPane(p, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(640, h));
		
		p.setBackground(Color.GRAY);
		
		add(sp);
		
		if (h > 500) {
			h = 500;
		}
		
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource("/pekase.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		setIconImage(img);
		
		setSize(640, h);
		setTitle("Frames");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
