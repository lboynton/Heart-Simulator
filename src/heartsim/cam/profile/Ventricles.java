/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.cam.profile;

import heartsim.cam.profile.Profile;
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
            model.getParameter("N").setValue("4");
        }

        return model;
    }
}
