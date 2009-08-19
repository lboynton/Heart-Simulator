/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

/**
 * Singleton class application settings and methods
 * @author Lee Boynton
 */
public class Application
{
    /** Singleton instance */
    private static Application parameters;

    private boolean debugMode = true;

    private Application()
    {
        // prevents public instantiation
    }

    public static synchronized Application getInstance()
    {
        if (parameters == null)
        {
            parameters = new Application();
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

    public void output(String text)
    {
        if (getInstance().isDebugMode())
        {
            System.out.println(text);
        }
    }
}
