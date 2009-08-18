/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.Overlay;

/**
 *
 * @author Lee Boynton
 */
public class BinaryPlotPanelOverlay implements Overlay
{
    private BufferedImage buffIm;
    private int[] buffer;
    private JSVGCanvas canvas;

    public BinaryPlotPanelOverlay(int width, int height, JSVGCanvas canvas)
    {
        this.canvas = canvas;
        buffIm = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster rasta = buffIm.getRaster();
        DataBufferInt buf =  (DataBufferInt) rasta.getDataBuffer();
        buffer = buf.getData();
    }

    public int[] getBuffer()
    {
        return buffer;
    }

    public Graphics2D getBufferGraphics()
    {
        return buffIm.createGraphics();
    }
    
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(AlphaComposite.Src);
        g2.drawImage(buffIm, 0, 0, canvas);
    }
}
