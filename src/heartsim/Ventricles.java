/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

import heartsim.ca.CAModel;
import heartsim.ca.Nishiyama;

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
    public CAModel loadParameters(CAModel model)
    {
        if (model instanceof Nishiyama)
        {
            model.getParameter("N").setValue("4");
        }

        return model;
    }
}
