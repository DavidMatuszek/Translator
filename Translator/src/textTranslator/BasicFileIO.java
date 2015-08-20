package textTranslator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;

/*
 * File BasicFileIO.java
 * Created on Sep 19, 2006
 */

public class BasicFileIO {
    static File previousInputFile = null;
    static File previousOutputFile = null;
    
    public static BufferedReader getReader() {
        File file = getInputFile();
        try {
            if (file != null) {
                String fileName = file.getCanonicalPath();
                return new BufferedReader(new FileReader(fileName));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static File getInputFile() {
        JFileChooser chooser = new JFileChooser(previousInputFile);
        chooser.setDialogTitle("Load which file?");
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            previousInputFile = chooser.getSelectedFile().getParentFile();
            return chooser.getSelectedFile();
        }
        else {
            return null;
        }
    }
    
    public static PrintWriter getWriter() {
        JFileChooser chooser = new JFileChooser(previousOutputFile);
        chooser.setDialogTitle("Save file as?");
        int result = chooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String fileName;
            try {
                if (file != null) {
                    fileName = file.getCanonicalPath();
                    previousOutputFile = file.getParentFile();
                    return new PrintWriter(new FileOutputStream(fileName), true);
                }
            }
            catch (IOException e) { }       
        }
        return null;
    }

}
