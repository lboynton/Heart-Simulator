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
            model.getParameter("N").setValue("120");
            model.getParameter("Delta 1").setValue("80");
            model.getParameter("Delta 2").setValue("60");
        }

        if(model instanceof NishiyamaExtended)
        {
            model.getParameter("uUp").setValue("10");
            model.getParameter("uDown").setValue("10");
        }

        return model;
    }

    @Override
    public int getOrder()
    {
        return 3;
    }
}
