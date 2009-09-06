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
    protected int vUp;
    protected int vDown;
    protected int initialUDown;

    public NishiyamaExtended()
    {
        super();

        this.setName("Nishiyama (Extended)");

        CAModelIntParameter uUpParam = new CAModelIntParameter(1);
        CAModelIntParameter uDownParam = new CAModelIntParameter(1);
        CAModelIntParameter vUpParam = new CAModelIntParameter(1);
        CAModelIntParameter vDownParam = new CAModelIntParameter(1);
        CAModelIntParameter initialUDownParam = new CAModelIntParameter(1);

        uUpParam.setDescription("Determines how steep the upstroke of the action " +
                "potential is");
        uDownParam.setDescription("Determines how steep the downstroke of the " +
                "action potential is");
        vUpParam.setDescription("The vUp parameter determines how fast the cell " +
                "reaches its maximum recovery value. Having a large value for " +
                "this will result in a cell that does not stay excited for long. " +
                "Conversely, a low value will result in the cell having a long " +
                "plateau period in which it is excited for a long time.");
        vDownParam.setDescription("The vDown parameter stipulates how fast a cell " +
                "reaches its recovered state. Having a large value will result " +
                "in the cell recovering very quickly, and being able to be " +
                "stimulated again much sooner than with a small value.");
        initialUDownParam.setDescription("Determines the slope of the initial " +
                "repolarisation of the cell. The value set for uUp must not be " +
                "a divisor of N for this to take effect.");

        uUpParam.setImage("./help_pictures/parameters.png");
        uDownParam.setImage("./help_pictures/parameters.png");
        vUpParam.setImage("./help_pictures/parameters.png");
        vDownParam.setImage("./help_pictures/parameters.png");
        initialUDownParam.setImage("./help_pictures/parameters.png");

        this.setParameter("uUp", uUpParam);
        this.setParameter("uDown", uDownParam);
        this.setParameter("vUp", vUpParam);
        this.setParameter("vDown", vDownParam);
        this.setParameter("iUDown", initialUDownParam);
    }

    @Override
    public void initCells()
    {
        uUp = (Integer) this.getParameter("uUp").getValue();
        uDown = (Integer) this.getParameter("uDown").getValue();
        vUp = (Integer) this.getParameter("vUp").getValue();
        vDown = (Integer) this.getParameter("uDown").getValue();
        initialUDown = (Integer) this.getParameter("iUDown").getValue();

        super.initCells();
    }

    @Override
    public void processCell(int row, int col, int[][] u, int[][] v, int[][] tempu)
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
                if (v[row][col] - vDown < 0)
                {
                    v[row][col] = 0;
                }
                else
                {
                    v[row][col] -= vDown;  // refractory
                }
            }
        }
        else if (v[row][col] == N - 1)
        {
            // downstoke
            if (u[row][col] - uDown < 0)
            {
                u[row][col] = 0;
            }
            else
            {
                u[row][col] -= uDown;
            }
        }
        else if (u[row][col] >= N - 1)
        {
            // initial repolarisation
            if (u[row][col] > N - 1)
            {
                u[row][col] -= initialUDown;
            }
            
            if (v[row][col] + vUp > N - 1)
            {
                v[row][col] = N - 1;
            }
            else
            {
                v[row][col] += vUp;
            }
        }
        else
        {
            // upstroke
            u[row][col] += uUp;
        }
    }
}
