/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BinaryPlotPanel.java
 *
 * Created on 15-Jun-2009, 13:44:00
 */
package heartsim.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

/**
 *
 * @author Lee Boynton
 */
public class BinaryPlotPanel extends javax.swing.JPanel
{
    private BufferedImage buffIm;
    private byte[] buffer;

    /** Creates new form BinaryPlotPanel */
    public BinaryPlotPanel()
    {
        initComponents();
    }

    public BinaryPlotPanel(int x, int y)
    {
        buffIm = new BufferedImage(x, y, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster rasta = buffIm.getRaster();
        DataBufferByte buf = (DataBufferByte) rasta.getDataBuffer();
        buffer = buf.getData();
    }

    @Override
    public void setSize(Dimension d)
    {
        buffIm = new BufferedImage(d.width, d.height, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster rasta = buffIm.getRaster();
        DataBufferByte buf = (DataBufferByte) rasta.getDataBuffer();
        buffer = buf.getData();
        super.setSize(d);
    }

    public void reset()
    {
        for(int i = 0; i < buffer.length; i++)
        {
            buffer[i] = 0;
        }
        this.repaint();
    }

    public byte[] getBuffer()
    {
        return buffer;
    }

    public Graphics2D getBufferGraphics()
    {
        return buffIm.createGraphics();
    }

    public int getImageHeight()
    {
        return buffIm.getHeight();
    }

    public int getImageWidth()
    {
        return buffIm.getWidth();
    }

    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(buffIm, 0, 0, this);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}