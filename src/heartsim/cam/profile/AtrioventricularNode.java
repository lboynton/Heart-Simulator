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
public class AtrioventricularNode extends Profile
{
    public AtrioventricularNode()
    {
        super("Atrioventricular node");

        this.addAlias("avnode");
    }

    @Override
    public CellularAutomataModel loadParameters(CellularAutomataModel model)
    {
        if(model instanceof Nishiyama)
        {
            model.getParameter("Delta 1").setValue("400");
            model.getParameter("Delta 2").setValue("450");
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
