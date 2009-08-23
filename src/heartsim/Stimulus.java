/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lee Boynton
 */
public class Stimulus
{
    private boolean started = true;
    private int stimRow;
    private int stimCol;
    private CellularAutomaton ca;

    public Stimulus(CellularAutomaton ca)
    {
        this.ca = ca;
    }

    public void setCa(CellularAutomaton ca)
    {
        this.ca = ca;
    }

    public void setStimulatedCell(int row, int col)
    {
        this.stimRow = row;
        this.stimCol = col;
    }

    public void run()
    {
        Thread t = new Thread(new StimulusRunnable());
        t.start();
    }

    public void stop()
    {
        started = false;
    }

    public class StimulusRunnable implements Runnable
    {
        public void run()
        {
            while (started)
            {
                ca.stimulate(stimRow, stimCol);
                
                try
                {
                    // stimulate every 3 seconds
                    Thread.sleep(3000);
                }
                catch (InterruptedException ex)
                {
                    Logger.getLogger(Stimulus.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
