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
public class SinoatrialNode extends Profile
{
    public SinoatrialNode()
    {
        // set name of the profile
        super("Sinoatrial node");

        // set the aliases of the heart tissue
        this.addAlias("SA node");
        this.addAlias("Sinus node");
    }

    @Override
    public CellularAutomataModel loadParameters(CellularAutomataModel model)
    {
        if(model instanceof Nishiyama)
        {
            model.getParameter("Delta 1").setValue("3");
            model.getParameter("Delta 2").setValue("7");
            model.getParameter("N").setValue("6");
        }

        return model;
    }
}
