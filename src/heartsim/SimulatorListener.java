/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

/**
 *
 * @author Lee Boynton
 */
public interface SimulatorListener
{
    public void simulationStarted();
    public void simulationPaused();
    public void simulationCompleted();
    public void simulationReset();
    public void simulationUpdated();
}
