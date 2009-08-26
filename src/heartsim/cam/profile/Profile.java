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
    protected String description;
    protected List<CellularAutomataModel> models = Application.getInstance().getCAModels();
    protected List<String> aliases = new ArrayList<String>();

    public Profile(String name)
    {
        setName(name);

        for (CellularAutomataModel model : Application.getInstance().getCAModels())
        {
            loadParameters(model);
        }
    }

    public String getName()
    {
        // get element at start of list as this is designated as the name
        return aliases.get(0);
    }

    public void setName(String name)
    {
        // remove the first element if it exists as this is the old name
        if (aliases.size() > 0)
        {
            aliases.remove(0);
        }

        // add new name to start of list
        aliases.add(0, name);
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
        return aliases.get(0);
    }

    public List<String> getAliases()
    {
        return aliases;
    }

    /**
     * Adds an alias for this tissue. Note that the name of the tisse is by
     * default included in the aliases. Case in insensitive.
     * @param alias Alternative name for this tissue
     */
    public void addAlias(String alias)
    {
        aliases.add(alias);
    }
}
