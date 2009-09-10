/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.gui.component;

import java.awt.Color;
import java.awt.Paint;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author Lee Boynton
 */
public class TissueSeries
{
    private XYSeries voltage;
    private XYSeries recovery;
    private int row;
    private int col;
    private int voltageSeries;
    private int recoverySeries;
    private String name;
    private Color voltageColour;
    private Color recoveryColour;

    public TissueSeries(int row, int col, String name)
    {
        this.row = row;
        this.col = col;
        this.name = name;
        this.voltage = new XYSeries(getName() + " voltage");
        this.recovery = new XYSeries(getName() + " recovery");
    }

    public Color getRecoveryColour()
    {
        return recoveryColour;
    }

    public Color getVoltageColour()
    {
        return voltageColour;
    }

    public int getRecoverySeries()
    {
        return recoverySeries;
    }

    public void setRecoverySeries(int recoverySeries)
    {
        this.recoverySeries = recoverySeries;
    }

    public int getVoltageSeries()
    {
        return voltageSeries;
    }

    public void setVoltageSeries(int voltageSeries)
    {
        this.voltageSeries = voltageSeries;
    }

    public int getCol()
    {
        return col;
    }

    public String getName()
    {
        return String.valueOf(row) + ", " + String.valueOf(col) + " " + name;
    }

    public XYSeries getRecovery()
    {
        return recovery;
    }

    public int getRow()
    {
        return row;
    }

    public XYSeries getVoltage()
    {
        return voltage;
    }

    @Override
    public String toString()
    {
        return getName();
    }

    @Override
    public boolean equals(Object obj)
    {
        return this.getName().equals(((TissueSeries)obj).getName());
    }

    public void setVoltageColor(Paint paint)
    {
        this.voltageColour = (Color) paint;
    }

    public void setRecoveryColor(Paint paint)
    {
        this.recoveryColour = (Color) paint;
    }
}
