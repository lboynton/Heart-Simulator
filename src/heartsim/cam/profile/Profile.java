/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.cam.profile;

import heartsim.Application;
import heartsim.cam.CellularAutomataModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lee Boynton
 */
public abstract class Profile
{
    protected String name;
    protected String description;
    protected List<CellularAutomataModel> models = Application.getInstance().getCAModels();
    protected List<String> aliases = new ArrayList<String>();

    public Profile(String name)
    {
        setName(name);
        
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
        aliases.add(name);
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public abstract CellularAutomataModel loadParameters(CellularAutomataModel model);

    @Override
    public String toString()
    {
        return name;
    }

    public List<String> getAliases()
    {
        return aliases;
    }

    public void addAlias(String alias)
    {
        aliases.add(name);
    }
}
