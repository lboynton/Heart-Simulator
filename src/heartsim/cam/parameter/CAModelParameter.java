/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.cam.parameter;

/**
 * A base wrapper class for individual parameters for cellular automata model
 * objects.
 *
 * @author Lee Boynton
 */
public abstract class CAModelParameter
{
    /**
     * Name of this CA model parameter
     */
    protected String name;

    /**
     * Description of this CA model parameter
     */
    protected String description;

    /**
     * Gets the name of the CA model parameter
     * @return Name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the CA model parameter
     * @param name Name of the parameter
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the description of the parameter
     * @return Description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description of the parameter
     * @param description Description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Sets the value of the parameter from a String
     * @param value Value
     * @return True if value was saved, false if the value could not be
     * converted to the correct type
     */
    public abstract boolean setValue(String value);

    /**
     * Gets the value of the parameter wrapped as an Object. The actual type
     * returned depends on the parameter subclass used.
     * @return Value of the parameter
     */
    public abstract Object getValue();

    //public abstract Image getImage();
}
