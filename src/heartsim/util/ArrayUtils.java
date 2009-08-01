/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsim.util;

/**
 * Utility methods for manipulating arrays
 * @author Lee Boynton
 */
public class ArrayUtils
{
    /**
     * Copies a 2D int array
     * @param original Original array
     * @param copy Array to copy values to
     */
    public static void copy2DArray(int[][] original, int[][] copy)
    {
        for (int row = 0; row < original.length; row++)
        {
            for (int col = 0; col < original[row].length; col++)
            {
                copy[row][col] = original[row][col];
            }
        }
    }
}