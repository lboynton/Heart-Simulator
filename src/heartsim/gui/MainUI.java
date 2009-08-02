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
import heartsim.ca.CAModel;
import heartsim.ca.parameter.CAModelParameter;
import heartsim.ca.Nishiyama;
import heartsim.ca.Tyson;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import heartsim.gui.layout.SpringUtilities;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryStepRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Lee Boynton
 */
public class MainUI extends javax.swing.JFrame
{
    private CAModel CAModel; // CA model being used in the simulation
    private SwingWorker worker;
    private int time; // time to run simulation
    private int currentTime = 0; // current time in simulation
    private File svgFile; // svg containing heart geometry
    private JFreeChart chart; // voltage and recovery line chart
    private DefaultCategoryDataset chartData; // line chart data
    private int stimX; // X-axis location cell which should be stimulated
    private int stimY; // Y-axis location cell which should be stimulated
    private CategoryStepRenderer renderer; // line chart renderer
    private final boolean DEBUG = true; // if debug is true then print output messages

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

        // content pane of JFrame isn't always same colour as panels
        this.getContentPane().setBackground(pnlDisplay.getBackground());

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

    private void setSvgFile(File svgFile)
    {
        if (svgFile.exists())
        {
            this.svgFile = svgFile;
            txtFile.setText(svgFile.getName());
        }
    }

    private ChartPanel createChart()
    {
        chartData = new DefaultCategoryDataset();

        renderer = new CategoryStepRenderer(true);
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
        CAModel.initCells();
        CAModel.stimulate(stimX, stimY);
        pnlDisplay.reset();
        btnStart.setEnabled(true);
        btnStep.setEnabled(true);
        btnReset.setEnabled(false);
        btnStop.setEnabled(false);
    }

    private void runSimulation()
    {
        btnStart.setEnabled(false);
        btnReset.setEnabled(false);
        btnStep.setEnabled(false);
        btnStop.setEnabled(true);

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

            if (chartData.getColumnCount() > 6)
            {
                chartData.removeColumn(0);
            }

            chartData.addValue(u[stimX][stimY], "Voltage", String.valueOf(currentTime));
            chartData.addValue(CAModel.getV(stimX, stimY), "Recovery", String.valueOf(currentTime));
            CAModel.step();
            pnlDisplay.repaint();
            currentTime++;
        }

        btnStart.setEnabled(true);
        btnReset.setEnabled(true);
        btnStep.setEnabled(true);
        btnStop.setEnabled(false);
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

        // add the CA model combo box selector first
        pnlParameters.add(lblModel);
        pnlParameters.add(cboBoxModel);

        // add time
        pnlParameters.add(lblTime);
        pnlParameters.add(txtTime);

        // loop through the parameters in the CA model and put them on the GUI
        for (final CAModelParameter p : CAModel.getParameters().values())
        {
            output("Found parameter: " + p.getName());

            JLabel lbl = new JLabel(p.getName());
            final JTextField txt = new JTextField();
            txt.setText(p.getValue().toString());
            txt.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    if(!p.setValue(txt.getText()))
                    {
                        output("Invalid format");
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
                0, 0, //initX, initY
                10, 6);       //xPad, yPad

        // draw the new GUI elements
        pnlParameters.revalidate();
    }

    private void output(String output)
    {
        if (DEBUG)
        {
            System.out.println(output);
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

        pnlDataSource = new javax.swing.JPanel();
        lblResolution = new javax.swing.JLabel();
        cboCellSize = new javax.swing.JComboBox();
        btnLoad = new javax.swing.JButton();
        btnBrowse = new javax.swing.JButton();
        lblResolution1 = new javax.swing.JLabel();
        txtFile = new javax.swing.JTextField();
        pnlControls = new javax.swing.JPanel();
        lblModel = new javax.swing.JLabel();
        cboBoxModel = new javax.swing.JComboBox();
        pnlParameters = new javax.swing.JPanel();
        txtTime = new javax.swing.JTextField();
        lblTime = new javax.swing.JLabel();
        pnlDisplayContainer = new javax.swing.JPanel();
        pnlDisplay = new heartsim.gui.BinaryPlotPanel();
        pnlChartContainer = new javax.swing.JPanel();
        pnlChart = createChart();
        pnlChart.setPreferredSize(new Dimension(250,150));
        chkBoxVoltage = new javax.swing.JCheckBox();
        chkBoxRecovery = new javax.swing.JCheckBox();
        lblStatus = new javax.swing.JLabel();
        btnStart = new javax.swing.JButton();
        btnStep = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();

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

        pnlControls.setBorder(javax.swing.BorderFactory.createTitledBorder("Simulation Controls"));

        lblModel.setText("Model");

        cboBoxModel.setModel(loadCAModels());
        cboBoxModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboBoxModelActionPerformed(evt);
            }
        });

        txtTime.setText("400");
        txtTime.setPreferredSize(new java.awt.Dimension(50, 25));

        lblTime.setText("Time");
        lblTime.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 4));

        javax.swing.GroupLayout pnlParametersLayout = new javax.swing.GroupLayout(pnlParameters);
        pnlParameters.setLayout(pnlParametersLayout);
        pnlParametersLayout.setHorizontalGroup(
            pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlParametersLayout.createSequentialGroup()
                .addComponent(lblTime)
                .addGap(30, 30, 30)
                .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        pnlParametersLayout.setVerticalGroup(
            pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlParametersLayout.createSequentialGroup()
                .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTime)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(99, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlControlsLayout = new javax.swing.GroupLayout(pnlControls);
        pnlControls.setLayout(pnlControlsLayout);
        pnlControlsLayout.setHorizontalGroup(
            pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlControlsLayout.createSequentialGroup()
                        .addComponent(lblModel)
                        .addGap(28, 28, 28)
                        .addComponent(cboBoxModel, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlParameters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        pnlControlsLayout.setVerticalGroup(
            pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlsLayout.createSequentialGroup()
                .addGroup(pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblModel)
                    .addComponent(cboBoxModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlParameters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addGap(0, 469, Short.MAX_VALUE)
        );
        pnlDisplayLayout.setVerticalGroup(
            pnlDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 506, Short.MAX_VALUE)
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
            .addGap(0, 91, Short.MAX_VALUE)
        );

        chkBoxVoltage.setForeground(java.awt.Color.red);
        chkBoxVoltage.setSelected(true);
        chkBoxVoltage.setText("Voltage");
        chkBoxVoltage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBoxVoltageActionPerformed(evt);
            }
        });

        chkBoxRecovery.setForeground(java.awt.Color.blue);
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlChartContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChartContainerLayout.createSequentialGroup()
                        .addComponent(pnlChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChartContainerLayout.createSequentialGroup()
                        .addComponent(chkBoxVoltage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkBoxRecovery)
                        .addGap(51, 51, 51))))
        );
        pnlChartContainerLayout.setVerticalGroup(
            pnlChartContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChartContainerLayout.createSequentialGroup()
                .addComponent(pnlChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlChartContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkBoxVoltage)
                    .addComponent(chkBoxRecovery))
                .addContainerGap())
        );

        lblStatus.setText(" ");
        lblStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnStart.setText("Start");
        btnStart.setEnabled(false);
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        btnStep.setText("Step");
        btnStep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStep.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStepActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlDisplayContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStep)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStop)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset))
                    .addComponent(pnlControls, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlChartContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDataSource, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlDataSource, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlChartContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlControls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnReset)
                            .addComponent(btnStop)
                            .addComponent(btnStep)
                            .addComponent(btnStart)))
                    .addComponent(pnlDisplayContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatus))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                btnStep.setEnabled(true);
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                btnReset.setEnabled(true);
                lblStatus.setText("Simulation finished");
            }
        };

        worker.execute();

    }//GEN-LAST:event_btnStartActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnResetActionPerformed
    {//GEN-HEADEREND:event_btnResetActionPerformed
        resetSimulation();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnStopActionPerformed
    {//GEN-HEADEREND:event_btnStopActionPerformed
        lblStatus.setText("Simulation finished");
        time = 0;
    }//GEN-LAST:event_btnStopActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_formComponentResized
    {//GEN-HEADEREND:event_formComponentResized
        pnlDisplay.setSize(pnlDisplay.getSize());
        CAModel.setSize(pnlDisplay.getSize());
    }//GEN-LAST:event_formComponentResized

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnLoadActionPerformed
    {//GEN-HEADEREND:event_btnLoadActionPerformed
        DataLoader loader = new DataLoader(svgFile.getPath());
        double size = Double.parseDouble(String.valueOf(cboCellSize.getSelectedItem()));
        loader.setSize(size);
        CAModel.setCells(loader.getGrid());
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

        if (worker == null || worker.isDone())
        {
            // simulation isn't running, start it
            this.btnStartActionPerformed(null);
        }
        else
        {
            // simulation is running, don't need to start it, just stimulate cell
            CAModel.stimulate(stimX, stimY);
        }
    }//GEN-LAST:event_pnlDisplayMouseClicked

    private void btnStepActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnStepActionPerformed
    {//GEN-HEADEREND:event_btnStepActionPerformed
        if (currentTime == 0)
        {
            resetSimulation();
        }
        lblStatus.setText("Stepped simulation at X: " + stimX + " Y: " + stimY);
        time = 1;
        runSimulation();
        btnStep.requestFocus();
    }//GEN-LAST:event_btnStepActionPerformed

    private void chkBoxVoltageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_chkBoxVoltageActionPerformed
    {//GEN-HEADEREND:event_chkBoxVoltageActionPerformed
        renderer.setSeriesVisible(0, chkBoxVoltage.isSelected());
    }//GEN-LAST:event_chkBoxVoltageActionPerformed

    private void chkBoxRecoveryActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_chkBoxRecoveryActionPerformed
    {//GEN-HEADEREND:event_chkBoxRecoveryActionPerformed
        renderer.setSeriesVisible(1, chkBoxRecovery.isSelected());
    }//GEN-LAST:event_chkBoxRecoveryActionPerformed

    private void cboBoxModelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cboBoxModelActionPerformed
    {//GEN-HEADEREND:event_cboBoxModelActionPerformed
        loadModelParameters();
    }//GEN-LAST:event_cboBoxModelActionPerformed

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
    private javax.swing.JComboBox cboBoxModel;
    private javax.swing.JComboBox cboCellSize;
    private javax.swing.JCheckBox chkBoxRecovery;
    private javax.swing.JCheckBox chkBoxVoltage;
    private javax.swing.JLabel lblModel;
    private javax.swing.JLabel lblResolution;
    private javax.swing.JLabel lblResolution1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTime;
    private javax.swing.JPanel pnlChart;
    private javax.swing.JPanel pnlChartContainer;
    private javax.swing.JPanel pnlControls;
    private javax.swing.JPanel pnlDataSource;
    private heartsim.gui.BinaryPlotPanel pnlDisplay;
    private javax.swing.JPanel pnlDisplayContainer;
    private javax.swing.JPanel pnlParameters;
    private javax.swing.JTextField txtFile;
    private javax.swing.JTextField txtTime;
    // End of variables declaration//GEN-END:variables
}
