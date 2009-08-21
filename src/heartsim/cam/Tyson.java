/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.cam;

import heartsim.cam.parameter.CAModelIntParameter;
import heartsim.util.ArrayUtils;
import java.awt.Dimension;

/**
 *
 * @author Lee Boynton
 */
public class Tyson extends CellularAutomataModel
{
    private int tempu[][] = new int[height][width]; // temporary storage of cell values
    private int tempv[][] = new int[height][width]; // temporary storage of cell values

    public Tyson()
    {
        super("Tyson");

        this.setDescription("An implementation of the cellular automaton model " +
                "by Martin Gerhardt, Heike Schuster and John J. Tyson presented " +
                "in \"A Cellular Automaton Model of Excitable Media Including " +
                "Curvature and Dispersion\".");

        // create parameters
        CAModelIntParameter r = new CAModelIntParameter(3);
        CAModelIntParameter vMax = new CAModelIntParameter(100);
        CAModelIntParameter vReco = new CAModelIntParameter(70);
        CAModelIntParameter vExci = new CAModelIntParameter(65);
        CAModelIntParameter gUp = new CAModelIntParameter(20);
        CAModelIntParameter gDown = new CAModelIntParameter(5);
        CAModelIntParameter k0Exci = new CAModelIntParameter(0);
        CAModelIntParameter k0Reco = new CAModelIntParameter(5);

        r.setDescription("The radius of the neighbourhood. Larger neighbourhoods " +
                "give finer spatial resolution to the automaton. As r increases, " +
                "so too do the number of grid points per unit length.");
        vMax.setDescription("Determines the maximum value of v");
        gUp.setDescription("The amount the recovery value increases by until it " +
                "reaches v max");
        gDown.setDescription("The amount the recovery value decreases until it " +
                "reaches 0");
        k0Exci.setDescription("Reflects the excitability of the medium. Smaller " +
                "values represent higher excitabilities");

        // add parameters
        this.setParameter("r", r);
        this.setParameter("V max", vMax);
        this.setParameter("V reco", vReco);
        this.setParameter("V exci", vExci);
        this.setParameter("g up", gUp);
        this.setParameter("g down", gDown);
        this.setParameter("k0 exci", k0Exci);
        this.setParameter("k0 reco", k0Reco);
    }

//    @Override
//    public void step()
//    {
//        int gUp = ((CAModelIntParameter) this.getParameter("g up")).getValueAsInt();
//        int vMax = ((CAModelIntParameter) this.getParameter("V max")).getValueAsInt();
//        int gDown = ((CAModelIntParameter) this.getParameter("g down")).getValueAsInt();
//
//        // copy voltage values into temporary array
//        ArrayUtils.copy2DArray(u, tempu);
//        ArrayUtils.copy2DArray(v, tempv);
//
//        for (int row = 1; row < cells.length - 1; row++)
//        {
//            for (int col = 1; col < cells[row].length - 1; col++)
//            {
//                if (tempu[row][col] == 1)
//                {
//                    // cell is in an excited state
//                    v[row][col] = Math.min(tempv[row][col] + gUp, vMax);
//                }
//                if (tempu[row][col] == 0)
//                {
//                    // cell is in a recovery state
//                    v[row][col] = Math.max(tempv[row][col] - gDown, 0);
//                }
//                if (tempu[row][col] == 0)
//                {
//                    // cell is recovered
//                    u[row][col] = 0;
//                }
//                if (tempu[row][col] == 1 && tempv[row][col] != vMax)
//                {
//                    // cell is becoming excited
//                    u[row][col] = 1;
//                }
//                if (tempu[row][col] == 1 && tempv[row][col] == vMax)
//                {
//                    // cell has finished being excited
//                    u[row][col] = 0;
//                }
//            }
//        }
//    }

    private int getExcitedCellsInNeighbourhood(int x, int y)
    {
        int r = ((CAModelIntParameter) this.getParameter("r")).getValueAsInt();

        return 0;
    }

    @Override
    public void initCells()
    {
        System.out.println("Init cells");

        tempu = new int[height][width]; // temporary storage of cell values
        tempv = new int[height][width]; // temporary storage of cell values

        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
            }
        }
    }

//    @Override
//    public boolean stimulate(int x, int y)
//    {
//        try
//        {
//            u[x][y] = 1;
//            u[x - 1][y] = 1;
//            u[x + 1][y] = 1;
//            u[x][y - 1] = 1;
//            u[x][y + 1] = 1;
//            u[x - 1][y - 1] = 1;
//            u[x + 1][y + 1] = 1;
//            u[x - 1][y + 1] = 1;
//            u[x + 1][y - 1] = 1;
//        }
//        catch (ArrayIndexOutOfBoundsException e)
//        {
//            //e.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }

    @Override
    public int getMax()
    {
        return (Integer) this.getParameter("V max").getValue();
    }

    @Override
    public int getMin()
    {
        return 0;
    }

    @Override
    public void printArrays()
    {
        String[] names =
        {
            "Voltage", "Recovery"
        };
        
        int[][][] arrays = new int[2][][];

        arrays[0] = tempu;
        arrays[1] = tempv;

        printArrays(names, arrays);
    }

    @Override
    public boolean isCellRecovered(int u, int v)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getCellExcitationValue()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int processCell(int row, int col, int[][] u, int[][] v, int[][] tempu)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}