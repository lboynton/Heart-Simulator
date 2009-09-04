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

        this.setDescription("Ventricles have moderate conduction velocity");
    }

    @Override
    public CellularAutomataModel loadParameters(CellularAutomataModel model)
    {
        if (model instanceof Nishiyama)
        {
            model.getParameter("N").setValue("6");
            model.getParameter("Delta 1").setValue("10");
            model.getParameter("Delta 2").setValue("8");
        }

        return model;
    }

    @Override
    public int getOrder()
    {
        return 8;
    }
}
