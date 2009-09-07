/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.util;

import java.io.File;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

/**
 * This class provides a number of general purpose utility methods for working
 * with files
 * @author Lee Boynton
 */
public class FileUtils
{
    /**
     * Gets the lowercase extension of a file
     * @param f The file
     * @return The extension
     */
    public static String getExtension(File f)
    {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1)
        {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    /**
     * Gets the system icon of a file
     * @param filename
     * @return The system icon
     */
    public static Icon getFileIcon(String filename)
    {
        Icon icon = null;

        try
        {
            //Create a temporary file with the specified extension
            File file = File.createTempFile("file", "." + getExtension(filename));

            FileSystemView view = FileSystemView.getFileSystemView();
            icon = view.getSystemIcon(file);

            //Delete the temporary file
            file.delete();
        }
        catch (IOException ex)
        {
        }

        return icon;
    }

    /**
     * Gets the lowercase extension of a filename
     * @param filename The filename
     * @return The extension
     */
    public static String getExtension(String filename)
    {
        return getExtension(new File(filename));
    }
}