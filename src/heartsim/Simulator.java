/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

import heartsim.ca.CAModel;
import heartsim.gui.BinaryPlotPanelOverlay;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Lee Boynton
 */
public class Simulator
{
    private List<SimulatorListener> listeners = Collections.synchronizedList(new ArrayList<SimulatorListener>());
    private CAModel caModel;
    private BinaryPlotPanelOverlay overlay;
    private boolean initialised = false;
    private int runTime = 500;

    private enum State
    {
        STOPPED, STARTED
    };

    private State state = State.STOPPED;

    public Simulator(CAModel caModel, BinaryPlotPanelOverlay overlay)
    {
        this.caModel = caModel;
        this.overlay = overlay;
    }

    public void setModel(CAModel model)
    {
        this.caModel = model;
    }

    public void setRunTime(int runTime)
    {
        this.runTime = runTime;
    }

    public void setStimulatedCell(int row, int col)
    {
        initialiseCAModel();
        caModel.stimulate(row, col);
    }

    private void initialiseCAModel()
    {
        if (!initialised)
        {
            caModel.initCells();
        }

        initialised = true;
    }

    public void run()
    {
        initialiseCAModel();

        if(state == State.STOPPED)
        {
            state = State.STARTED;
            Thread t = new Thread(new SimulatorRunnable());
            t.start();
        }
    }

    public void stop()
    {
        state = State.STOPPED;
        initialised = false;
    }

    public void pause()
    {
        state = State.STOPPED;
    }

    public void addListener(SimulatorListener listener)
    {
        listeners.add(listener);
    }

    public void removeListener(SimulatorListener listener)
    {
        if (listeners.contains(listener))
        {
            listeners.remove(listener);
        }
    }

    private void fireSimulationUpdated()
    {
        for (SimulatorListener listener : listeners)
        {
            listener.simulationUpdated();
        }
    }

    public class SimulatorRunnable implements Runnable
    {
        public void run()
        {
            int[] data = overlay.getBuffer();

            int[][] u = caModel.getU();

            for (int t = 0; t < runTime; t++)
            {
                if(state == State.STOPPED)
                {
                    break;
                }
                
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

                caModel.step();
                fireSimulationUpdated();
            }

            state = State.STOPPED;
        }
    }
}
