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
public class Atria extends Profile
{
    public Atria()
    {
        super("Atria");
    }

    @Override
    public CellularAutomataModel loadParameters(CellularAutomataModel model)
    {
        if (model instanceof Nishiyama)
        {
            model.getParameter("Delta 1").setValue("80");
            model.getParameter("Delta 2").setValue("120");
            model.getParameter("N").setValue("150");
        }

        if(model instanceof NishiyamaExtended)
        {
            model.getParameter("uUp").setValue("10");
            model.getParameter("uDown").setValue("10");
        }

        return model;
    }
}
