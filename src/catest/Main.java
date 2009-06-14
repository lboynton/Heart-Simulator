/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catest;

import java.util.Random;

/**
 *
 * @author Lee Boynton
 */
public class Main
{
    private final int size = 10;                   // size of the grid
    private final int n = 5;                       //
    private final int delta1 = 3;                  // first delta value
    private final int delta2 = 7;                  // second delta value
    private final int time = 10;                   // duration to run simulation
    private int u[][] = new int[size][size]; // voltage values for each cell
    private int v[][] = new int[size][size]; // recovery values for each cell
    private int delta[][] = new int[size][size]; // delta values for each cell
    private int temp[][] = new int[size][size]; // temporary storage of cell values

    private void initGrid()
    {
        Random generator = new Random();

        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++)
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

        u[size-1][0] = 4;
    }

    private void printGrid()
    {
        int values[][][] =
        {
            u, v, delta
        };

        String names[] =
        {
            "Voltage   ", "Recovery  ", "Delta     "
        };

        for (int row = -1; row < size; row++)
        {
            for (int i = 0; i < 3; i++)
            {
                if (row == -1)
                {
                    System.out.print(names[i] + " ");
                    continue;
                }

                for (int col = 0; col < size; col++)
                {
                    System.out.print(values[i][row][col]);
                }

                System.out.print(" ");
            }

            System.out.println();
        }
    }

    private void start()
    {
        for (int i = 0; i < time; i++)
        {
            for (int row = 0; row < size; row++)
            {
                for (int col = 0; col < size; col++)
                {
                    if(u[row][col] >= delta[row][col]) u[row][col]++;
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Main test = new Main();
        test.initGrid();
        test.printGrid();
        test.start();
        test.printGrid();
    }
}
