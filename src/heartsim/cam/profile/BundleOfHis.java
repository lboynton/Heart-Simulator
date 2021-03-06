/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.cam.profile;

import heartsim.cam.CellularAutomataModel;
import heartsim.cam.Nishiyama;
import heartsim.cam.NishiyamaExtended;

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

        this.setImage("./help_pictures/profile_bundle_of_his.png");
    }

    @Override
    public CellularAutomataModel loadParameters(CellularAutomataModel model)
    {
        if (model instanceof Nishiyama)
        {
            model.getParameter("Delta 1").setValue("10");
            model.getParameter("Delta 2").setValue("20");
            model.getParameter("N").setValue("200");
        }

        if (model instanceof NishiyamaExtended)
        {
            model.getParameter("uUp").setValue("55");
            model.getParameter("uDown").setValue("30");
        }

        return model;
    }

    @Override
    public int getOrder()
    {
        return 5;
    }
}
