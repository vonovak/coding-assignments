/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.novakvo5;

import java.io.File;
import javax.swing.filechooser.*;

/**
 *
 * @author vonovak
 * trida, ktera pri loadovani hry vyradi ze zobrazeni soubory, ktere maji priponu jinou nez txt
 */
public class GameFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = getExtension(f);
        if (extension != null) {
            if (extension.equals("txt")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     *vraci priponu predaneho souboru
     * @param file je predany soubor
     * @return priponu predaneho souboru
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = 0;
        i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    @Override
    public String getDescription() {
        return "piskvorky game files (*.txt)";
    }
}
