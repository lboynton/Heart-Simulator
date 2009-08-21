/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.cam.profle;

import heartsim.*;
import heartsim.cam.CellularAutomataModel;
import java.util.List;

/**
 *
 * @author Lee Boynton
 */
public abstract class Profile
{
    protected String name;
    protected List<CellularAutomataModel> models = Application.getInstance().getCAModels();

    public Profile(String name)
    {
        this.name = name;
        
        for(CellularAutomataModel model:Application.getInstance().getCAModels())
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

    public abstract CellularAutomataModel loadParameters(CellularAutomataModel model);

    @Override
    public String toString()
    {
        return name;
    }
}
