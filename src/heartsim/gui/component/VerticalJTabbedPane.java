/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.gui.component;

import heartsim.gui.*;
import java.awt.Component;
import javax.swing.JTabbedPane;

/**
 *
 * @author Lee Boynton
 */
public class VerticalJTabbedPane extends JTabbedPane
{
    @Override
    public void addTab(String title, Component component)
    {
        super.addTab(null, new VerticalTextIcon(title, tabPlacement==JTabbedPane.RIGHT), component);
    }
}
