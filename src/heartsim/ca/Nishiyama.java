/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.ca;

import heartsim.ca.parameter.CAModelIntParameter;
import heartsim.util.ArrayUtils;
import heartsim.util.StringUtils;
import java.awt.Dimension;
import java.util.Random;

/**
 * Nishiyama cellular automata model
 * @author Lee Boynton
 */
public class Nishiyama extends CAModel
{
    private int delta[][] = new int[height][width]; // delta values for each cell
    private int tempu[][] = new int[height][width]; // temporary storage of cell values
    private Random generator = new Random();

    public Nishiyama()
    {
        // set name of CA model
        super("Nishiyama");

        // create parameters
        CAModelIntParameter N = new CAModelIntParameter(6);
        CAModelIntParameter delta1 = new CAModelIntParameter(3);
        CAModelIntParameter delta2 = new CAModelIntParameter(7);

        // set descriptions of parameters
        N.setDescription("This value affects how high the voltage/recovery of a cell can go before it plateaus");
        delta1.setDescription("<html>Cells are randomly assigned either delta 1 or delta 2.<br>The sum of the Moore neighbours must be at least this value before a cell becomes excited.</html>");
        delta2.setDescription(delta1.getDescription());

        // add parameters
        this.setParameter("N", N);
        this.setParameter("Delta 1", delta1);
        this.setParameter("Delta 2", delta2);
    }

    public boolean stimulate(int x, int y)
    {
        try
        {
            u[x][y] = 1;
        }
        catch(java.lang.ArrayIndexOutOfBoundsException ex)
        {
            return false;
        }

        return true;
    }

    public void initCells()
    {
        int delta1 = (Integer) this.getParameter("Delta 1").getValue();
        int delta2 = (Integer) this.getParameter("Delta 2").getValue();

        u = new int[height][width]; // voltage values for each cell
        v = new int[height][width]; // recovery values for each cell
        delta = new int[height][width]; // delta values for each cell
        tempu = new int[height][width]; // temporary storage of cell values

        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                // initialise voltage values
                if(isCell(row,col)) u[row][col] = 0;
                else u[row][col] = -1;

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

    public void printArrays()
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
            names[i] = StringUtils.padRight(names[i], width);
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

    public void step()
    {
        // copy voltage values into temporary array
        ArrayUtils.copy2DArray(u, tempu);

        int N = (Integer) this.getParameter("N").getValue();

        for (int row = 1; row < cells.length - 1; row++)
        {
            for (int col = 1; col < cells[row].length - 1; col++)
            {
                if (!cells[row][col])
                {
                    continue;
                }

                if (u[row][col] == 0)
                {
                    if (v[row][col] == 0)
                    {
                        // check for stimulation
                        // inefficient count of neighbours
                        if (tempu[row - 1][col - 1] + tempu[row][col - 1] +
                                tempu[row + 1][col - 1] + tempu[row - 1][col] +
                                tempu[row + 1][col] + tempu[row - 1][col + 1] +
                                tempu[row][col + 1] + tempu[row + 1][col + 1] >= delta[row][col])
                        {
                            u[row][col] = 1;  // stimulated
                        }
                    }
                    else
                    {
                        v[row][col]--;  // refractory
                    }
                }
                else if (v[row][col] == N - 1)
                {
                    u[row][col]--;  // downstoke
                }
                else if (u[row][col] == N - 1)
                {
                    v[row][col]++;  // plateau
                }
                else
                {
                    u[row][col]++;  // upstroke
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
        boolean[][] cells = new boolean[10][10];
        for (int i = 0; i < cells.length; i++)
        {
            for (int j = 0; j < cells[i].length; j++)
            {
                cells[i][j] = true;
            }
        }
        test.setCells(cells);
        test.setSize(new Dimension(10, 10));
        test.initCells();
        test.stimulate(1, 1);
        test.printCells();
        int time = 20;

        for (int t = 0; t < time; t++)
        {
            test.printArrays();
            test.step();
        }

        test.printArrays();
    }
}