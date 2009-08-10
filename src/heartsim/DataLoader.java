/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

import java.awt.geom.GeneralPath;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGOMPathElement;
import org.apache.batik.ext.awt.geom.ExtendedGeneralPath;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.ParseException;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Reads data from SVG file
 * @author Lee Boynton
 */
public class DataLoader
{
    private String file; // svg file to load from
    private boolean cells[][]; // resized array of cells
    private double size; // cell size (smaller cell size means bigger heart)
    private Document doc;
    private ExtendedGeneralPath ventriclesPath;
    private boolean fileOpened = false;
    private ArrayList<String> paths = new ArrayList<String>();
    private ArrayList<ExtendedGeneralPath> pathShapes = new ArrayList<ExtendedGeneralPath>();
    private int sizeX = 0;
    private int sizeY = 0;

    public DataLoader()
    {
    }

    public DataLoader(String file)
    {
        this.file = file;
    }

    public DataLoader(String file, String[] paths)
    {
        this.file = file;
        this.paths.addAll(Arrays.asList(paths));
    }

    public DataLoader(String[] paths)
    {
        this.paths.addAll(Arrays.asList(paths));
    }

    public void addPath(String path)
    {
        paths.add(path);
    }

    public void removePath(String path)
    {
        paths.remove(path);
    }

    public String[] getPaths()
    {
        return (String[]) paths.toArray();
    }

    public void setSize(double size)
    {
        this.size = size;
        cells = null;
    }

    public void setSize(int width, int height)
    {
    }

    public void setFile(String file)
    {
        this.file = file;
        fileOpened = false;
    }

    private void openFile()
    {
        if (fileOpened)
        {
            return;
        }

        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory documentFactory = new SAXSVGDocumentFactory(parser);
        String uri = new File(file).toURI().toString();

        try
        {
            doc = (Document) documentFactory.createDocument(uri);
        }
        catch (IOException ex)
        {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ExtendedGeneralPath getVentriclesPath()
    {
        return ventriclesPath;
    }

    private void createGrid()
    {
        openFile();
        Element ventriclesElement = (Element) doc.getElementById("ventricles");

        try
        {
            ventriclesPath = (ExtendedGeneralPath) AWTPathProducer.createShape(new StringReader(ventriclesElement.getAttributeNS(null, "d")), GeneralPath.WIND_EVEN_ODD);
        }
        catch (IOException ex)
        {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ParseException ex)
        {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        int xVal = 0;
        int yVal = 0;

        // work out how big the 2D cells array should be and add two because we
        // are dividing doubles which will return a decimal value which needs to
        // be rounded up
        sizeX = (int) ((int) (ventriclesPath.getBounds2D().getMaxY() / size) - (ventriclesPath.getBounds2D().getMinY() / size)) + 2;
        sizeY = (int) ((int) (ventriclesPath.getBounds2D().getMaxX() / size) - (ventriclesPath.getBounds2D().getMinX() / size)) + 2;

        cells = new boolean[sizeX][sizeY];

        for (double y = ventriclesPath.getBounds2D().getMinY(); y < ventriclesPath.getBounds2D().getMaxY(); y = y + size)
        {
            for (double x = ventriclesPath.getBounds2D().getMinX(); x < ventriclesPath.getBounds2D().getMaxX(); x = x + size)
            {
                cells[xVal][yVal++] = ventriclesPath.contains(x, y);
            }

            yVal = 0;
            xVal++;
        }
    }

    public ExtendedGeneralPath[] getPathShapes()
    {
        ExtendedGeneralPath[] shapes = new ExtendedGeneralPath[pathShapes.size()];

        pathShapes.toArray(shapes);

        return shapes;
    }

    private void loadPaths()
    {
        ExtendedGeneralPath shape;

        for (String path : paths)
        {
            Element element = (Element) doc.getElementById(path);

            if (element == null)
            {
                System.out.println("Could not find element: " + path);
                continue;
            }

            try
            {
                shape = (ExtendedGeneralPath) AWTPathProducer.createShape(new StringReader(element.getAttributeNS(null, "d")), GeneralPath.WIND_EVEN_ODD);

                if (shape != null)
                {
                    pathShapes.add(shape);
                }
            }
            catch (IOException ex)
            {
                Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (ParseException ex)
            {
                Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void loadData()
    {
        cells = new boolean[sizeX][sizeY];

        for (ExtendedGeneralPath path : pathShapes)
        {
            int xVal = 0;
            int yVal = 0;

            for (double y = path.getBounds2D().getMinY(); y < path.getBounds2D().getMaxY(); y = y + size)
            {
                for (double x = path.getBounds2D().getMinX(); x < path.getBounds2D().getMaxX(); x = x + size)
                {
                    cells[xVal][yVal++] = path.contains(x, y);
                }

                yVal = 0;
                xVal++;
            }
        }
    }

    private void computeSize()
    {
        for (ExtendedGeneralPath path : pathShapes)
        {
            // work out how big the 2D cells array should be and add two because we
            // are dividing doubles which will return a decimal value which needs to
            // be rounded up
            int maxX = (int) ((int) (path.getBounds2D().getMaxY() / size) - (path.getBounds2D().getMinY() / size)) + 2;
            int maxY = (int) ((int) (path.getBounds2D().getMaxX() / size) - (path.getBounds2D().getMinX() / size)) + 2;

            // store the largest X and Y value as the size
            if (maxX > sizeX)
            {
                sizeX = maxX;
            }
            if (maxY > sizeY)
            {
                sizeY = maxY;
            }
        }
    }

    public String[] getPathsInFile()
    {
        // ensure file has been opened
        openFile();

        NodeList pathsInFile = doc.getElementsByTagName("path");

        String[] pathNames = new String[pathsInFile.getLength()];

        for(int i = 0; i < pathsInFile.getLength(); i++)
        {
            pathNames[i] = ((SVGOMPathElement)pathsInFile.item(i)).getAttribute("id");
        }

        return pathNames;
    }

    public boolean[][] getGrid()
    {
        if (cells == null)
        {
            openFile();
            loadPaths();
            computeSize();
            loadData();
        }
        return cells;
    }

    public static void printGrid(boolean[][] grid)
    {
        for (int x = 0; x < grid.length; x++)
        {
            for (int y = 0; y < grid[0].length; y++)
            {
                if (grid[x][y])
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

    public static void main(String args[])
    {
        final DataLoader loader = new DataLoader("./geometry_data/heart2.svg", new String[]
                {
                    "ventricles",
                    "atria"
                });
        loader.setSize(10);
        DataLoader.printGrid(loader.getGrid());
    }
}
