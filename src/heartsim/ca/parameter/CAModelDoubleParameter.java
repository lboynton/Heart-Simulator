/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.ca.parameter;

/**
 *
 * @author Lee Boynton
 */
public class CAModelDoubleParameter extends CAModelParameter
{
    private double value;

    public CAModelDoubleParameter(double value)
    {
        this.value = value;
    }

    @Override
    public boolean setValue(String value)
    {
        try
        {
            this.value = Double.valueOf(value);
        }
        catch(java.lang.NumberFormatException ex)
        {
            return false;
        }

        return true;
    }

    public void setValue(double value)
    {
        this.value = value;
    }

    public double getValueAsDouble()
    {
        return value;
    }

    @Override
    public Object getValue()
    {
        return (Double) value;
    }
}