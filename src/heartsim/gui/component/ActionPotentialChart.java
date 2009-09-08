/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.gui.component;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
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
    private final XYSeriesCollection chartData = new XYSeriesCollection();
    private final JFreeChart chart;
    private int visibleTimeSteps = 2000;
    private int min;
    private int max;
    private List<Point> cells = new ArrayList<Point>();

    public ActionPotentialChart()
    {
        // set the chart later
        super(null);

//        chart = new JFreeChart("Action Potential for " + col + ", " + row, plot);

        chart = ChartFactory.createXYLineChart(
                "Action Potential", // chart title
                "Time", // x axis label
                "Potential", // y axis label
                chartData, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
                );

        reset();

        chart.setBackgroundPaint(null);

        this.setChart(chart);
    }

    public void setRange(int min, int max)
    {
        this.min = min;
        this.max = max + 10;

        setRangeRange();
    }

    public void setRangeRange()
    {
        ((XYPlot) chart.getPlot()).getRangeAxis().setRange(this.min, this.max);
    }

    public void setDomainRange()
    {
        ((XYPlot) chart.getPlot()).getDomainAxis().setRange(0, visibleTimeSteps);
    }

    public void setRanges()
    {
        setRangeRange();
        setDomainRange();
    }

    public void addCell(int row, int col)
    {
        Point point = new Point(col, row);

        if (!cells.contains(point))
        {
            String uSeriesName = String.valueOf(row) + ", " + String.valueOf(col) + " voltage";
            String vSeriesName = String.valueOf(row) + ", " + String.valueOf(col) + " recovery";

            XYSeries uSeries = new XYSeries(uSeriesName);
            XYSeries vSeries = new XYSeries(vSeriesName);

            chartData.addSeries(uSeries);
            chartData.addSeries(vSeries);

            cells.add(point);
        }
    }

    public void setVoltageValue(int time, int value, int row, int col)
    {
        String series = String.valueOf(row) + ", " + String.valueOf(col) + " voltage";

        XYSeries xySeries = chartData.getSeries(series);

        xySeries.add(time, value);

        if (time > visibleTimeSteps)
        {
            ((XYPlot) chart.getPlot()).getDomainAxis().setRange(time - visibleTimeSteps, time);
        }
    }

    public void setRecoveryValue(int time, int value, int row, int col)
    {
        String series = String.valueOf(row) + ", " + String.valueOf(col) + " recovery";

        XYSeries xySeries = chartData.getSeries(series);

        xySeries.add(time, value);

        if (time > visibleTimeSteps)
        {
            ((XYPlot) chart.getPlot()).getDomainAxis().setRange(time - visibleTimeSteps, time);
        }
    }

    public void setRecoveryEnabled(boolean recovery)
    {
        ((XYPlot) chart.getPlot()).getRenderer().setSeriesVisible(1, recovery);
    }

    public void setVoltageEnabled(boolean voltage)
    {
        ((XYPlot) chart.getPlot()).getRenderer().setSeriesVisible(0, voltage);
    }

    public void reset()
    {
        setRanges();

        for (Object obj : chartData.getSeries())
        {
            if (obj instanceof XYSeries)
            {
                ((XYSeries) obj).clear();
            }
        }
    }
}
