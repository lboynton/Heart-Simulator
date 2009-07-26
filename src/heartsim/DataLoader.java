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

    public DataLoader(String file)
    {
        this.file = file;
    }

    public void setSize(double size)
    {
        this.size = size;
    }

    private void createGrid()
    {
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        String uri = new File(file).toURI().toString();
        Document doc = null;
        try
        {
            doc = (Document) f.createDocument(uri);
        }
        catch (IOException ex)
        {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        Element e = (Element) doc.getElementById("heart");
        ExtendedGeneralPath s = null;
        try
        {
            s = (ExtendedGeneralPath) AWTPathProducer.createShape(new StringReader(e.getAttributeNS(null, "d")), GeneralPath.WIND_EVEN_ODD);
        }
        catch (IOException ex)
        {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ParseException ex)
        {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        int xval = 0;
        int yval = 0;

        int maxX = (int) (s.getBounds2D().getMaxY() / size) + 1;
        int maxY = (int) (s.getBounds2D().getMaxX() / size) + 1;

        cells = new boolean[maxX][maxY];

        System.out.println(s.getBounds2D().getMaxX() / size);
        System.out.println(s.getBounds2D().getMaxY() / size);

        for(double y = 0; y < s.getBounds2D().getMaxY(); y = y + size)
        {
            for(double x = 0; x < s.getBounds2D().getMaxX(); x = x + size)
            {
                cells[xval][yval++] = s.contains(x, y);
            }

            yval=0;
            xval++;
        }
    }

    public boolean[][] getGrid()
    {
        createGrid();
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
