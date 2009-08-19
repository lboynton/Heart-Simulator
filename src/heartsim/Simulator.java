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
public class Simulator implements Runnable
{
    private List<SimulatorListener> listeners = Collections.synchronizedList(new ArrayList<SimulatorListener>());
    private CAModel caModel;
    private BinaryPlotPanelOverlay overlay;
    private boolean initialised = false;

    public Simulator(CAModel caModel, BinaryPlotPanelOverlay overlay)
    {
        this.caModel = caModel;
        this.overlay = overlay;
    }

    public void setModel(CAModel model)
    {
        this.caModel = model;
    }

    public void setStimulatedCell(int x, int y)
    {
        caModel.stimulate(x, y);
    }

    public void run()
    {
        if (!initialised)
        {
            caModel.initCells();
        }
        else
        {
            initialised = true;
        }

        Runnable r = new Runnable()
        {
            public void run()
            {
                int[] data = overlay.getBuffer();

                int[][] u = caModel.getU();

                for (int t = 0; t < 500; t++)
                {
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
            }
        };

        Thread t = new Thread(r);
        t.start();
    }

    public void stop()
    {
    }

    public void reset()
    {
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
}
