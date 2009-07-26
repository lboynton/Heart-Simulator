/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainUI.java
 *
 * Created on 15-Jun-2009, 13:37:52
 */
package heartsim.gui;

import heartsim.DataLoader;
import heartsim.ca.Nishiyama;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

/**
 *
 * @author Lee Boynton
 */
public class MainUI extends javax.swing.JFrame
{
    private Nishiyama nishiyama;
    private SwingWorker worker;
    private int time;
    private File svgFile;

    /** Creates new form MainUI */
    public MainUI()
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
        this.setLocationRelativeTo(null);
        nishiyama = new Nishiyama();
        setSvgFile(new File("geometry_data/heart.svg"));
    }

    private void setSvgFile(File svgFile)
    {
        this.svgFile = svgFile;
        lblFile.setText(svgFile.getName());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPane = new javax.swing.JSplitPane();
        pnlDisplay = new BinaryPlotPanel(400, 400);
        pnlControls = new javax.swing.JPanel();
        lblResolution = new javax.swing.JLabel();
        cboResolution = new javax.swing.JComboBox();
        btnLoad = new javax.swing.JButton();
        txtTime = new javax.swing.JTextField();
        lblTime = new javax.swing.JLabel();
        txtN = new javax.swing.JTextField();
        txtDelta1 = new javax.swing.JTextField();
        txtDelta2 = new javax.swing.JTextField();
        btnStart = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        lblN = new javax.swing.JLabel();
        lblDelta1 = new javax.swing.JLabel();
        lblDelta2 = new javax.swing.JLabel();
        lblFile = new javax.swing.JLabel()
        {
            public void setText(String text)
            {
                super.setText(text);
                this.setToolTipText(text);
            }
        };
        btnBrowse = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Nishiyama");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        javax.swing.GroupLayout pnlDisplayLayout = new javax.swing.GroupLayout(pnlDisplay);
        pnlDisplay.setLayout(pnlDisplayLayout);
        pnlDisplayLayout.setHorizontalGroup(
            pnlDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 376, Short.MAX_VALUE)
        );
        pnlDisplayLayout.setVerticalGroup(
            pnlDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 328, Short.MAX_VALUE)
        );

        splitPane.setLeftComponent(pnlDisplay);

        pnlControls.setBorder(javax.swing.BorderFactory.createTitledBorder("Controls"));

        lblResolution.setText("Cell size");

        cboResolution.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "4", "3", "2", "1", "0.5" }));

        btnLoad.setText("Load");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        txtTime.setText("400");
        txtTime.setPreferredSize(new java.awt.Dimension(50, 25));

        lblTime.setText("Time");
        lblTime.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 4));

        txtN.setText("5");
        txtN.setPreferredSize(new java.awt.Dimension(50, 25));

        txtDelta1.setText("3");
        txtDelta1.setPreferredSize(new java.awt.Dimension(50, 25));

        txtDelta2.setText("7");
        txtDelta2.setPreferredSize(new java.awt.Dimension(50, 25));

        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        btnStop.setText("Stop");
        btnStop.setEnabled(false);
        btnStop.setFocusable(false);
        btnStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.setEnabled(false);
        btnReset.setFocusable(false);
        btnReset.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReset.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        lblN.setText("N");
        lblN.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 4));

        lblDelta1.setText("Delta 1");
        lblDelta1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 4));

        lblDelta2.setText("Delta 2");
        lblDelta2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 4));

        lblFile.setText("No file loaded");
        lblFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblFileMouseEntered(evt);
            }
        });

        btnBrowse.setText("Browse");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlControlsLayout = new javax.swing.GroupLayout(pnlControls);
        pnlControls.setLayout(pnlControlsLayout);
        pnlControlsLayout.setHorizontalGroup(
            pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLoad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                    .addComponent(btnBrowse, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlControlsLayout.createSequentialGroup()
                        .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStop)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset))
                    .addComponent(lblFile, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlControlsLayout.createSequentialGroup()
                        .addComponent(lblResolution)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboResolution, 0, 108, Short.MAX_VALUE))
                    .addGroup(pnlControlsLayout.createSequentialGroup()
                        .addGroup(pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTime)
                            .addComponent(lblN)
                            .addComponent(lblDelta2)
                            .addComponent(lblDelta1))
                        .addGap(13, 13, 13)
                        .addGroup(pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTime, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                            .addComponent(txtN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                            .addComponent(txtDelta1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                            .addComponent(txtDelta2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlControlsLayout.setVerticalGroup(
            pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlsLayout.createSequentialGroup()
                .addComponent(lblFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBrowse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblResolution)
                    .addComponent(cboResolution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLoad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtN, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDelta1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDelta1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDelta2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDelta2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStart)
                    .addComponent(btnReset)
                    .addComponent(btnStop))
                .addContainerGap())
        );

        splitPane.setRightComponent(pnlControls);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnStartActionPerformed
    {//GEN-HEADEREND:event_btnStartActionPerformed
        nishiyama.setDelta1(Integer.parseInt(txtDelta1.getText()));
        nishiyama.setDelta2(Integer.parseInt(txtDelta2.getText()));
        nishiyama.setN(Integer.parseInt(txtN.getText()));
        nishiyama.initCells();
        pnlDisplay.repaint();

        worker = new SwingWorker<Object, Void>()
        {
            @Override
            public Object doInBackground() throws Exception
            {
                btnStart.setEnabled(false);
                btnReset.setEnabled(false);
                btnStop.setEnabled(true);

                byte[] data = ((BinaryPlotPanel) pnlDisplay).getBuffer();

                int[][] u = nishiyama.getU();
                time = Integer.parseInt(txtTime.getText());

                for (int t = 0; t < time; t++)
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

                    nishiyama.step();
                    pnlDisplay.repaint();
                }

                return new Object();
            }

            @Override
            protected void done()
            {
                super.done();
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                btnReset.setEnabled(true);
            }
        };

        worker.execute();

    }//GEN-LAST:event_btnStartActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnResetActionPerformed
    {//GEN-HEADEREND:event_btnResetActionPerformed
        nishiyama.initCells();
        ((BinaryPlotPanel) pnlDisplay).reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnStopActionPerformed
    {//GEN-HEADEREND:event_btnStopActionPerformed
        time = 0;
    }//GEN-LAST:event_btnStopActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_formComponentResized
    {//GEN-HEADEREND:event_formComponentResized
        pnlDisplay.setSize(pnlDisplay.getSize());
        nishiyama.setSize(pnlDisplay.getSize());
    }//GEN-LAST:event_formComponentResized

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnLoadActionPerformed
    {//GEN-HEADEREND:event_btnLoadActionPerformed
        DataLoader loader = new DataLoader(svgFile.getPath());
        double size = Double.parseDouble(String.valueOf(cboResolution.getSelectedItem()));
        loader.setSize(size);
        nishiyama.setCells(loader.getGrid());
}//GEN-LAST:event_btnLoadActionPerformed

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnBrowseActionPerformed
    {//GEN-HEADEREND:event_btnBrowseActionPerformed
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if(result == JFileChooser.APPROVE_OPTION)
        {
            setSvgFile(chooser.getSelectedFile());
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void lblFileMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_lblFileMouseEntered
    {//GEN-HEADEREND:event_lblFileMouseEntered
        lblFile.setToolTipText(lblFile.getText());
    }//GEN-LAST:event_lblFileMouseEntered

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new MainUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JComboBox cboResolution;
    private javax.swing.JLabel lblDelta1;
    private javax.swing.JLabel lblDelta2;
    private javax.swing.JLabel lblFile;
    private javax.swing.JLabel lblN;
    private javax.swing.JLabel lblResolution;
    private javax.swing.JLabel lblTime;
    private javax.swing.JPanel pnlControls;
    private javax.swing.JPanel pnlDisplay;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JTextField txtDelta1;
    private javax.swing.JTextField txtDelta2;
    private javax.swing.JTextField txtN;
    private javax.swing.JTextField txtTime;
    // End of variables declaration//GEN-END:variables
}
