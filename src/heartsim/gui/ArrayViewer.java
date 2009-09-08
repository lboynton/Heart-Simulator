/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ArrayViewer.java
 *
 * Created on 06-Sep-2009, 17:15:22
 */
package heartsim.gui;

/**
 *
 * @author Lee Boynton
 */
public class ArrayViewer extends javax.swing.JDialog
{
    private int[][] array;

    /** Creates new form ArrayViewer */
    public ArrayViewer(java.awt.Frame parent, boolean modal, int[][] array)
    {
        super(parent, modal);
        this.array = array;
        initComponents();
    }

    public int[][] getArray()
    {
        return array;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        arrayPanel1 = new heartsim.gui.component.ArrayPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        arrayPanel1.setArray(this.getArray());
        arrayPanel1.setLayout(new java.awt.GridLayout(arrayPanel1.getArrayWidth(), arrayPanel1.getArrayWidth()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(arrayPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(arrayPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                int[][] array = new int[10][10];

                ArrayViewer dialog = new ArrayViewer(new javax.swing.JFrame(), true, array);

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
    private heartsim.gui.component.ArrayPanel arrayPanel1;
    // End of variables declaration//GEN-END:variables
}