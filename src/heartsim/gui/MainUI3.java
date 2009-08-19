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
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import javax.swing.SwingWorker;
import org.apache.batik.ext.swing.JAffineTransformChooser;
import org.apache.batik.ext.swing.JAffineTransformChooser.Dialog;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.gvt.InteractorAdapter;
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
    private int stimX = 250;
    private int stimY = 450;

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
        CAModel.stimulate(stimY, stimX);
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
                    if (u[i][j] == -1)
                    {
                        data[k] = -1;
                    }
                    // this pixel will be white
                    if (u[i][j] == 0)
                    {
                        data[k] = 1;
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
        toolbar = new javax.swing.JToolBar();
        btnViewCells = new javax.swing.JButton();
        btnStart = new javax.swing.JButton();
        btnTransform = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        progressBar.setMaximum(7);
        progressBar.setStringPainted(true);

        svgCanvas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                svgCanvasMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout svgCanvasLayout = new javax.swing.GroupLayout(svgCanvas);
        svgCanvas.setLayout(svgCanvasLayout);
        svgCanvasLayout.setHorizontalGroup(
            svgCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 585, Short.MAX_VALUE)
        );
        svgCanvasLayout.setVerticalGroup(
            svgCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 375, Short.MAX_VALUE)
        );

        lblStatus.setText("Status");

        toolbar.setRollover(true);

        btnViewCells.setText("Vew cells");
        btnViewCells.setFocusable(false);
        btnViewCells.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnViewCells.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnViewCells.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewCellsActionPerformed(evt);
            }
        });
        toolbar.add(btnViewCells);

        btnStart.setText("Run simulation");
        btnStart.setFocusable(false);
        btnStart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });
        toolbar.add(btnStart);

        btnTransform.setText("Transform");
        btnTransform.setFocusable(false);
        btnTransform.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTransform.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTransform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransformActionPerformed(evt);
            }
        });
        toolbar.add(btnTransform);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(svgCanvas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lblStatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 393, Short.MAX_VALUE)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnViewCellsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnViewCellsActionPerformed
    {//GEN-HEADEREND:event_btnViewCellsActionPerformed
        new CellsViewer(cellGenerator.getCells()).setVisible(true);
    }//GEN-LAST:event_btnViewCellsActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnStartActionPerformed
    {//GEN-HEADEREND:event_btnStartActionPerformed
        VisualisationSwingWorker worker = new VisualisationSwingWorker();
        worker.execute();
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnTransformActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnTransformActionPerformed
    {//GEN-HEADEREND:event_btnTransformActionPerformed
        Dialog transformDialog = JAffineTransformChooser.createDialog(this, "Transform");

        AffineTransform txf = transformDialog.showDialog();
        if (txf != null)
        {
            AffineTransform at = svgCanvas.getRenderingTransform();
            if (at == null)
            {
                at = new AffineTransform();
            }

            txf.concatenate(at);
            svgCanvas.setRenderingTransform(txf);
            generatorWorker = new CellGeneratorWorker();
            generatorWorker.execute();
        }
    }//GEN-LAST:event_btnTransformActionPerformed

    private void svgCanvasMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_svgCanvasMouseClicked
    {//GEN-HEADEREND:event_svgCanvasMouseClicked
        stimX = evt.getX();
        stimY = evt.getY();
        btnStartActionPerformed(null);
    }//GEN-LAST:event_svgCanvasMouseClicked

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
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnTransform;
    private javax.swing.JButton btnViewCells;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JProgressBar progressBar;
    private org.apache.batik.swing.JSVGCanvas svgCanvas;
    private javax.swing.JToolBar toolbar;
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
