/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

import heartsim.cam.CellularAutomataModel;
import heartsim.cam.profile.Default;
import heartsim.cam.profile.Profile;
import heartsim.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;

/**
 * A class for different heart tissue
 * @author Lee Boynton
 */
public class HeartTissue implements Comparable
{
    protected CellularAutomataModel currentModel;
    protected String name;
    protected String description;
    protected List<Element> elements = new ArrayList<Element>();
    protected Profile profile;

    public HeartTissue(String name)
    {
        this(name, "");
    }

    public HeartTissue(String name, String description)
    {
        this.description = description;
        setName(name);

        if (Application.getInstance().getCAModels().size() > 0)
        {
            currentModel = Application.getInstance().getCAModels().get(0);
        }

        profile = getDetectedProfile();
    }

    private Profile getDetectedProfile()
    {
        for (Profile p : Application.getInstance().getTissueProfiles())
        {
            for (String alias : p.getAliases())
            {
                if(alias.equalsIgnoreCase(name))
                {
                    Application.getInstance().output("Selecting " + p.getName() + " profile for tissue " + name);
                    p.loadParameters(currentModel);
                    return p;
                }
            }
        }

        // could not detect profile so use generic default
        Profile p = new Default();

        Application.getInstance().output("Could not detect profile for " + name + ", using default profile instead");

        return p;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public CellularAutomataModel getModel()
    {
        return currentModel;
    }

    public void setModel(CellularAutomataModel model)
    {
        Application.getInstance().output("Setting " + name + " to use " + model.getName() + " model");
        this.currentModel = model;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        name = StringUtils.removeNewLines(name);
        name = StringUtils.removeTabs(name);
        name = name.trim();
        
        this.name = name;
    }

    public List<Element> getElements()
    {
        return elements;
    }

    public void setElements(List<Element> elements)
    {
        this.elements = elements;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public Profile getProfile()
    {
        return profile;
    }

    public void setProfile(Profile profile)
    {
        this.profile = profile;
        this.setModel(profile.loadParameters(this.getModel()));
    }

    @Override
    public boolean equals(Object obj)
    {
        return ((HeartTissue)obj).getName().equals(this.getName());
    }

    public int compareTo(Object o)
    {
        return this.getDetectedProfile().getOrder() - ((HeartTissue)o).getProfile().getOrder();
    }
}
