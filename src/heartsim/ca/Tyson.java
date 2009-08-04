/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.ca;

import heartsim.ca.parameter.CAModelIntParameter;

/**
 *
 * @author Lee Boynton
 */
public class Tyson extends CAModel
{
    public Tyson()
    {
        super("Tyson");

        // create parameters
        CAModelIntParameter r = new CAModelIntParameter(3);
        CAModelIntParameter vMax = new CAModelIntParameter(100);
        CAModelIntParameter vReco = new CAModelIntParameter(70);
        CAModelIntParameter vExci = new CAModelIntParameter(65);
        CAModelIntParameter gUp = new CAModelIntParameter(20);
        CAModelIntParameter gDown = new CAModelIntParameter(5);
        CAModelIntParameter k0Exci = new CAModelIntParameter(0);
        CAModelIntParameter k0Reco = new CAModelIntParameter(5);

        // add parameters
        this.setParameter("r", r);
        this.setParameter("V max", vMax);
        this.setParameter("V reco", vReco);
        this.setParameter("V exci", vExci);
        this.setParameter("g up", gUp);
        this.setParameter("g down", gDown);
        this.setParameter("k0 exci", k0Exci);
        this.setParameter("k0 reco", k0Reco);
    }

    @Override
    public void step()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void initCells()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void stimulate(int x, int y)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
