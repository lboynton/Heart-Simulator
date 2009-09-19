/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChartDialog.java
 *
 * Created on 27-Aug-2009, 16:30:44
 */
package heartsim.gui;

import heartsim.gui.component.ActionPotentialChart;
import heartsim.gui.component.TissueSeries;
import heartsim.gui.component.TissueSeriesListener;
import heartsim.gui.component.TissueSeriesRenderer;
import java.awt.Frame;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.UIManager;

/**
 *
 * @author Lee Boynton
 */
public class ChartDialog extends javax.swing.JDialog implements TissueSeriesListener
{
    private ChartDialogEvent listener;
    private DefaultListModel tissuesModel = new DefaultListModel();

    /** Creates new form ChartDialog */
    private ChartDialog(java.awt.Frame parent, boolean modal)
    {
        this(parent, modal, null);
    }

    public ChartDialog(Frame owner, boolean modal, ChartDialogEvent listener)
    {
        super(owner, modal);
        this.listener = listener;

        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {
            System.err.println("Unable to use system look and feel");
        }

        initComponents();

        chart.addListener(this);
    }

    public void setListener(ChartDialogEvent listener)
    {
        this.listener = listener;
    }

    public ActionPotentialChart getChart()
    {
        return chart;
    }

    public ListModel getTissuesModel()
    {
        return tissuesModel;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chart = new heartsim.gui.component.ActionPotentialChart();
        btnClose = new javax.swing.JButton();
        chkBoxVoltage = new javax.swing.JCheckBox();
        chkBoxRecovery = new javax.swing.JCheckBox();
        btnReset = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstTissues = new javax.swing.JList();
        bntAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Action Potentials");

        javax.swing.GroupLayout chartLayout = new javax.swing.GroupLayout(chart);
        chart.setLayout(chartLayout);
        chartLayout.setHorizontalGroup(
            chartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 573, Short.MAX_VALUE)
        );
        chartLayout.setVerticalGroup(
            chartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 366, Short.MAX_VALUE)
        );

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        chkBoxVoltage.setSelected(true);
        chkBoxVoltage.setText("Voltage");
        chkBoxVoltage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBoxVoltageActionPerformed(evt);
            }
        });

        chkBoxRecovery.setSelected(true);
        chkBoxRecovery.setText("Recovery");
        chkBoxRecovery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBoxRecoveryActionPerformed(evt);
            }
        });

        btnReset.setText("Reset View");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        lstTissues.setModel(getTissuesModel());
        jScrollPane1.setViewportView(lstTissues);

        bntAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/heartsim/gui/icon/list-add.png"))); // NOI18N
        bntAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntAddActionPerformed(evt);
            }
        });

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/heartsim/gui/icon/list-remove.png"))); // NOI18N
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        jToggleButton1.setText("Select cell");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bntAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRemove)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jToggleButton1))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnReset)
                        .addGap(10, 10, 10)
                        .addComponent(chkBoxVoltage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkBoxRecovery)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 460, Short.MAX_VALUE)
                        .addComponent(btnClose)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jToggleButton1)
                            .addComponent(btnRemove)
                            .addComponent(bntAdd))
                        .addGap(7, 7, 7)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE))
                    .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkBoxVoltage)
                    .addComponent(chkBoxRecovery)
                    .addComponent(btnClose)
                    .addComponent(btnReset))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCloseActionPerformed
    {//GEN-HEADEREND:event_btnCloseActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnCloseActionPerformed

    private void chkBoxVoltageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_chkBoxVoltageActionPerformed
    {//GEN-HEADEREND:event_chkBoxVoltageActionPerformed
        chart.setVoltageEnabled(chkBoxVoltage.isSelected());
    }//GEN-LAST:event_chkBoxVoltageActionPerformed

    private void chkBoxRecoveryActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_chkBoxRecoveryActionPerformed
    {//GEN-HEADEREND:event_chkBoxRecoveryActionPerformed
        chart.setRecoveryEnabled(chkBoxRecovery.isSelected());
    }//GEN-LAST:event_chkBoxRecoveryActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnResetActionPerformed
    {//GEN-HEADEREND:event_btnResetActionPerformed
        chart.setRanges();
    }//GEN-LAST:event_btnResetActionPerformed

    private void bntAddActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bntAddActionPerformed
    {//GEN-HEADEREND:event_bntAddActionPerformed
        chart.addTissue(0, 0, "New cell");
    }//GEN-LAST:event_bntAddActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnRemoveActionPerformed
    {//GEN-HEADEREND:event_btnRemoveActionPerformed
        chart.removeTissue((TissueSeries) lstTissues.getSelectedValue());
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jToggleButton1ActionPerformed
    {//GEN-HEADEREND:event_jToggleButton1ActionPerformed
        if(jToggleButton1.isSelected())
        {
            listener.setCellSelectionMode();
        }
        else
        {
            listener.cancelCellSelectionMode();
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                ChartDialog dialog = new ChartDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter()
                {
                    public void windowClosing(java.awt.event.WindowEvent e)
                    {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntAdd;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnReset;
    private heartsim.gui.component.ActionPotentialChart chart;
    private javax.swing.JCheckBox chkBoxRecovery;
    private javax.swing.JCheckBox chkBoxVoltage;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JList lstTissues;
    // End of variables declaration//GEN-END:variables

    public void tissueAdded(TissueSeries tissue)
    {
        tissuesModel.addElement(tissue);
    }

    public void tissueRemoved(TissueSeries tissue)
    {
        tissuesModel.removeElement(tissue);
    }
}