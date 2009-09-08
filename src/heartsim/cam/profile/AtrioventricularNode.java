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

        this.setDescription("The atrioventricular node delays the impulse from " +
                "the sinoatrial node so that the ventricles contract after they " +
                "have filled up with blood from the atria, which contract before " +
                "the ventricles.\n\n" +
                "Consequently, the sinoatrial node is very slow at conducting " +
                "waves. It also has a short action potential duration.");

        this.setImage("./help_pictures/profile_avnode.png");
    }

    @Override
    public CellularAutomataModel loadParameters(CellularAutomataModel model)
    {
        if(model instanceof Nishiyama)
        {
            model.getParameter("Delta 1").setValue("400");
            model.getParameter("Delta 2").setValue("410");
            model.getParameter("N").setValue("140");
        }

        if(model instanceof NishiyamaExtended)
        {
            model.getParameter("uUp").setValue("10");
            model.getParameter("uDown").setValue("5");
        }

        return model;
    }

    @Override
    public int getOrder()
    {
        return 4;
    }
}
