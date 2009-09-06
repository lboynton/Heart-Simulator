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
            model.getParameter("Delta 1").setValue("90");
            model.getParameter("Delta 2").setValue("130");
            model.getParameter("N").setValue("200");
        }

        if (model instanceof NishiyamaExtended)
        {
            model.getParameter("uUp").setValue("51");
            model.getParameter("uDown").setValue("30");
        }

        return model;
    }

    @Override
    public int getOrder()
    {
        return 8;
    }
}
