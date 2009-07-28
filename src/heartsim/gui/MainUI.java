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
import java.awt.Dimension;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.CategoryStepRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Lee Boynton
 */
public class MainUI extends javax.swing.JFrame
{
    private Nishiyama nishiyama = new Nishiyama();
    private SwingWorker worker;
    private int time;
    private int currentTime = 0;
    private File svgFile;
    private JFreeChart chart;
    private DefaultCategoryDataset chartData;
    private int stimX;
    private int stimY;

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
        setSvgFile(new File("geometry_data/heart.svg"));
    }

    private void setSvgFile(File svgFile)
    {
        this.svgFile = svgFile;
        txtFile.setText(svgFile.getName());
    }

    private ChartPanel createChart()
    {
        chartData = new DefaultCategoryDataset();

        final CategoryItemRenderer renderer = new CategoryStepRenderer(true);
        final CategoryAxis domainAxis = new CategoryAxis();
        final ValueAxis rangeAxis = new NumberAxis();
        rangeAxis.setRange(0, 5);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        final CategoryPlot plot = new CategoryPlot(chartData, domainAxis, rangeAxis, renderer);
        chart = new JFreeChart(null, plot);
        chart.removeLegend();
        chart.setBackgroundPaint(null);

        ChartPanel panel = new ChartPanel(chart);

        return panel;
    }

    private void resetSimulation()
    {
        currentTime = 0;
        nishiyama.setDelta1(Integer.parseInt(txtDelta1.getText()));
        nishiyama.setDelta2(Integer.parseInt(txtDelta2.getText()));
        nishiyama.setN(Integer.parseInt(txtN.getText()));
        nishiyama.initCells();
        nishiyama.stimulate(stimX, stimY);
        pnlDisplay.repaint();
        btnStart.setEnabled(true);
        btnStep.setEnabled(true);
        btnReset.setEnabled(false);
        btnStop.setEnabled(false);
    }

    private void runSimulation(int time)
    {
        btnStart.setEnabled(false);
        btnReset.setEnabled(false);
        btnStep.setEnabled(false);
        btnStop.setEnabled(true);

        byte[] data = pnlDisplay.getBuffer();

        int[][] u = nishiyama.getU();

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

            if (chartData.getColumnCount() > 6)
            {
                chartData.removeColumn(0);
            }

            chartData.addValue(u[stimX][stimY], "Voltage", String.valueOf(currentTime));
            chartData.addValue(nishiyama.getV(stimX, stimY), "Recovery", String.valueOf(currentTime));
            nishiyama.step();
            pnlDisplay.repaint();
            currentTime++;
        }

        btnStart.setEnabled(true);
        btnReset.setEnabled(true);
        btnStep.setEnabled(true);
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

        pnlDataSource = new javax.swing.JPanel();
        lblResolution = new javax.swing.JLabel();
        cboCellSize = new javax.swing.JComboBox();
        btnLoad = new javax.swing.JButton();
        btnBrowse = new javax.swing.JButton();
        lblResolution1 = new javax.swing.JLabel();
        txtFile = new javax.swing.JTextField();
        pnlParameters = new javax.swing.JPanel();
        btnStart = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        lblN = new javax.swing.JLabel();
        lblDelta1 = new javax.swing.JLabel();
        lblDelta2 = new javax.swing.JLabel();
        txtDelta2 = new javax.swing.JTextField();
        txtDelta1 = new javax.swing.JTextField();
        txtN = new javax.swing.JTextField();
        lblTime = new javax.swing.JLabel();
        txtTime = new javax.swing.JTextField();
        btnStep = new javax.swing.JButton();
        pnlDisplayContainer = new javax.swing.JPanel();
        pnlDisplay = new heartsim.gui.BinaryPlotPanel();
        pnlChartContainer = new javax.swing.JPanel();
        pnlChart = createChart();
        pnlChart.setPreferredSize(new Dimension(250,150));
        chkBxVoltage = new javax.swing.JCheckBox();
        chkBoxRecovery = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Nishiyama");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        pnlDataSource.setBorder(javax.swing.BorderFactory.createTitledBorder("Heart Data"));

        lblResolution.setText("Cell size");

        cboCellSize.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "4", "3", "2", "1", "0.5" }));

        btnLoad.setText("Load");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        btnBrowse.setText("Browse");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        lblResolution1.setText("File");

        txtFile.setEditable(false);

        javax.swing.GroupLayout pnlDataSourceLayout = new javax.swing.GroupLayout(pnlDataSource);
        pnlDataSource.setLayout(pnlDataSourceLayout);
        pnlDataSourceLayout.setHorizontalGroup(
            pnlDataSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDataSourceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDataSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblResolution)
                    .addComponent(lblResolution1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDataSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFile, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                    .addComponent(cboCellSize, 0, 103, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDataSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLoad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBrowse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlDataSourceLayout.setVerticalGroup(
            pnlDataSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDataSourceLayout.createSequentialGroup()
                .addGroup(pnlDataSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBrowse)
                    .addComponent(lblResolution1)
                    .addComponent(txtFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDataSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblResolution)
                    .addComponent(cboCellSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLoad))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlParameters.setBorder(javax.swing.BorderFactory.createTitledBorder("Simulation Controls"));

        btnStart.setText("Start");
        btnStart.setEnabled(false);
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        btnStop.setText("Stop");
        btnStop.setEnabled(false);
        btnStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.setEnabled(false);
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

        txtDelta2.setText("7");
        txtDelta2.setPreferredSize(new java.awt.Dimension(50, 25));

        txtDelta1.setText("3");
        txtDelta1.setPreferredSize(new java.awt.Dimension(50, 25));

        txtN.setText("5");
        txtN.setPreferredSize(new java.awt.Dimension(50, 25));

        lblTime.setText("Time");
        lblTime.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 4));

        txtTime.setText("400");
        txtTime.setPreferredSize(new java.awt.Dimension(50, 25));

        btnStep.setText("Step");
        btnStep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStep.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlParametersLayout = new javax.swing.GroupLayout(pnlParameters);
        pnlParameters.setLayout(pnlParametersLayout);
        pnlParametersLayout.setHorizontalGroup(
            pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlParametersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlParametersLayout.createSequentialGroup()
                        .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStep)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStop)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset))
                    .addGroup(pnlParametersLayout.createSequentialGroup()
                        .addComponent(lblTime)
                        .addGap(28, 28, 28)
                        .addComponent(txtTime, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlParametersLayout.createSequentialGroup()
                        .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblN)
                            .addComponent(lblDelta2)
                            .addComponent(lblDelta1))
                        .addGap(13, 13, 13)
                        .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(txtDelta1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(txtDelta2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlParametersLayout.setVerticalGroup(
            pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlParametersLayout.createSequentialGroup()
                .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTime)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtN, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDelta1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDelta1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDelta2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDelta2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStart)
                    .addComponent(btnStop)
                    .addComponent(btnReset)
                    .addComponent(btnStep))
                .addContainerGap())
        );

        pnlDisplayContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Visualisation"));

        pnlDisplay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDisplayMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlDisplayLayout = new javax.swing.GroupLayout(pnlDisplay);
        pnlDisplay.setLayout(pnlDisplayLayout);
        pnlDisplayLayout.setHorizontalGroup(
            pnlDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 339, Short.MAX_VALUE)
        );
        pnlDisplayLayout.setVerticalGroup(
            pnlDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 452, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlDisplayContainerLayout = new javax.swing.GroupLayout(pnlDisplayContainer);
        pnlDisplayContainer.setLayout(pnlDisplayContainerLayout);
        pnlDisplayContainerLayout.setHorizontalGroup(
            pnlDisplayContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDisplayContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlDisplayContainerLayout.setVerticalGroup(
            pnlDisplayContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDisplayContainerLayout.createSequentialGroup()
                .addComponent(pnlDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlChartContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Voltage/Recovery"));

        javax.swing.GroupLayout pnlChartLayout = new javax.swing.GroupLayout(pnlChart);
        pnlChart.setLayout(pnlChartLayout);
        pnlChartLayout.setHorizontalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 231, Short.MAX_VALUE)
        );
        pnlChartLayout.setVerticalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 101, Short.MAX_VALUE)
        );

        chkBxVoltage.setSelected(true);
        chkBxVoltage.setText("Voltage");
        chkBxVoltage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBxVoltageActionPerformed(evt);
            }
        });

        chkBoxRecovery.setSelected(true);
        chkBoxRecovery.setText("Recovery");
        chkBoxRecovery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBoxRecoveryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlChartContainerLayout = new javax.swing.GroupLayout(pnlChartContainer);
        pnlChartContainer.setLayout(pnlChartContainerLayout);
        pnlChartContainerLayout.setHorizontalGroup(
            pnlChartContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChartContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkBxVoltage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkBoxRecovery)
                .addContainerGap(82, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChartContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlChartContainerLayout.setVerticalGroup(
            pnlChartContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChartContainerLayout.createSequentialGroup()
                .addComponent(pnlChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlChartContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkBxVoltage)
                    .addComponent(chkBoxRecovery))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlDisplayContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnlChartContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlParameters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDataSource, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlDataSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlChartContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlParameters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(pnlDisplayContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnStartActionPerformed
    {//GEN-HEADEREND:event_btnStartActionPerformed
        resetSimulation();

        worker = new SwingWorker<Object, Void>()
        {
            @Override
            public Object doInBackground() throws Exception
            {
                runSimulation(Integer.parseInt(txtTime.getText()));

                return new Object();
            }

            @Override
            protected void done()
            {
                super.done();
                btnStep.setEnabled(true);
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
        pnlDisplay.reset();
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
        double size = Double.parseDouble(String.valueOf(cboCellSize.getSelectedItem()));
        loader.setSize(size);
        nishiyama.setCells(loader.getGrid());
}//GEN-LAST:event_btnLoadActionPerformed

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnBrowseActionPerformed
    {//GEN-HEADEREND:event_btnBrowseActionPerformed
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION)
        {
            setSvgFile(chooser.getSelectedFile());
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void pnlDisplayMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_pnlDisplayMouseClicked
    {//GEN-HEADEREND:event_pnlDisplayMouseClicked
        stimX = evt.getY();
        stimY = evt.getX();
        this.btnStartActionPerformed(null);
    }//GEN-LAST:event_pnlDisplayMouseClicked

    private void btnStepActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnStepActionPerformed
    {//GEN-HEADEREND:event_btnStepActionPerformed
        runSimulation(1);
        btnStep.requestFocus();
    }//GEN-LAST:event_btnStepActionPerformed

    private void chkBxVoltageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_chkBxVoltageActionPerformed
    {//GEN-HEADEREND:event_chkBxVoltageActionPerformed
        // TODO add code to toggle voltage visibility on chart
    }//GEN-LAST:event_chkBxVoltageActionPerformed

    private void chkBoxRecoveryActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_chkBoxRecoveryActionPerformed
    {//GEN-HEADEREND:event_chkBoxRecoveryActionPerformed
        // TODO add code to toggle recovery visibility on chart
    }//GEN-LAST:event_chkBoxRecoveryActionPerformed

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
    private javax.swing.JButton btnStep;
    private javax.swing.JButton btnStop;
    private javax.swing.JComboBox cboCellSize;
    private javax.swing.JCheckBox chkBoxRecovery;
    private javax.swing.JCheckBox chkBxVoltage;
    private javax.swing.JLabel lblDelta1;
    private javax.swing.JLabel lblDelta2;
    private javax.swing.JLabel lblN;
    private javax.swing.JLabel lblResolution;
    private javax.swing.JLabel lblResolution1;
    private javax.swing.JLabel lblTime;
    private javax.swing.JPanel pnlChart;
    private javax.swing.JPanel pnlChartContainer;
    private javax.swing.JPanel pnlDataSource;
    private heartsim.gui.BinaryPlotPanel pnlDisplay;
    private javax.swing.JPanel pnlDisplayContainer;
    private javax.swing.JPanel pnlParameters;
    private javax.swing.JTextField txtDelta1;
    private javax.swing.JTextField txtDelta2;
    private javax.swing.JTextField txtFile;
    private javax.swing.JTextField txtN;
    private javax.swing.JTextField txtTime;
    // End of variables declaration//GEN-END:variables
}
