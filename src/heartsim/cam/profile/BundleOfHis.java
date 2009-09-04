/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.cam.profile;

import heartsim.cam.CellularAutomataModel;
import heartsim.cam.Nishiyama;

/**
 *
 * @author Lee Boynton
 */
public class BundleOfHis extends Profile
{
    public BundleOfHis()
    {
        super("Bundle of His");

        this.addAlias("AV bundle");
        this.addAlias("Atrioventricular bundle");
    }

    @Override
    public CellularAutomataModel loadParameters(CellularAutomataModel model)
    {
        if(model instanceof Nishiyama)
        {
            model.getParameter("Delta 1").setValue("3");
            model.getParameter("Delta 2").setValue("7");
            model.getParameter("N").setValue("6");
        }

        return model;
    }

    @Override
    public int getOrder()
    {
        return 5;
    }
}
