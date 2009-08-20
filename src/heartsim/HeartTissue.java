/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

import heartsim.ca.CAModel;

/**
 * A class for different heart tissue
 * @author Lee Boynton
 */
public class HeartTissue
{
    protected CAModel model;
    protected String name;
    protected String description;

    public HeartTissue(String name)
    {
        this.name = name;
    }

    public HeartTissue(String name, String description)
    {
        this.name = name;
        this.description = description;
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
        return model;
    }

    public void setModel(CAModel model)
    {
        this.model = model;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}