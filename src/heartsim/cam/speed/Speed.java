/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.cam.speed;

/**
 *
 * @author Lee Boynton
 */
public abstract class Speed
{
    protected int delay;
    protected String name;

    public Speed(int delay, String name)
    {
        this.delay = delay;
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public int getDelay()
    {
        return delay;
    }
}
