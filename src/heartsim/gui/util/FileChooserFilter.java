/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.gui.util;

import heartsim.util.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.filechooser.FileFilter;

/**
 * Makes it easier to create filters for use with JFileChoosers.
 * @author Lee Boynton
 */
public class FileChooserFilter extends FileFilter
{
    private List<String> extensions = new ArrayList<String>();
    private String description;

    /**
     * Creates a new file chooser filter with the specified extensions and description
     * For use with JFileChoosers
     * @param extensions The extensions to include
     * @param description The description
     */
    public FileChooserFilter(String[] extensions, String description)
    {
        this.extensions.addAll(Arrays.asList(extensions));
    }
    
    /**
     * Creates a new file chooser filter with the specified extension and description
     * For use with JFileChoosers
     * @param extension The extension to include
     * @param description The description
     */
    public FileChooserFilter(String extension, String description)
    {
        this.description = description;
        extensions.add(extension);
    }
    
    /**
     * Adds an extension to this file filter
     * @param extension The extension
     */
    public void addExtension(String extension)
    {
        extensions.add(extension);
    }

    /**
     * Accepts image files
     * @param f The file
     * @return True if file is an image, false otherwise
     */
    @Override
    public boolean accept(File f)
    {
        if (f.isDirectory())
        {
            return true;
        }

        String fileExtension = FileUtils.getExtension(f);
        if (fileExtension != null)
        {
            for(String extension:extensions)
            {
                if(fileExtension.equals(extension)) return true;
            }
        }

        return false;
    }

    /**
     * The description of the filter that is displayed
     * @return The description
     */
    @Override
    public String getDescription()
    {
        return description;
    }
}