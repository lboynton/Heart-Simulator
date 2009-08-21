/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

import heartsim.ca.CAModel;
import heartsim.ca.Nishiyama;
import heartsim.ca.Tyson;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Gets all the CA models in use by the program. New models should be added
     * here
     * @return
     */
    public List<CAModel> getCAModels()
    {
        List<CAModel> models = new ArrayList<CAModel>();

        models.add(new Nishiyama());
        models.add(new Tyson());

        return models;
    }
}
