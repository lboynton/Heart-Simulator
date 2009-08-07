/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainUI2.java
 *
 * Created on 07-Aug-2009, 13:18:40
 */
package heartsim.gui;

import heartsim.DataLoader;
import heartsim.ca.CAModel;
import heartsim.ca.Nishiyama;
import heartsim.ca.Tyson;
import heartsim.ca.parameter.CAModelParameter;
import heartsim.gui.layout.SpringUtilities;
import heartsim.gui.util.FileChooserFilter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

/**
 *
 * @author Lee Boynton
 */
public class MainUI2 extends javax.swing.JFrame
{
    private CAModel CAModel; // CA model being used in the simulation
    private final boolean DEBUG = true; // if debug is true then print output messages
    private File svgFile; // svg containing heart geometry
    private double cellSize = 4;
    private int time; // time to run simulation
    private int currentTime = 0; // current time in simulation
    private int stimX = 53; // X-axis location cell which should be stimulated
    private int stimY = 18; // Y-axis location cell which should be stimulated
    private SwingWorker<Object, Void> worker;
    private DataLoader loader;
    
    /** Creates new form MainUI2 */
    public MainUI2()
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

        // center frame on window
        this.setLocationRelativeTo(null);

        // load initially selected CA model
        cboBoxModel.setSelectedIndex(0);

        // initially load an SVG file
        setSvgFile(new File("geometry_data/heart.svg"));
    }

    /**
     * Loads the available CA models into the combo box in the GUI. New CA models
     * should be added in here
     * @return Combo box model containing all the CA models
     */
    private ComboBoxModel loadCAModels()
    {
        DefaultComboBoxModel CAModels = new DefaultComboBoxModel();

        // add CA models to combo box model
        CAModels.addElement(new Nishiyama());
        CAModels.addElement(new Tyson());

        return CAModels;
    }

    /**
     * Called when the CA model changes. Loads the CA model parameters into the
     * GUI.
     */
    private void loadModelParameters()
    {
        output("Loading model parameters");

        // skip loading parameters if model hasn't changed
        if (CAModel != null && CAModel.equals(cboBoxModel.getSelectedItem()))
        {
            output("Same model selected, skipping.");
            return;
        }

        // get the CA model that was selected
        CAModel = (CAModel) cboBoxModel.getSelectedItem();

        output("Model selected is " + CAModel.getName());

        // clear any existing parameters in the GUI
        pnlParameters.removeAll();

        // use spring layout for this panel
        pnlParameters.setLayout(new SpringLayout());
        
        // add the tissue combo box selector first
        pnlParameters.add(lblTissue);
        pnlParameters.add(cboBoxTissue);

        // add the CA model combo box selector
        pnlParameters.add(lblModel);
        pnlParameters.add(cboBoxModel);

        // loop through the parameters in the CA model and put them on the GUI
        for (final CAModelParameter p : CAModel.getParameters().values())
        {
            output("Found parameter: " + p.getName());

            JLabel lbl = new JLabel(p.getName());
            final JTextField txt = new JTextField();
            txt.setText(p.getValue().toString());
            txt.addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyReleased(KeyEvent evt)
                {
                    if (!p.setValue(txt.getText()))
                    {
                        output("Invalid parameter: " + txt.getText());
                        txt.setForeground(Color.red);
                    }
                    else
                    {
                        txt.setForeground(Color.BLACK);
                    }
                    CAModel.setParameter(p.getName(), p);
                }
            });

            pnlParameters.add(lbl);
            pnlParameters.add(txt);
        }

        // place components in grid
        SpringUtilities.makeCompactGrid(pnlParameters,
                CAModel.getParameters().size() + 2, 2, //rows, cols
                10, 12, //initX, initY
                12, 6);       //xPad, yPad
    }

    private void output(String output)
    {
        if (DEBUG)
        {
            System.out.println(output);
        }
    }

    private void setStatusBar(String text)
    {
        lblStatus.setText(text);
    }

    private void setSvgFile(File svgFile)
    {
        if (!svgFile.exists())
        {
            return;
        }

        this.svgFile = svgFile;
        setStatusBar("Loaded file: " + svgFile.getName());
        loadHeart();
    }

    private void loadHeart()
    {
        loader = new DataLoader(svgFile.getPath());
        loader.setSize(cellSize);
        CAModel.setCells(loader.getGrid());
        CAModel.setSize(pnlDisplay.getSize());
    }

    private void resetSimulation()
    {
        currentTime = 0;
        CAModel.initCells();
        CAModel.stimulate(stimX, stimY);
//        pnlDisplay.setPreferredSize(new Dimension(loader.getGrid().length, loader.getGrid()[0].length));
//        pnlDisplay.setSize(new Dimension(loader.getGrid().length, loader.getGrid()[0].length));
        pnlDisplay.setSize(pnlDisplay.getSize());
        pnlDisplay.reset();
        btnStart.setEnabled(true);
        btnStepForward.setEnabled(true);
        btnStop.setEnabled(false);
    }

    private void runSimulation()
    {
        btnStart.setEnabled(false);
        btnStepForward.setEnabled(false);
        btnStop.setEnabled(true);

        if (!CAModel.isCell(stimX, stimY))
        {
            output("No cell at X: " + stimX + " Y: " + stimY);
            return;
        }

        output("Started simulation at X: " + stimX + " Y: " + stimY);

        byte[] data = pnlDisplay.getBuffer();

        int[][] u = CAModel.getU();

        for (int t = 0; t < this.time; t++)
        {
            int k = 0;

            for (int i = 0; i < u.length; i++)
            {
                for (int j = 0; j < u[0].length; j++)
                {
                    byte val = (byte) u[i][j];
                    if (val == 0)
                    {
                        data[k] = -10;
                    }
                    else
                    {
                        data[k] = (byte) u[i][j];
                    }
                    k++;
                }
            }

            /*if (chartData.getColumnCount() > 6)
            {
                chartData.removeColumn(0);
            }*/

            currentTime++;
            //chartData.addValue(u[stimX][stimY], "Voltage", String.valueOf(currentTime));
            //chartData.addValue(CAModel.getV(stimX, stimY), "Recovery", String.valueOf(currentTime));
            CAModel.step();
            pnlDisplay.repaint();

        }

        btnStart.setEnabled(true);
        btnStepForward.setEnabled(true);
        btnStop.setEnabled(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblStatus = new javax.swing.JLabel();
        toolBar = new javax.swing.JToolBar();
        btnOpen = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnStart = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnStepForward = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnZoomIn = new javax.swing.JButton();
        btnZoomOut = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnAbout = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        pnlDisplay = new heartsim.gui.BinaryPlotPanel();
        tabbedPane = new javax.swing.JTabbedPane();
        pnlCA = new javax.swing.JPanel();
        pnlParameters = new javax.swing.JPanel();
        lblTissue = new javax.swing.JLabel();
        lblModel = new javax.swing.JLabel();
        cboBoxTissue = new javax.swing.JComboBox();
        cboBoxModel = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        lblTime = new javax.swing.JLabel();
        txtTime = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblStatus.setText("Status");

        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        btnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/heartsim/gui/icon/document-open.png"))); // NOI18N
        btnOpen.setFocusable(false);
        btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        toolBar.add(btnOpen);
        toolBar.add(jSeparator1);

        btnStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/heartsim/gui/icon/media-playback-start.png"))); // NOI18N
        btnStart.setFocusable(false);
        btnStart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });
        toolBar.add(btnStart);

        btnStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/heartsim/gui/icon/media-playback-stop.png"))); // NOI18N
        btnStop.setFocusable(false);
        btnStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });
        toolBar.add(btnStop);

        btnStepForward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/heartsim/gui/icon/media-seek-forward.png"))); // NOI18N
        btnStepForward.setFocusable(false);
        btnStepForward.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStepForward.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(btnStepForward);
        toolBar.add(jSeparator2);

        btnZoomIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/heartsim/gui/icon/zoom-in.png"))); // NOI18N
        btnZoomIn.setFocusable(false);
        btnZoomIn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnZoomIn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomInActionPerformed(evt);
            }
        });
        toolBar.add(btnZoomIn);

        btnZoomOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/heartsim/gui/icon/zoom-out.png"))); // NOI18N
        btnZoomOut.setFocusable(false);
        btnZoomOut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnZoomOut.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnZoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomOutActionPerformed(evt);
            }
        });
        toolBar.add(btnZoomOut);
        toolBar.add(jSeparator3);

        btnAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/heartsim/gui/icon/help-browser.png"))); // NOI18N
        btnAbout.setFocusable(false);
        btnAbout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAbout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(btnAbout);

        jPanel1.setBackground(java.awt.Color.white);

        pnlDisplay.setBackground(java.awt.Color.white);

        javax.swing.GroupLayout pnlDisplayLayout = new javax.swing.GroupLayout(pnlDisplay);
        pnlDisplay.setLayout(pnlDisplayLayout);
        pnlDisplayLayout.setHorizontalGroup(
            pnlDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 157, Short.MAX_VALUE)
        );
        pnlDisplayLayout.setVerticalGroup(
            pnlDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 157, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(107, Short.MAX_VALUE)
                .addComponent(pnlDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(123, Short.MAX_VALUE)
                .addComponent(pnlDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
        );

        jScrollPane1.setViewportView(jPanel1);

        lblTissue.setText("Tissue");

        lblModel.setText("Model");

        cboBoxTissue.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ventricles" }));

        cboBoxModel.setModel(loadCAModels());
        cboBoxModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboBoxModelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlParametersLayout = new javax.swing.GroupLayout(pnlParameters);
        pnlParameters.setLayout(pnlParametersLayout);
        pnlParametersLayout.setHorizontalGroup(
            pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlParametersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTissue)
                    .addComponent(lblModel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboBoxTissue, 0, 136, Short.MAX_VALUE)
                    .addComponent(cboBoxModel, 0, 136, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlParametersLayout.setVerticalGroup(
            pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlParametersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTissue)
                    .addComponent(cboBoxTissue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblModel)
                    .addComponent(cboBoxModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlCALayout = new javax.swing.GroupLayout(pnlCA);
        pnlCA.setLayout(pnlCALayout);
        pnlCALayout.setHorizontalGroup(
            pnlCALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlParameters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlCALayout.setVerticalGroup(
            pnlCALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCALayout.createSequentialGroup()
                .addComponent(pnlParameters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(271, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Model", pnlCA);

        jLabel4.setText("Heart rate");

        jTextField2.setText("70");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(316, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Pacemaker", jPanel2);

        lblTime.setText("Time");

        txtTime.setText("500");

        jLabel1.setText("Speed");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTime)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(txtTime, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTime)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(283, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Simulation", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatus)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboBoxModelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cboBoxModelActionPerformed
    {//GEN-HEADEREND:event_cboBoxModelActionPerformed
        loadModelParameters();
    }//GEN-LAST:event_cboBoxModelActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnOpenActionPerformed
    {//GEN-HEADEREND:event_btnOpenActionPerformed
        JFileChooser chooser = new JFileChooser(".");
        chooser.setFileFilter(new FileChooserFilter("svg", "SVG Files"));
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION)
        {
            setSvgFile(chooser.getSelectedFile());
        }
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnZoomInActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnZoomInActionPerformed
    {//GEN-HEADEREND:event_btnZoomInActionPerformed
        cellSize = cellSize - .5;
        loadHeart();
    }//GEN-LAST:event_btnZoomInActionPerformed

    private void btnZoomOutActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnZoomOutActionPerformed
    {//GEN-HEADEREND:event_btnZoomOutActionPerformed
        cellSize = cellSize + .5;
        loadHeart();
    }//GEN-LAST:event_btnZoomOutActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnStartActionPerformed
    {//GEN-HEADEREND:event_btnStartActionPerformed
        resetSimulation();
        lblStatus.setText("Started simulation at X: " + stimX + " Y: " + stimY);

        worker = new SwingWorker<Object, Void>()
        {
            @Override
            public Object doInBackground() throws Exception
            {
                time = Integer.parseInt(txtTime.getText());
                runSimulation();

                return null;
            }

            @Override
            protected void done()
            {
                super.done();
                btnStepForward.setEnabled(true);
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                lblStatus.setText("Simulation finished");
            }
        };

        worker.execute();
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnStopActionPerformed
    {//GEN-HEADEREND:event_btnStopActionPerformed
        resetSimulation();
    }//GEN-LAST:event_btnStopActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new MainUI2().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbout;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStepForward;
    private javax.swing.JButton btnStop;
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomOut;
    private javax.swing.JComboBox cboBoxModel;
    private javax.swing.JComboBox cboBoxTissue;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lblModel;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTissue;
    private javax.swing.JPanel pnlCA;
    private heartsim.gui.BinaryPlotPanel pnlDisplay;
    private javax.swing.JPanel pnlParameters;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JTextField txtTime;
    // End of variables declaration//GEN-END:variables
}
