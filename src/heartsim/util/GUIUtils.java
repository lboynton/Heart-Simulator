/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.util;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JTextField;

/**
 *
 * @author Lee Boynton
 */
public class GUIUtils
{
    public static void lock(Container container, boolean recursive, boolean lock)
    {
        List<Class> blacklist = new ArrayList<Class>();

        blacklist.add(JTextField.class);
        blacklist.add(JButton.class);
        blacklist.add(JComboBox.class);
        blacklist.add(JMenu.class);
        
        for (Component c : container.getComponents())
        {
            if ((blacklist.contains(c.getClass())))
            {
                c.setEnabled(!lock);
            }

            if (c instanceof Container && recursive)
            {
                lock((Container) c, recursive, lock);
            }
        }
    }
}
