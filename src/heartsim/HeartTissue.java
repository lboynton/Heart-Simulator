/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

import heartsim.ca.CAModel;
import org.apache.batik.ext.awt.geom.ExtendedGeneralPath;

/**
 * A class for different heart tissue
 * @author Lee Boynton
 */
public class HeartTissue
{
    protected CAModel model;
    protected ExtendedGeneralPath outline;
    protected String name;
    protected String description;

    public HeartTissue(ExtendedGeneralPath outline, String name)
    {
        this.outline = outline;
        this.name = name;
    }

    public HeartTissue(ExtendedGeneralPath outline, String name, String description)
    {
        this.outline = outline;
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

    public ExtendedGeneralPath getOutline()
    {
        return outline;
    }

    public void setOutline(ExtendedGeneralPath outline)
    {
        this.outline = outline;
    }
}
