/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

/**
 * Singleton class for getting and setting global application parameters
 * @author Lee Boynton
 */
public class ApplicationParameters
{
    /** Singleton instance */
    private static ApplicationParameters parameters;

    private boolean debugMode = true;

    private ApplicationParameters()
    {
        // prevents public instantiation
    }

    public static synchronized ApplicationParameters getInstance()
    {
        if (parameters == null)
        {
            parameters = new ApplicationParameters();
        }

        return parameters;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException();
    }

    public boolean isDebugMode()
    {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode)
    {
        this.debugMode = debugMode;
    }
}
