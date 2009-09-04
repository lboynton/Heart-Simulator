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
public class PurkinjeFibres extends Profile
{
    public PurkinjeFibres()
    {
        super("Purkinje fibres");

        this.addAlias("Purkinje fibers");
        this.addAlias("Purkyne fibres");
        this.addAlias("Purkyne fibers");
        this.addAlias("Subendocardial branches");
    }

    @Override
    public CellularAutomataModel loadParameters(CellularAutomataModel model)
    {
        if(model instanceof Nishiyama)
        {
            model.getParameter("Delta 1").setValue("1");
            model.getParameter("Delta 2").setValue("2");
            model.getParameter("N").setValue("3");
        }

        return model;
    }

    @Override
    public int getOrder()
    {
        return 7;
    }
}