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

import heartsim.Application;
import heartsim.CellGenerator;
import heartsim.CellGeneratorListener;
import heartsim.Simulator;
import heartsim.SimulatorListener;
import heartsim.ca.CAModel;
import heartsim.ca.Nishiyama;
import heartsim.gui.util.FileChooserFilter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import org.apache.batik.ext.swing.JAffineTransformChooser;
import org.apache.batik.ext.swing.JAffineTransformChooser.Dialog;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.gvt.GVTTreeRendererListener;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderListener;
import org.apache.batik.swing.svg.JSVGComponent;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderListener;

/**
 *
 * @author Lee Boynton
 */
public class MainUI3 extends javax.swing.JFrame implements CellGeneratorListener,
        SVGDocumentLoaderListener, GVTTreeBuilderListener, GVTTreeRendererListener,
        SimulatorListener
{
    private CellGenerator cellGenerator;
    private CAModel caModel = new Nishiyama();
    private CellGeneratorWorker generatorWorker;
    private int stimRow = 250;
    private int stimCol = 450;
    private int timeToRun = 0;
    private int currentTime = 0;
    private final BinaryPlotPanelOverlay overlay;
    private final Simulator simulation;

    /** Creates new form MainUI3 */
    public MainUI3()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {
            System.err.println("Unable to use system look and feel");
        }

        initComponents();

        // set the JSVGCanvas listeners.
        svgCanvas.addSVGDocumentLoaderListener(this);
        svgCanvas.addGVTTreeBuilderListener(this);
        svgCanvas.addGVTTreeRendererListener(this);

        // create the cell generator and initially tell it to try to load some
        // elements
        cellGenerator = new CellGenerator(svgCanvas, new String[]
                {
                    "ventricles", "atria", "sanode", "avnode"
                });

        // add listener so we know when it's finished generating the cells array
        cellGenerator.addGeneratorListener(this);

        overlay = new BinaryPlotPanelOverlay(svgCanvas);
        svgCanvas.getOverlays().add(overlay);

        simulation = new Simulator(caModel, overlay);
        simulation.addListener(this);

        // initially load an SVG file
        loadSVG("./geometry_data/heart4.svg");
    }

    public void loadSVG(String path)
    {
        loadSVG(new File(path));
    }

    public void loadSVG(File file)
    {
        String uri = file.toURI().toString();

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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toolbar = new javax.swing.JToolBar();
        btnOpen = new javax.swing.JButton();
        btnOpenSeparator = new javax.swing.JToolBar.Separator();
        btnStart = new javax.swing.JButton();
        btnPause = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnStepForward = new javax.swing.JButton();
        pnlRootContainer = new javax.swing.JPanel();
        progressBar = new javax.swing.JProgressBar();
        svgCanvas = new org.apache.batik.swing.JSVGCanvas();
        lblStatus = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuItmExit = new javax.swing.JMenuItem();
        mnuAdvanced = new javax.swing.JMenu();
        mnuItmTransform = new javax.swing.JMenuItem();
        mnuDebug = new javax.swing.JMenu();
        mnuItmVerboseOutput = new javax.swing.JCheckBoxMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        mnuItmViewCells = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        mnuItmPrintCells = new javax.swing.JMenuItem();
        mnuItmPrintArrays = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        toolbar.setFloatable(false);
        toolbar.setRollover(true);

        btnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/heartsim/gui/icon/document-open.png"))); // NOI18N
        btnOpen.setToolTipText("Open SVG file containing heart geometry");
        btnOpen.setFocusable(false);
        btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        toolbar.add(btnOpen);
        toolbar.add(btnOpenSeparator);

        btnStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/heartsim/gui/icon/media-playback-start.png"))); // NOI18N
        btnStart.setToolTipText("Run the simulation with the specified parameters");
        btnStart.setFocusable(false);
        btnStart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });
        toolbar.add(btnStart);

        btnPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/heartsim/gui/icon/media-playback-pause.png"))); // NOI18N
        btnPause.setToolTipText("Pauses the simulation");
        btnPause.setEnabled(false);
        btnPause.setFocusable(false);
        btnPause.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPause.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPauseActionPerformed(evt);
            }
        });
        toolbar.add(btnPause);

        btnStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/heartsim/gui/icon/media-playback-stop.png"))); // NOI18N
        btnStop.setToolTipText("Stops the simulation if it is running and resets the simulation");
        btnStop.setEnabled(false);
        btnStop.setFocusable(false);
        btnStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });
        toolbar.add(btnStop);

        btnStepForward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/heartsim/gui/icon/media-seek-forward.png"))); // NOI18N
        btnStepForward.setToolTipText("Step simulation forward");
        btnStepForward.setFocusable(false);
        btnStepForward.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStepForward.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStepForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStepForwardActionPerformed(evt);
            }
        });
        toolbar.add(btnStepForward);

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
            .addGap(0, 611, Short.MAX_VALUE)
        );
        svgCanvasLayout.setVerticalGroup(
            svgCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 359, Short.MAX_VALUE)
        );

        lblStatus.setText("Status");

        javax.swing.GroupLayout pnlRootContainerLayout = new javax.swing.GroupLayout(pnlRootContainer);
        pnlRootContainer.setLayout(pnlRootContainerLayout);
        pnlRootContainerLayout.setHorizontalGroup(
            pnlRootContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRootContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(svgCanvas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlRootContainerLayout.createSequentialGroup()
                        .addComponent(lblStatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 423, Short.MAX_VALUE)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlRootContainerLayout.setVerticalGroup(
            pnlRootContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(svgCanvas, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRootContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus))
                .addContainerGap())
        );

        mnuFile.setText("File");

        mnuItmExit.setText("Exit");
        mnuItmExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmExitActionPerformed(evt);
            }
        });
        mnuFile.add(mnuItmExit);

        jMenuBar1.add(mnuFile);

        mnuAdvanced.setText("Advanced");

        mnuItmTransform.setText("Transform");
        mnuItmTransform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmTransformActionPerformed(evt);
            }
        });
        mnuAdvanced.add(mnuItmTransform);

        jMenuBar1.add(mnuAdvanced);

        mnuDebug.setText("Debug");

        mnuItmVerboseOutput.setSelected(true);
        mnuItmVerboseOutput.setText("Verbose output to terminal");
        mnuItmVerboseOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmVerboseOutputActionPerformed(evt);
            }
        });
        mnuDebug.add(mnuItmVerboseOutput);
        mnuDebug.add(jSeparator2);

        mnuItmViewCells.setText("View cells array");
        mnuItmViewCells.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmViewCellsActionPerformed(evt);
            }
        });
        mnuDebug.add(mnuItmViewCells);
        mnuDebug.add(jSeparator1);

        mnuItmPrintCells.setText("Print cells to terminal");
        mnuItmPrintCells.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmPrintCellsActionPerformed(evt);
            }
        });
        mnuDebug.add(mnuItmPrintCells);

        mnuItmPrintArrays.setText("Print arrays to terminal");
        mnuItmPrintArrays.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmPrintArraysActionPerformed(evt);
            }
        });
        mnuDebug.add(mnuItmPrintArrays);

        jMenuBar1.add(mnuDebug);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlRootContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlRootContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void svgCanvasMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_svgCanvasMouseClicked
    {//GEN-HEADEREND:event_svgCanvasMouseClicked
        // left click
        if (evt.getButton() == MouseEvent.BUTTON1)
        {
            // run simulation
            stimCol = evt.getX();
            stimRow = evt.getY();
            simulation.setStimulatedCell(stimRow, stimCol);
            simulation.run();
        }

        if (evt.getButton() == MouseEvent.BUTTON3)
        {
            // pause simulation
            simulation.pause();
        }
    }//GEN-LAST:event_svgCanvasMouseClicked

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnOpenActionPerformed
    {//GEN-HEADEREND:event_btnOpenActionPerformed
        JFileChooser chooser = new JFileChooser(".");
        chooser.setFileFilter(new FileChooserFilter("svg", "SVG Files"));
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION)
        {
            loadSVG(chooser.getSelectedFile());
        }
}//GEN-LAST:event_btnOpenActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnStartActionPerformed
    {//GEN-HEADEREND:event_btnStartActionPerformed
        simulation.setStimulatedCell(stimRow, stimCol);
        simulation.run();
}//GEN-LAST:event_btnStartActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnStopActionPerformed
    {//GEN-HEADEREND:event_btnStopActionPerformed
        simulation.stop();
}//GEN-LAST:event_btnStopActionPerformed

    private void btnStepForwardActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnStepForwardActionPerformed
    {//GEN-HEADEREND:event_btnStepForwardActionPerformed
        simulation.run();
}//GEN-LAST:event_btnStepForwardActionPerformed

    private void btnPauseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnPauseActionPerformed
    {//GEN-HEADEREND:event_btnPauseActionPerformed
        simulation.pause();
    }//GEN-LAST:event_btnPauseActionPerformed

    private void mnuItmPrintArraysActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuItmPrintArraysActionPerformed
    {//GEN-HEADEREND:event_mnuItmPrintArraysActionPerformed
        ((Nishiyama) caModel).printArrays();
    }//GEN-LAST:event_mnuItmPrintArraysActionPerformed

    private void mnuItmPrintCellsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuItmPrintCellsActionPerformed
    {//GEN-HEADEREND:event_mnuItmPrintCellsActionPerformed
        caModel.printCells();
    }//GEN-LAST:event_mnuItmPrintCellsActionPerformed

    private void mnuItmExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuItmExitActionPerformed
    {//GEN-HEADEREND:event_mnuItmExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnuItmExitActionPerformed

    private void mnuItmViewCellsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuItmViewCellsActionPerformed
    {//GEN-HEADEREND:event_mnuItmViewCellsActionPerformed
        new CellsViewer(cellGenerator.getCells()).setVisible(true);
    }//GEN-LAST:event_mnuItmViewCellsActionPerformed

    private void mnuItmTransformActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuItmTransformActionPerformed
    {//GEN-HEADEREND:event_mnuItmTransformActionPerformed
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
    }//GEN-LAST:event_mnuItmTransformActionPerformed

    private void mnuItmVerboseOutputActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuItmVerboseOutputActionPerformed
    {//GEN-HEADEREND:event_mnuItmVerboseOutputActionPerformed
        Application.getInstance().setDebugMode(mnuItmVerboseOutput.isSelected());
    }//GEN-LAST:event_mnuItmVerboseOutputActionPerformed

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
    private javax.swing.JButton btnOpen;
    private javax.swing.JToolBar.Separator btnOpenSeparator;
    private javax.swing.JButton btnPause;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStepForward;
    private javax.swing.JButton btnStop;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JMenu mnuAdvanced;
    private javax.swing.JMenu mnuDebug;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenuItem mnuItmExit;
    private javax.swing.JMenuItem mnuItmPrintArrays;
    private javax.swing.JMenuItem mnuItmPrintCells;
    private javax.swing.JMenuItem mnuItmTransform;
    private javax.swing.JCheckBoxMenuItem mnuItmVerboseOutput;
    private javax.swing.JMenuItem mnuItmViewCells;
    private javax.swing.JPanel pnlRootContainer;
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
        caModel.setCells(cellGenerator.getCells());
        overlay.setSize(cellGenerator.getCells()[0].length, cellGenerator.getCells().length);
    }

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

    public void documentLoadingCancelled(SVGDocumentLoaderEvent arg0)
    {
    }

    public void documentLoadingFailed(SVGDocumentLoaderEvent arg0)
    {
    }

    public void gvtBuildCancelled(GVTTreeBuilderEvent arg0)
    {
    }

    public void gvtBuildFailed(GVTTreeBuilderEvent arg0)
    {
    }

    public void gvtRenderingStarted(GVTTreeRendererEvent arg0)
    {
    }

    public void gvtRenderingCancelled(GVTTreeRendererEvent arg0)
    {
    }

    public void gvtRenderingFailed(GVTTreeRendererEvent arg0)
    {
    }

    public void simulationStarted()
    {
        Application.getInstance().output("Started");
        setStatusText("Simulation started");
        btnStop.setEnabled(true);
        btnPause.setEnabled(true);
        btnStart.setEnabled(false);
    }

    public void simulationPaused()
    {
        Application.getInstance().output("Paused");
        setStatusText("Simulation paused");
        btnStop.setEnabled(true);
        btnPause.setEnabled(false);
        btnStart.setEnabled(true);
    }

    public void simulationUpdated()
    {
        svgCanvas.repaint();
    }

    public void simulationStopped()
    {
        Application.getInstance().output("Stopped");
        setStatusText("Simulation stopped");
        btnStop.setEnabled(false);
        btnPause.setEnabled(false);
        btnStart.setEnabled(true);
    }

    public void simulationCompleted()
    {
        Application.getInstance().output("Completed");
        setStatusText("Simulation completed");
        btnStop.setEnabled(false);
        btnPause.setEnabled(false);
        btnStart.setEnabled(true);
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
}
