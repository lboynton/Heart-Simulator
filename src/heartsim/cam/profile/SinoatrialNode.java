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
            model.getParameter("Delta 1").setValue("180");
            model.getParameter("Delta 2").setValue("220");
            model.getParameter("N").setValue("200");
        }

        if(model instanceof NishiyamaExtended)
        {
            model.getParameter("uUp").setValue("100");
            model.getParameter("uDown").setValue("10");
        }

        return model;
    }
}
