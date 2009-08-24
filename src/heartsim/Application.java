/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

import heartsim.cam.profile.Ventricles;
import heartsim.cam.profile.Atria;
import heartsim.cam.profile.Profile;
import heartsim.cam.CellularAutomataModel;
import heartsim.cam.Nishiyama;
import heartsim.cam.Tyson;
import heartsim.cam.profile.AtrioventricularNode;
import heartsim.cam.profile.Default;
import heartsim.cam.profile.Insulator;
import heartsim.cam.profile.InternodalFibres;
import heartsim.cam.profile.SinoatrialNode;
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
     * here.
     * @return
     */
    public List<CellularAutomataModel> getCAModels()
    {
        List<CellularAutomataModel> models = new ArrayList<CellularAutomataModel>();

        models.add(new Nishiyama());
        models.add(new Tyson());

        return models;
    }

    /**
     * Returns a list of profiles for different types of heart tissues. The
     * profiles have different parameter values for different cellular automata
     * models to match the type of heart tissue. New profiles should be added
     * here.
     * @return
     */
    public List<Profile> getTissueProfiles()
    {
        List<Profile> profiles = new ArrayList<Profile>();

        profiles.add(new Default());
        profiles.add(new Ventricles());
        profiles.add(new Atria());
        profiles.add(new SinoatrialNode());
        profiles.add(new AtrioventricularNode());
        profiles.add(new InternodalFibres());
        profiles.add(new Insulator());

        return profiles;
    }
}
