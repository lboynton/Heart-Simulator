/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderListener;
import org.w3c.dom.Element;

/**
 *
 * @author Lee Boynton
 */
public class CellGenerator implements SVGDocumentLoaderListener
{
    private List<CellGeneratorListener> listeners = Collections.synchronizedList(new ArrayList<CellGeneratorListener>());
    private JSVGCanvas canvas;
    private List<String> paths = new ArrayList<String>();
    private Map<HeartTissue, Boolean> tissues = new HashMap<HeartTissue, Boolean>();
    private String tissueNames[][];
    private boolean cells[][];
    private boolean completed = false;
    private boolean initialised = false;
    private int progress = 0;
    private String tissueLoading = "None";

    public CellGenerator(JSVGCanvas canvas, String paths[])
    {
        this.canvas = canvas;
        this.paths.addAll(Arrays.asList(paths));
        canvas.addSVGDocumentLoaderListener(this);
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

        Thread thread = new Thread(new CellGeneratorRunnable());
        thread.setName("Cell generator");
        thread.start();

        // notify listeners that cell generation has finished
        fireGenerationCompleted();
    }

    public List<HeartTissue> getTissues()
    {
        return new ArrayList(tissues.keySet());
    }

    public boolean isEnabled(HeartTissue tissue)
    {
        return tissues.get(tissue);
    }

    public String[][] getTissueNames()
    {
        return tissueNames;
    }

    public void disableTissue(HeartTissue tissue)
    {
        tissues.put(tissue, false);
        run();
    }

    public void enableTissue(HeartTissue tissue)
    {
        tissues.put(tissue, true);
        run();
    }

    public void documentLoadingStarted(SVGDocumentLoaderEvent arg0)
    {
    }

    public void documentLoadingCompleted(SVGDocumentLoaderEvent arg0)
    {
        // new svg loaded so we are not initialised
        initialised = false;
    }

    public void documentLoadingCancelled(SVGDocumentLoaderEvent arg0)
    {
    }

    public void documentLoadingFailed(SVGDocumentLoaderEvent arg0)
    {
    }

    public class CellGeneratorRunnable implements Runnable
    {
        public void run()
        {
            if (!initialised)
            {
                loadTissues();
                initialised = true;
            }

            createDataArray();
            completed = true;
        }

        private void loadTissues()
        {
            tissues.clear();

            for (String path : paths)
            {
                Element element = (Element) canvas.getSVGDocument().getElementById(path);

                if (element != null)
                {
                    HeartTissue tissue = new HeartTissue(path);
                    tissue.setElement(element);
                    tissues.put(tissue, true);
                }
            }
        }

        // TODO: Split up
        private void createDataArray()
        {
            fireGenerationStarted();

            cells = new boolean[canvas.getPreferredSize().height][canvas.getPreferredSize().width];
            tissueNames = new String[canvas.getPreferredSize().height][canvas.getPreferredSize().width];

            for (HeartTissue tissue : tissues.keySet())
            {
                if (!tissues.get(tissue))
                {
                    Application.getInstance().output(tissue.getName() + " is disabled, skipping");
                    continue;
                }

                tissueLoading = tissue.getName();

                Application.getInstance().output("Generating cells for " + tissueLoading);

                GraphicsNode node = canvas.getUpdateManager().getBridgeContext().getGraphicsNode(tissue.getElement());

                if (node != null)
                {
                    AffineTransform elementsAt = node.getGlobalTransform();
                    Shape selectionHighlight = node.getOutline();
                    AffineTransform at = canvas.getRenderingTransform();
                    at.concatenate(elementsAt);
                    Shape s = at.createTransformedShape(selectionHighlight);

                    if (s == null)
                    {
                        break;
                    }

                    tissue.setShape(s);

                    for (int row = 0; row < canvas.getPreferredSize().height; row++)
                    {
                        for (int col = 0; col < canvas.getPreferredSize().width; col++)
                        {
                            if (s.contains(new Point(col, row)))
                            {
                                cells[row][col] = true;
                                tissueNames[row][col] = tissueLoading;
                            }
                        }

                        // row completed
                        progress = (int) (((row + 1) / (double) canvas.getPreferredSize().height) * 100);
                    }
                }
            }

            fireGenerationCompleted();
        }
    }
}
