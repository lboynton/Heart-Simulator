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
public class Default extends Profile
{
    public Default()
    {
        super("None");
    }

    @Override
    public CellularAutomataModel loadParameters(CellularAutomataModel model)
    {
        if(model instanceof Nishiyama)
        {
            model.getParameter("N").setValue("5");
            model.getParameter("Delta 1").setValue("3");
            model.getParameter("Delta 2").setValue("7");
        }

        return model;
    }
}
