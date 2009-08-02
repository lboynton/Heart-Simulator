/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.ca;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Base class for cellular automata models
 * @author Lee Boynton
 */
public abstract class CAModel
{
    protected String name; // name of the CA model
    protected String description; // description of the CA model
    protected int height; // height of the grid
    protected int width; // width of the grid
    protected int u[][] = new int[height][width]; // voltage values for each cell
    protected int v[][] = new int[height][width]; // recovery values for each cell
    protected boolean cells[][] = new boolean[height][width]; // true/false if there is a cell
    protected Map<String, CAModelParameter> parameters = new TreeMap<String, CAModelParameter>();

    protected CAModel(String name)
    {
        this.name = name;
    }
    
    /**
     * Get the name of the cellular automata model
     * @return Name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets a list of the parameters this cellular automata model takes
     * @return List of parameter names
     */
    public Map<String, CAModelParameter> getParameters()
    {
        return parameters;
    }

    public void setParameter(String name, CAModelParameter initialValue)
    {
        initialValue.setName(name);
        parameters.put(name, initialValue);
    }

    public CAModelParameter getParameter(String name)
    {
        return parameters.get(name);
    }

    /**
     * Steps the simulation
     */
    public abstract void step();

    /**
     * Initialises the cells
     */
    public abstract void initCells();

    /**
     * Stimulates a cell so that it starts an electrical wave
     * @param x
     * @param y
     */
    public abstract void stimulate(int x, int y);

    /**
     * Sets the cell geometry
     * @param cells Boolean array which says if a cell is inside the heart or
     * outside
     */
    public void setCells(boolean[][] cells)
    {
        this.cells = cells;
    }

    /**
     * Sets the size of the grid
     * @param dimension
     */
    public void setSize(Dimension d)
    {
        height = d.height;
        width = d.width;
    }

    /**
     * Gets the voltage values
     * @return Voltages
     */
    public int[][] getU()
    {
        return u;
    }

    /**
     * Gets the recovery value at a specific cell location
     * @param x X-axis value of the cell location
     * @param y Y-axis value of the cell location
     * @return Recovery value of the cell
     */
    public int getV(int x, int y)
    {
        return v[x][y];
    }

    @Override
    public String toString()
    {
        return name;
    }
}