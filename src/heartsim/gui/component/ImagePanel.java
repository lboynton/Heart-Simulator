/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.gui.component;

/**
 *
 * @author Lee Boynton
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel
{
    private BufferedImage image;

    public void setImage(String path)
    {
        try
        {
            image = ImageIO.read(new File(path));
            this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        }
        catch (Exception ex)
        {
            this.setPreferredSize(new Dimension(0, 0));
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        if(image != null)
        {
            g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), Color.white, null);
        }
    }
}

