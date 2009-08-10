/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim;

import java.awt.geom.GeneralPath;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.ext.awt.geom.ExtendedGeneralPath;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.ParseException;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
    
    public DataLoader(String file)
    {
        this.file = file;
    }

    public void setSize(double size)
    {
        this.size = size;
        cells = null;
    }

    public void setSize(int width, int height)
    {
        
    }

    private void openFile()
    {
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
        int sizeX = (int) ((int) (ventriclesPath.getBounds2D().getMaxY() / size) - (ventriclesPath.getBounds2D().getMinY() / size)) + 2;
        int sizeY = (int) ((int) (ventriclesPath.getBounds2D().getMaxX() / size) - (ventriclesPath.getBounds2D().getMinX() / size)) + 2;

        cells = new boolean[sizeX][sizeY];

        for(double y = ventriclesPath.getBounds2D().getMinY(); y < ventriclesPath.getBounds2D().getMaxY(); y = y + size)
        {
            for(double x = ventriclesPath.getBounds2D().getMinX(); x < ventriclesPath.getBounds2D().getMaxX(); x = x + size)
            {
                cells[xVal][yVal++] = ventriclesPath.contains(x, y);
            }

            yVal=0;
            xVal++;
        }
    }

    public boolean[][] getGrid()
    {
        if(cells == null) createGrid();
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
        final DataLoader loader = new DataLoader("./geometry_data/heart.svg");
        loader.setSize(10);
        DataLoader.printGrid(loader.getGrid());
    }
}
