/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.gui.component;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Lee Boynton
 */
public class ActionPotentialChart extends ChartPanel
{
    private final XYSeriesCollection chartData;
    private final JFreeChart chart;
    private int col = 0;
    private int row = 0;
    private String tissue;
    private int visibleTimeSteps = 2000;

    public ActionPotentialChart()
    {
        // set the chart later
        super(null);

        XYSeries voltageSeries = new XYSeries("Voltage");
        XYSeries recoverySeries = new XYSeries("Recovery");

        chartData = new XYSeriesCollection();
        chartData.addSeries(voltageSeries);
        chartData.addSeries(recoverySeries);

//        chart = new JFreeChart("Action Potential for " + col + ", " + row, plot);

        chart = ChartFactory.createXYLineChart(
            "Action Potential",      // chart title
            "Time",                      // x axis label
            "Potential",                      // y axis label
            chartData,                  // data
            PlotOrientation.VERTICAL,
            false,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        reset();

        chart.setBackgroundPaint(null);

        this.setChart(chart);
    }

    public void setRange(int min, int max)
    {
        ((XYPlot)chart.getPlot()).getRangeAxis().setRange(min, max + 10);
    }

    public void setVoltageValue(int time, int value)
    {
        chartData.getSeries(0).add(time, value);

        if(time > visibleTimeSteps)
        {
            ((XYPlot)chart.getPlot()).getDomainAxis().setRange(time - visibleTimeSteps, time);
        }
    }

    public void setRecoveryValue(int time, int value)
    {
        chartData.getSeries(1).add(time, value);

        if(time > visibleTimeSteps)
        {
            ((XYPlot)chart.getPlot()).getDomainAxis().setRange(time - visibleTimeSteps, time);
        }
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
        ((XYPlot)chart.getPlot()).getRenderer().setSeriesVisible(1, recovery);
    }

    public void setVoltageEnabled(boolean voltage)
    {
        ((XYPlot)chart.getPlot()).getRenderer().setSeriesVisible(0, voltage);
    }

    public void reset()
    {
        // set maximum time steps range
        ((XYPlot)chart.getPlot()).getDomainAxis().setRange(0, visibleTimeSteps);

        chartData.getSeries(0).clear();
        chartData.getSeries(1).clear();
    }
}
