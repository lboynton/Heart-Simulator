/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.cam.profile;

import heartsim.cam.Nishiyama;
import heartsim.cam.CellularAutomataModel;

/**
 *
 * @author Lee Boynton
 */
public class Insulator extends Profile
{
    public Insulator()
    {
        super("Insulator");

        setDescription("The insulator profile does not allow an electrical wave" +
                "to pass through it");
    }

    @Override
    public CellularAutomataModel loadParameters(CellularAutomataModel model)
    {
        if(model instanceof Nishiyama)
        {
            model.getParameter("N").setValue("1");
            model.getParameter("Delta 1").setValue("2");
            model.getParameter("Delta 2").setValue("2");
        }

        return model;
    }
}
