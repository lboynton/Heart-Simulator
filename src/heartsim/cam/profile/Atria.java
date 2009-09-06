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

        this.setDescription("The atrial walls have a slow conduction velocity " +
                "and short action potential duration.");

        this.setImage("./help_pictures/profile_atria.png");
    }

    @Override
    public CellularAutomataModel loadParameters(CellularAutomataModel model)
    {
        if (model instanceof Nishiyama)
        {
            model.getParameter("Delta 1").setValue("80");
            model.getParameter("Delta 2").setValue("120");
            model.getParameter("N").setValue("260");
        }

        if(model instanceof NishiyamaExtended)
        {
            model.getParameter("uUp").setValue("51");
            model.getParameter("uDown").setValue("5");
        }

        return model;
    }

    @Override
    public int getOrder()
    {
        return 2;
    }
}
