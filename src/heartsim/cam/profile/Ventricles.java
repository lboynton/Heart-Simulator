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
public class Ventricles extends Profile
{
    public Ventricles()
    {
        super("Ventricles");
    }

    @Override
    public CellularAutomataModel loadParameters(CellularAutomataModel model)
    {
        if (model instanceof Nishiyama)
        {
            model.getParameter("Delta 1").setValue("8");
            model.getParameter("Delta 2").setValue("12");
            model.getParameter("N").setValue("11");
        }

        return model;
    }
}
