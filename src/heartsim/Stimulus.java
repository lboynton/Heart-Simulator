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
    private int waitTime = 2000;

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

    public void setWaitTime(int waitTime)
    {
        Application.getInstance().output("Wait time set to " + waitTime);
        this.waitTime = waitTime;
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
                    Thread.sleep(waitTime);
                }
                catch (InterruptedException ex)
                {
                    Logger.getLogger(Stimulus.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
