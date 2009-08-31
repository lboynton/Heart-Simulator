/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.cam;

/**
 *
 * @author Lee Boynton
 */
public class None extends CellularAutomataModel
{
    public None()
    {
        super("None");

        this.setDescription("The selected heart tissue will not use any cellular" +
                "automata model. This effectively stops the tissue from conducting" +
                "a wave.");
    }

    @Override
    public void initCells()
    {
    }

    @Override
    public void printArrays()
    {
    }

    @Override
    public int getMax()
    {
        return 0;
    }

    @Override
    public int getMin()
    {
        return 0;
    }

    @Override
    public boolean isCellRecovered(int u, int v)
    {
        return false;
    }

    @Override
    public int getCellThreshold()
    {
        return 1;
    }

    @Override
    public void processCell(int row, int col, int[][] u, int[][] v, int[][] tempu)
    {
    }
}
