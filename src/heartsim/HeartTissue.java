/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

import heartsim.ca.CAModel;
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
    protected CAModel currentModel;
    protected String name;
    protected String description;
    protected Shape shape;
    protected Element element;
    protected List<CAModel> availableModels = new ArrayList<CAModel>();

    public HeartTissue(String name)
    {
        this.name = StringUtils.prettify(name);
        availableModels.addAll(Application.getInstance().getCAModels());

        if (availableModels.size() > 0)
        {
            currentModel = availableModels.get(0);
        }
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
    }

    public List<CAModel> getAvailableModels()
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

    public CAModel getModel()
    {
        return currentModel;
    }

    public void setModel(CAModel model)
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
