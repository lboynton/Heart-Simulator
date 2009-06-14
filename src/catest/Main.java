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
    private final int size = 10; // size of the grid
    private final int N = 5; //
    private final int delta1 = 3; // first delta value
    private final int delta2 = 3; // second delta value
    private final int time = 20; // duration to run simulation
    private int u[][] = new int[size][size]; // voltage values for each cell
    private int v[][] = new int[size][size]; // recovery values for each cell
    private int delta[][] = new int[size][size]; // delta values for each cell
    private int temp[][] = new int[size][size]; // temporary storage of cell values

    private void initCells()
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

        u[size - 2][1] = 4;
    }

    private void printCells()
    {
        int values[][][] =
        {
            u, temp, v, delta
        };

        String names[] =
        {
            "Voltage", "Temp", "Recovery", "Delta"
        };

        for (int i = 0; i < names.length; i++)
        {
            names[i] = padRight(names[i], size);
        }

        for (int row = -1; row < size; row++)
        {
            for (int i = 0; i < names.length; i++)
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

    private void copyIntoTemp()
    {
        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++)
            {
                temp[row][col] = u[row][col];
            }
        }
    }

    private void start()
    {
        for (int t = 0; t < time; t++)
        {
            // create copy of voltage values
            this.copyIntoTemp();
            printCells();

            for (int row = 1; row < size - 1; row++)
            {
                for (int col = 1; col < size - 1; col++)
                {
                    int stmn =
                            temp[row - 1][col - 1] +
                            temp[row - 1][col + 1] +
                            temp[row - 1][col] +
                            temp[row + 1][col - 1] +
                            temp[row + 1][col + 1] +
                            temp[row + 1][col] +
                            temp[row][col + 1] +
                            temp[row][col - 1];

                    if (stmn >= delta[row][col])
                    {
                        // right
                        if (u[row][col] < N && v[row][col] == 0)
                        {
                            u[row][col]++;
                            continue;
                        }
                        // up
                        if (u[row][col] == N && v[row][col] < N)
                        {
                            v[row][col]++;
                            continue;
                        }
                        // left
                        if (u[row][col] > 0 && v[row][col] == N)
                        {
                            u[row][col]--;
                            continue;
                        }
                        // down
                        if (u[row][col] == 0 && v[row][col] > 0)
                        {
                            v[row][col]--;
                        }
                    }
                }
            }
        }
        printCells();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Main test = new Main();
        test.initCells();
        test.start();
    }

    public static String padRight(String s, int n)
    {
        return String.format("%1$-" + n + "s", s);
    }
}
