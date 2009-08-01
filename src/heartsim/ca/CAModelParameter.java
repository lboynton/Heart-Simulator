/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.ca;

/**
 *
 * @author Lee Boynton
 */
public class CAModelParameter<T>
{
    private String name;
    private T value;
    
    public CAModelParameter(T value)
    {
        this.value = value;
    }

    public String getName()
    {
        return name;
    }

    public T getValue()
    {
        return value;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}