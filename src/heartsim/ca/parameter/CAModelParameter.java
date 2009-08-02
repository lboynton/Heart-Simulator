/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.ca.parameter;

/**
 *
 * @author Lee Boynton
 */
public abstract class CAModelParameter
{
    protected String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public abstract boolean setValue(String value);

    public abstract Object getValue();
}
