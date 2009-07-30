/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.ca;

import java.awt.Dimension;

/**
 * Base class for cellular automata models
 * @author Lee Boynton
 */
public abstract class CAModel
{
    /**
     * Get the name of the cellular automata model
     * @return Name
     */
    public abstract String getName();

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
    public abstract void setCells(boolean[][] cells);

    /**
     * Sets the size of the grid
     * @param dimension
     */
    public abstract void setSize(Dimension dimension);

    /**
     * Gets a list of the parameters this cellular automata model takes
     * @return List of parameter names
     */
    public abstract String[] getParameterList();
}