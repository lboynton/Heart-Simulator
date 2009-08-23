/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.cam.parameter;

/**
 *
 * @author Lee Boynton
 */
public class CAModelIntParameter extends CAModelParameter
{
    private int value;

    public CAModelIntParameter(int value)
    {
        this.value = value;
    }

    @Override
    public boolean setValue(String value)
    {
        try
        {
            this.value = Integer.valueOf(value);
        }
        catch(java.lang.NumberFormatException ex)
        {
            return false;
        }

        return true;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public int getValueAsInt()
    {
        return value;
    }

    @Override
    public Object getValue()
    {
        return (Integer) value;
    }
}