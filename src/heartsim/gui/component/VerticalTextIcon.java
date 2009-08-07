/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.gui.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Creates a vertical text icon, for use in vertical jtabbedpanes. See
 *
 * <a href="http://www.jroller.com/santhosh/entry/adobe_like_tabbedpane_in_swing">
 * http://www.jroller.com/santhosh/entry/adobe_like_tabbedpane_in_swing</a>
 *
 * @author Santhosh Kumar
 */
public class VerticalTextIcon implements Icon, SwingConstants
{
    private Font font = UIManager.getFont("Label.font");
    private FontMetrics fm = new JLabel().getFontMetrics(font);
    private String text;
    private int width, height;
    private boolean clockwise;

    public VerticalTextIcon(String text, boolean clockwise)
    {
        this.text = text;
        width = SwingUtilities.computeStringWidth(fm, text);
        height = fm.getHeight();
        this.clockwise = clockwise;
    }

    public void paintIcon(Component c, Graphics g, int x, int y)
    {
        Graphics2D g2 = (Graphics2D) g;
        Font oldFont = g.getFont();
        Color oldColor = g.getColor();
        AffineTransform oldTransform = g2.getTransform();
        
        g.setFont(font);
        g.setColor(Color.black);
        fm = g2.getFontMetrics();

        if (clockwise)
        {
            g2.translate(x + getIconWidth(), y);
            g2.rotate(Math.PI / 2);
        }
        else
        {
            g2.translate(x, y + getIconHeight());
            g2.rotate(-Math.PI / 2);
        }
        g.drawString(text, 0, fm.getLeading() + fm.getAscent());

        g.setFont(oldFont);
        g.setColor(oldColor);
        g2.setTransform(oldTransform);
    }

    public int getIconWidth()
    {
        return height;
    }

    public int getIconHeight()
    {
        return width;
    }
}