/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.cam;

import heartsim.cam.parameter.CAModelIntParameter;

/**
 *
 * @author Lee Boynton
 */
public class NishiyamaExtended extends Nishiyama
{
    protected int uUp;
    protected int uDown;

    public NishiyamaExtended()
    {
        super();

        this.setName("Nishiyama (Extended)");

        CAModelIntParameter uUpParam = new CAModelIntParameter(1);
        CAModelIntParameter uDownParam = new CAModelIntParameter(1);

        this.setParameter("uUp", uUpParam);
        this.setParameter("uDown", uDownParam);
    }

    @Override
    public void initCells()
    {
        uUp = (Integer) this.getParameter("uUp").getValue();
        uDown = (Integer) this.getParameter("uDown").getValue();

        super.initCells();
    }

    @Override
    public int processCell(int row, int col, int[][] u, int[][] v, int[][] tempu)
    {
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
            // downstoke
            if(u[row][col] - uDown < 0)
            {
                u[row][col] = 0;
            }
            else
            {
                u[row][col] -= uDown;
            }
        }
        else if (u[row][col] == N - 1)
        {
            v[row][col]++;  // plateau
        }
        else
        {
            // upstroke
            if(u[row][col] + uUp > N - 1)
            {
                u[row][col] = N - 1;
            }
            else
            {
                u[row][col] += uUp;
            }
        }

        return v[row][col];
    }
}
