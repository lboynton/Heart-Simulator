/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.SVGConstants;
import org.w3c.dom.Element;

/**
 *
 * @author Lee Boynton
 */
public class CellGenerator implements Runnable
{
    private List<CellGeneratorListener> listeners = Collections.synchronizedList(new ArrayList<CellGeneratorListener>());
    private JSVGCanvas canvas;
    private List<String> paths = new ArrayList<String>();
    private List<Element> elements = new ArrayList<Element>();
    private boolean cells[][];
    private boolean completed = false;
    private int progress = 0;
    private String tissueLoading = "None";

    public CellGenerator(JSVGCanvas canvas)
    {
        this.canvas = canvas;
    }

    public int getProgress()
    {
        return progress;
    }

    public boolean isCompleted()
    {
        return completed;
    }

    public String getTissueLoading()
    {
        return tissueLoading;
    }

    public void addPath(String path)
    {
        paths.add(path);
    }

    public void removePath(String path)
    {
        if (paths.contains(path))
        {
            paths.remove(path);
        }
    }

    public String[] getPaths()
    {
        return (String[]) paths.toArray();
    }

    public void addGeneratorListener(CellGeneratorListener listener)
    {
        listeners.add(listener);
    }

    public void removeGeneratorListener(CellGeneratorListener listener)
    {
        if (listeners.contains(listener))
        {
            listeners.remove(listener);
        }
    }

    public void fireGenerationStarted()
    {
        for (CellGeneratorListener l : listeners)
        {
            l.cellGenerationStarted();
        }
    }

    public void fireGenerationCompleted()
    {
        for (CellGeneratorListener l : listeners)
        {
            l.cellGenerationCompleted();
        }
    }

    public boolean[][] getCells()
    {
        return cells;
    }

    public void run()
    {
        // notify listeners that cell generation has started
        fireGenerationStarted();

        loadElements();
        createDataArray();
        completed = true;

        // notify listeners that cell generation has finished
        fireGenerationCompleted();
    }

    private void loadElements()
    {
        for (String path : paths)
        {
            Element element = (Element) canvas.getSVGDocument().getElementById(path);

            if (element != null)
            {
                elements.add(element);
            }
        }
    }

    private void createDataArray()
    {
        cells = new boolean[canvas.getPreferredSize().height + 1][canvas.getPreferredSize().width + 1];

        for (Element element : elements)
        {
            tissueLoading = element.getAttribute(SVGConstants.SVG_IDENTITY_VALUE);

            GraphicsNode node = canvas.getUpdateManager().getBridgeContext().getGraphicsNode(element);

            if (node != null)
            {
                for (int y = 0; y < canvas.getPreferredSize().height; y++)
                {
                    for (int x = 0; x < canvas.getPreferredSize().width; x++)
                    {
                        if (node.contains(new Point(x, y)))
                        {
                            cells[y][x] = true;
                        }
                    }

                    // row completed
                    progress = (int) (((y + 1) / (double) canvas.getPreferredSize().height) * 100);
                }
            }
        }
    }
}
