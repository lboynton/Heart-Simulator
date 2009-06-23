/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catest;

import java.awt.Dimension;
import java.util.Random;

/**
 *
 * @author Lee Boynton
 */
public class Nishiyama
{
    private int height = 10; // height of the grid
    private int width = 10; // width of the grid
    private final int N = 5; //
    private final int delta1 = 3; // first delta value
    private final int delta2 = 7; // second delta value
    private static final int time = 50; // duration to run simulation
    private int u[][] = new int[height][width]; // voltage values for each cell
    private int v[][] = new int[height][width]; // recovery values for each cell
    private int delta[][] = new int[height][width]; // delta values for each cell
    private int tempu[][] = new int[height][width]; // temporary storage of cell values
    private int tempv[][] = new int[height][width];
    public int[][] getU()
    {
        return u;
    }

    public void setSize(Dimension d)
    {
        height = d.height;
        width = d.width;
        initCells();
    }

    public void initCells()
    {
        Random generator = new Random();

        u = new int[height][width]; // voltage values for each cell
        v = new int[height][width]; // recovery values for each cell
        delta = new int[height][width]; // delta values for each cell
        tempu = new int[height][width]; // temporary storage of cell values
        tempv = new int[height][width];

        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                // initialise voltage values
                u[row][col] = 0;

                // initialise recovery values
                v[row][col] = 0;

                // initialise delta values
                int randomDelta = generator.nextInt(2);

                if (randomDelta == 0)
                {
                    delta[row][col] = delta1;
                }
                else
                {
                    delta[row][col] = delta2;
                }
            }
        }

        u[height / 2][width / 2] = 4;
    }

    private void printCells()
    {
        int values[][][] =
        {
            u, tempu, v, delta
        };

        String names[] =
        {
            "Voltage", "Temp", "Recovery", "Delta"
        };

        for (int i = 0; i < names.length; i++)
        {
            names[i] = padRight(names[i], width);
        }

        for (int row = -1; row < height; row++)
        {
            for (int i = 0; i < names.length; i++)
            {
                if (row == -1)
                {
                    System.out.print(names[i] + " ");
                    continue;
                }

                for (int col = 0; col < width; col++)
                {
                    System.out.print(values[i][row][col]);
                }

                System.out.print(" ");
            }

            System.out.println();
        }
    }

    private void copyIntoTemp()
    {
        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                tempu[row][col] = u[row][col];
                tempv[row][col] = v[row][col];
            }
        }
    }

    public void step()
    {
        // create copy of voltage values
        copyIntoTemp();

        for (int row = 1; row < height - 1; row++)
        {
            for (int col = 1; col < width - 1; col++)
            {
                int stmn =
                        tempu[row - 1][col - 1] +
                        tempu[row - 1][col + 1] +
                        tempu[row - 1][col] +
                        tempu[row + 1][col - 1] +
                        tempu[row + 1][col + 1] +
                        tempu[row + 1][col] +
                        tempu[row][col + 1] +
                        tempu[row][col - 1];

                if (stmn >= delta[row][col])
                {
                    int count = 0;

                    // right
                    if (tempu[row][col] < N && tempv[row][col] == 0)
                    {
                        u[row][col]++;
                        count++;
                    }
                    // up
                    if (tempu[row][col] >= N && tempv[row][col] < N)
                    {
                        v[row][col]++;
                        count++;
                    }
                    // left
                    if (tempu[row][col] > 0 && tempv[row][col] >= N)
                    {
                        u[row][col]--;
                        count++;
                    }
                    // down
                    if (tempu[row][col] == 0 && tempv[row][col] > 0)
                    {
                        v[row][col]--;
                        count++;
                    }

                    // count should be 1 each time, check that it is
                    if(count != 1) System.out.println("U: " + u[row][col] + " v: " + v[row][col]);
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Nishiyama test = new Nishiyama();
        test.initCells();

        for(int t = 0; t < time; t++)
        {
            test.printCells();
            test.step();
        }

        test.printCells();
    }

    public static String padRight(String s, int n)
    {
        return String.format("%1$-" + n + "s", s);
    }
}
