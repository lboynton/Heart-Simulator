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
public class InternodalFibres extends Profile
{
    public InternodalFibres()
    {
        super("Internodal fibres");
    }

    @Override
    public CellularAutomataModel loadParameters(CellularAutomataModel model)
    {
        if(model instanceof Nishiyama)
        {
            // has to be made slower to take into account the fibres go around
            // the atrial walls, despite them really being marginally faster
            // than the atrial cells
            model.getParameter("Delta 1").setValue("180");
            model.getParameter("Delta 2").setValue("140");
            model.getParameter("N").setValue("120");
        }

        if(model instanceof NishiyamaExtended)
        {
            model.getParameter("uUp").setValue("20");
            model.getParameter("uDown").setValue("1");
        }

        return model;
    }

    @Override
    public int getOrder()
    {
        return 3;
    }
}
