/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.gui.component;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Lee Boynton
 */
public class ActionPotentialChart extends ChartPanel
{
    private final DefaultCategoryDataset chartData;
    private final JFreeChart chart;
    private final CategoryAxis domainAxis;
    private final NumberAxis rangeAxis;
    private final LineRenderer3D renderer;
    private int col = 0;
    private int row = 0;
    private String tissue;
    private boolean voltage = true;
    private boolean recovery = true;

    public ActionPotentialChart()
    {
        // set the chart later
        super(null);

        chartData = new DefaultCategoryDataset();
        renderer = new LineRenderer3D();
        domainAxis = new CategoryAxis("Time");
        rangeAxis = new NumberAxis("Voltage");
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        final CategoryPlot plot = new CategoryPlot(chartData, domainAxis, rangeAxis, renderer);
        chart = new JFreeChart("Action Potential for " + col + ", " + row, plot);
        chart.removeLegend();
        chart.setBackgroundPaint(null);

        this.setChart(chart);
    }

    public void setRange(int min, int max)
    {
        rangeAxis.setRange(min, max + 10);
    }

    public void setVoltageValue(int time, int value)
    {
        if(!voltage) return;

        if (chartData.getColumnCount() > 500)
        {
            chartData.removeColumn(0);
        }

        chartData.addValue(value, "Voltage", String.valueOf(time));
    }

    public void setRecoveryValue(int time, int value)
    {
        if(!recovery) return;
        
        if (chartData.getColumnCount() > 500)
        {
            chartData.removeColumn(0);
        }

        chartData.addValue(value, "Recovery", String.valueOf(time));
    }

    public void setCell(int row, int col)
    {
        this.row = row;
        this.col = col;
        chart.setTitle("Action Potential for " + col + ", " + row);
    }

    public void setCell(int row, int col, String nameOfTissue)
    {
        if (nameOfTissue == null)
        {
            this.tissue = "Empty Cell";
        }
        else
        {
            this.tissue = nameOfTissue;
        }
        this.row = row;
        this.col = col;
        chart.setTitle("Action Potential for " + tissue + " at " + col + ", " + row);
    }

    public int getCellColumn()
    {
        return col;
    }

    public int getCellRow()
    {
        return row;
    }

    public void setRecoveryEnabled(boolean recovery)
    {
        this.recovery = recovery;
    }

    public void setVoltageEnabled(boolean voltage)
    {
        this.voltage = voltage;
    }
}
