/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

import heartsim.cam.CellularAutomataModel;
import heartsim.cam.profile.Profile;
import heartsim.util.StringUtils;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;

/**
 * A class for different heart tissue
 * @author Lee Boynton
 */
public class HeartTissue
{
    protected CellularAutomataModel currentModel;
    protected String name;
    protected String description;
    protected Shape shape;
    protected Element element;
    protected List<CellularAutomataModel> availableModels = new ArrayList<CellularAutomataModel>();
    protected Profile profile;

    public HeartTissue(String name)
    {
        this.name = StringUtils.prettify(name);
        availableModels.addAll(Application.getInstance().getCAModels());

        if (availableModels.size() > 0)
        {
            currentModel = availableModels.get(0);
        }

        detectProfile();
    }

    public HeartTissue(String name, String description)
    {
        this.name = StringUtils.prettify(name);
        this.description = description;
        availableModels.addAll(Application.getInstance().getCAModels());

        if (availableModels.size() > 0)
        {
            currentModel = availableModels.get(0);
        }

        detectProfile();
    }

    private void detectProfile()
    {
        for (Profile p : Application.getInstance().getTissueProfiles())
        {
            for (String alias : p.getAliases())
            {
                if(alias.equals(name))
                {
                    Application.getInstance().output("Selecting " + p.getName() + " profile for element " + name);
                    profile = p;
                    p.loadParameters(currentModel);
                    return;
                }
            }
        }

        Application.getInstance().output("Could not detect profile for " + name + ", using default profile instead");
    }

    public List<CellularAutomataModel> getAvailableModels()
    {
        return availableModels;
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
        this.currentModel = model;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = StringUtils.prettify(name);
    }

    public Element getElement()
    {
        return element;
    }

    public void setElement(Element element)
    {
        this.element = element;
    }

    public void setShape(Shape shape)
    {
        this.shape = shape;
    }

    public boolean containsCell(int row, int col)
    {
        return shape.contains(col, row);
    }

    @Override
    public String toString()
    {
        return name;
    }
}
