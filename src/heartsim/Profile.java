/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

import heartsim.ca.CAModel;
import java.util.List;

/**
 *
 * @author Lee Boynton
 */
public abstract class Profile
{
    protected String name;
    protected List<CAModel> models = Application.getInstance().getCAModels();

    public Profile(String name)
    {
        this.name = name;
        
        for(CAModel model:Application.getInstance().getCAModels())
        {
            loadParameters(model);
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public abstract CAModel loadParameters(CAModel model);

    @Override
    public String toString()
    {
        return name;
    }
}
