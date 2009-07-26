/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.ca;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 *
 * @author Lee Boynton
 */
public class Nishiyama
{
    private int height = 100; // height of the grid
    private int width = 100; // width of the grid
    private int N = 5; //
    private int delta1 = 3; // first delta value
    private int delta2 = 7; // second delta value
    private static final int time = 50; // duration to run simulation
    private int u[][] = new int[height][width]; // voltage values for each cell
    private int v[][] = new int[height][width]; // recovery values for each cell
    private int delta[][] = new int[height][width]; // delta values for each cell
    private int tempu[][] = new int[height][width]; // temporary storage of cell values
    private boolean cells[][] = new boolean[height][width]; // true/false if there is a cell

    public int[][] getU()
    {
        return u;
    }

    public void setN(int N)
    {
        this.N = N;
    }

    public void setDelta1(int delta1)
    {
        this.delta1 = delta1;
    }

    public void setDelta2(int delta2)
    {
        this.delta2 = delta2;
    }

    public void setSize(Dimension d)
    {
        height = d.height;
        width = d.width;
        initCells();
    }

    private void readCellsFile() throws FileNotFoundException, IOException
    {
        FileInputStream fstream = new FileInputStream("./geometry_data/heart_lattice_2d");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String strLine;

        while ((strLine = br.readLine()) != null)
        {
            String coords[] = strLine.split(" ");
            cells[Integer.parseInt(coords[0])][Integer.parseInt(coords[1])] = true;
        }

        in.close();
    }

    public void initCells()
    {
        Random generator = new Random();

        u = new int[height][width]; // voltage values for each cell
        v = new int[height][width]; // recovery values for each cell
        delta = new int[height][width]; // delta values for each cell
        tempu = new int[height][width]; // temporary storage of cell values
        cells = new boolean[height][width]; // true/false if there is a cell

        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                // initialise voltage values
                u[row][col] = 0;

                // initialise recovery values
                v[row][col] = 0;

                // initialise cells
                cells[row][col] = false;

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

        // initially stimulate bottom of heart
        u[87][37] = 1;

        try
        {
            readCellsFile();
        }
        catch (Exception ex)
        {
            System.err.println("Could not open cells file");
            System.err.println(ex.toString());
            System.exit(1);
        }
    }

    private void printCells()
    {
        System.out.println("Cells:");
        
        for(int row = 0; row < height; row++)
        {
            for(int col = 0; col < width; col++)
            {
                if(cells[row][col]) System.out.print("*");
                else System.out.print(" ");
            }

            System.out.println();
        }
    }

    private void printArrays()
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
                if(!cells[row][col]) continue;
                
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
        test.initCells();
        test.printCells();

        for (int t = 0; t < time; t++)
        {
            //test.printArrays();
            test.step();
        }

        test.printArrays();
    }

    public static String padRight(String s, int n)
    {
        return String.format("%1$-" + n + "s", s);
    }
}
