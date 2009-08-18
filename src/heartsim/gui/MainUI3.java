/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainUI3.java
 *
 * Created on 18-Aug-2009, 08:44:11
 */
package heartsim.gui;

import heartsim.CellGenerator;
import heartsim.CellGeneratorListener;
import heartsim.ca.CAModel;
import heartsim.ca.Nishiyama;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.apache.batik.swing.svg.JSVGComponent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;

/**
 *
 * @author Lee Boynton
 */
public class MainUI3 extends javax.swing.JFrame implements CellGeneratorListener
{
    private CellGenerator cellGenerator;
    private CAModel CAModel = new Nishiyama();
    private CellGeneratorWorker generatorWorker;

    /** Creates new form MainUI3 */
    public MainUI3()
    {
        initComponents();
        // Set the JSVGCanvas listeners.
        svgCanvas.addSVGDocumentLoaderListener(new SVGDocumentLoaderAdapter()
        {
            public void documentLoadingStarted(SVGDocumentLoaderEvent e)
            {
                setStatusText("Document loading...");
                incrementProgressBar();
            }

            public void documentLoadingCompleted(SVGDocumentLoaderEvent e)
            {
                setStatusText("Document loaded");
                incrementProgressBar();
            }
        });

        svgCanvas.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter()
        {
            public void gvtBuildStarted(GVTTreeBuilderEvent e)
            {
                setStatusText("Build started...");
                incrementProgressBar();
            }

            public void gvtBuildCompleted(GVTTreeBuilderEvent e)
            {
                setStatusText("Build completed");
                incrementProgressBar();
            }
        });

        svgCanvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter()
        {
            public void gvtRenderingPrepare(GVTTreeRendererEvent e)
            {
                lblStatus.setText("Rendering started...");
                incrementProgressBar();
            }

            public void gvtRenderingCompleted(GVTTreeRendererEvent e)
            {
                lblStatus.setText("Rendering completed");
                incrementProgressBar();
                generatorWorker = new CellGeneratorWorker();
                generatorWorker.execute();
            }
        });

        cellGenerator = new CellGenerator(svgCanvas);
        cellGenerator.addGeneratorListener(this);
        cellGenerator.addPath("ventricles");
        cellGenerator.addPath("atria");
        cellGenerator.addPath("sanode");
        cellGenerator.addPath("avnode");

        loadSVG("./geometry_data/heart4.svg");
    }

    public void loadSVG(String path)
    {
        String uri = new File(path).toURI().toString();

        // this ensures an update manager is created
        svgCanvas.setDocumentState(JSVGComponent.ALWAYS_DYNAMIC);

        svgCanvas.setURI(uri);
    }

    public void incrementProgressBar()
    {
        progressBar.setValue(progressBar.getValue() + 1);
    }

    public void resetProgressBar()
    {
        progressBar.setValue(0);
    }

    public void setStatusText(String text)
    {
        lblStatus.setText(text);
    }

    public void runSimulation()
    {
        CAModel.setCells(cellGenerator.getCells());
        CAModel.setSize(new Dimension(cellGenerator.getCells()[0].length, cellGenerator.getCells().length));
        CAModel.initCells();
        CAModel.stimulate(400, 250);
        BinaryPlotPanelOverlay overlay = new BinaryPlotPanelOverlay(cellGenerator.getCells()[0].length, cellGenerator.getCells().length, svgCanvas);
        svgCanvas.getOverlays().add(overlay);

        int[] data = overlay.getBuffer();

        int[][] u = CAModel.getU();

        for (int t = 0; t < 2000; t++)
        {
            int k = 0;

            for (int i = 0; i < u.length; i++)
            {
                for (int j = 0; j < u[0].length; j++)
                {
                    // this pixel will be white
                    if (u[i][j] == 0)
                    {
                        //data[k] = new Color(0,0,0,0).;
                        //data[k] =
                    }
                    // blue
                    if (u[i][j] == 1)
                    {
                        data[k] = 255;
                    }
                    // green
                    if (u[i][j] == 2)
                    {
                        data[k] = 65280;
                    }
                    // yellow
                    if (u[i][j] == 3)
                    {
                        data[k] = 16776960;
                    }
                    // orange
                    if (u[i][j] == 4)
                    {
                        data[k] = 14251783;
                    }
                    // red
                    if (u[i][j] == 5)
                    {
                        data[k] = 16711680;
                    }
                    k++;
                }
            }

            CAModel.step();
            svgCanvas.repaint();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progressBar = new javax.swing.JProgressBar();
        svgCanvas = new org.apache.batik.swing.JSVGCanvas();
        lblStatus = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        progressBar.setMaximum(7);
        progressBar.setStringPainted(true);

        javax.swing.GroupLayout svgCanvasLayout = new javax.swing.GroupLayout(svgCanvas);
        svgCanvas.setLayout(svgCanvasLayout);
        svgCanvasLayout.setHorizontalGroup(
            svgCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 581, Short.MAX_VALUE)
        );
        svgCanvasLayout.setVerticalGroup(
            svgCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 375, Short.MAX_VALUE)
        );

        lblStatus.setText("Status");

        jToolBar1.setRollover(true);

        jButton1.setText("Vew cells");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setText("Run simulation");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(svgCanvas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lblStatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 393, Short.MAX_VALUE)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(svgCanvas, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        new CellsViewer(cellGenerator.getCells()).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
    {//GEN-HEADEREND:event_jButton2ActionPerformed
        VisualisationSwingWorker worker = new VisualisationSwingWorker();
        worker.execute();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new MainUI3().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JProgressBar progressBar;
    private org.apache.batik.swing.JSVGCanvas svgCanvas;
    // End of variables declaration//GEN-END:variables

    public void cellGenerationStarted()
    {
        setStatusText("Generating cells for (this may take a while)...");
    }

    public void cellGenerationCompleted()
    {
        progressBar.setValue(cellGenerator.getProgress());
        setStatusText("Cells generated");
    }

    public class CellGeneratorWorker extends SwingWorker
    {
        @Override
        protected Object doInBackground() throws Exception
        {
            progressBar.setMaximum(100);
            resetProgressBar();
            Thread thread = new Thread(cellGenerator);
            thread.start();
            while (!cellGenerator.isCompleted())
            {
                progressBar.setValue(cellGenerator.getProgress());
                Thread.sleep(1000);
            }
            return null;
        }
    }

    public class VisualisationSwingWorker extends SwingWorker<Object, Void>
    {
        @Override
        public Object doInBackground() throws Exception
        {
            runSimulation();

            return null;
        }
    }
}
