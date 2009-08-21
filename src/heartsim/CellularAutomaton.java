/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

import heartsim.cam.CellularAutomataModel;
import heartsim.util.ArrayUtils;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lee Boynton
 */
public class CellularAutomaton
{
    private int height; // height of the grid
    private int width; // width of the grid
    private int u[][] = new int[height][width]; // voltage values for each cell
    private int v[][] = new int[height][width]; // recovery values for each cell
    private int tempu[][] = new int[height][width]; // voltage values for each cell
    private boolean cells[][] = new boolean[height][width]; // true/false if there is a cell
    private List<HeartTissue> tissues = new ArrayList<HeartTissue>();
    private String tissueNames[][];

    public boolean isCell(int row, int col)
    {
        try
        {
            return cells[row][col];
        }
        catch (IndexOutOfBoundsException ex)
        {
            return false;
        }
    }

    public void setTissues(List<HeartTissue> tissues)
    {
        this.tissues = tissues;
    }

    public void setTissueNames(String[][] tissueNames)
    {
        this.tissueNames = tissueNames;
    }

    public List<HeartTissue> getTissues()
    {
        return tissues;
    }

    /**
     * Progresses the simulation forward one time-step
     */
    public void step()
    {
        // copy voltage values into temporary array
        ArrayUtils.copy2DArray(u, tempu);

        for (int row = 1; row < cells.length - 1; row++)
        {
            for (int col = 1; col < cells[row].length - 1; col++)
            {
                if (!cells[row][col])
                {
                    continue;
                }

                for (HeartTissue tissue : tissues)
                {
                    if(tissue.getName().equals(tissueNames[row][col]))
                    {
                        tissue.getModel().processCell(row, col, u, v, tempu);
                    }
                }
            }
        }
    }

    public void initCells()
    {
        u = new int[height][width]; // voltage values for each cell
        v = new int[height][width]; // recovery values for each cell
        tempu = new int[height][width]; // voltage values for each cell

        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                // initialise voltage values
                if (isCell(row, col))
                {
                    u[row][col] = 0;
                }
                else
                {
                    u[row][col] = -1;
                }

                // initialise recovery values
                v[row][col] = 0;
            }
        }

        for (HeartTissue tissue : tissues)
        {
            tissue.getModel().setSize(height, width);
            tissue.getModel().initCells();
        }
    }

    /**
     * Sets the cell geometry
     * @param cells Boolean array which says if a cell is inside the heart or
     * outside
     */
    public void setCells(boolean[][] cells)
    {
        this.cells = cells;
        setSize(new Dimension(cells[0].length, cells.length));
    }

    /**
     * Sets the size of the grid
     * @param d
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

    public void printCells()
    {
        System.out.println("Cells:");

        for (int row = 0; row < cells.length; row++)
        {
            for (int col = 0; col < cells[0].length; col++)
            {
                if (cells[row][col])
                {
                    System.out.print("*");
                }
                else
                {
                    System.out.print(" ");
                }
            }

            System.out.println();
        }
    }

    /**
     * Stimulates a cell so that it starts an electrical wave
     * @param x
     * @param y
     * @return True if cell was stimulated, false if not
     */
    public boolean stimulate(int row, int col)
    {
        try
        {
            if (!isCell(row, col))
            {
                System.out.println("No cell at row: " + row + " col: " + col);
                return false;
            }

            for (HeartTissue tissue : tissues)
            {
                if (tissue.containsCell(row, col))
                {
                    tissue.getModel().processCell(row, col, u, v, tempu);

                    if (tissue.getModel().isCellRecovered(u[row][col], v[row][col]))
                    {
                        u[row][col] = tissue.getModel().getCellExcitationValue();
                    }
                }
            }
        }
        catch (java.lang.ArrayIndexOutOfBoundsException ex)
        {
            return false;
        }

        return true;
    }
}
