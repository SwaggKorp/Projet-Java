
package escape;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author arnaud
 */
public class FileManager {
    private String fileName;
    private Escape window;
    private Grid grid;
    private JFileChooser fileChooser;
    
    public FileManager(Escape parent, Grid grid) {
        window = parent;
        this.grid = grid;
        setFileName(null);                                                      // set initial window title and filename as new file
        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Escape file","ecp");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);
    }
    public void openFileDialog() {
        int result = fileChooser.showOpenDialog(window);
        if(result == JFileChooser.APPROVE_OPTION) {
            open(fileChooser.getSelectedFile().getPath());
        }
    }
    public void saveFileDialog() {
        int result = fileChooser.showSaveDialog(window);
        if(result == JFileChooser.APPROVE_OPTION)
            save(fileChooser.getSelectedFile().getPath()+".ecp");
    }
    public void save() {
        if(fileName==null) {
            saveFileDialog();
        } else { 
            save(fileName);
        }
    }
    public void save(String fileName) {
        setFileName(fileName);
        try {
            PrintWriter writer;
            writer =  new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            writer.println(window.getWidth());
            writer.println(window.getHeight());
            saveGrid(writer);
            writer.close();
        } catch (java.io.IOException ex) {
            System.err.println("saving file error : cannot write the file");
        }
    }

    public void newFile() {
        setFileName(null);
        for(int i=0;i<grid.getGridWidth(); i++) {
            for(int j=0;j<grid.getGridHeight();j++) {
                grid.getBlock(i,j).setAlive(true);
                grid.getBlock(i,j).reconnectAll();
            }
        }
        grid.repaint();
    }
    public void open(String filename) {
        setFileName(filename);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName)); // open the file
            int width = Integer.parseInt(reader.readLine());                    // resize the window to the file's value
            int height = Integer.parseInt(reader.readLine());
            window.setSize(width, height);                                      
            grid.resize(width, height-22);                                      // create the missing blocks and/or remove the exceeding ones
            loadGrid(reader);                                                   // update block states (wall or ground)
       
            reader.close();
        } catch(java.io.FileNotFoundException exc) {
            System.err.println("opening error : file not found");
        } catch (java.io.IOException|java.lang.NumberFormatException e) {
            System.err.println("opening error : invalid file");
        }
    }
    private void loadGrid(BufferedReader reader) throws java.io.IOException {
        for(int i=0;i<grid.getGridWidth(); i++) {
            for(int j=0;j<grid.getGridHeight();j++) {
                grid.getBlock(i,j).setAlive(reader.read() == '1');
                grid.getBlock(i,j).reconnectAll();
            }
        }
        grid.repaint();
    }
    private void saveGrid(PrintWriter writer) throws java.io.IOException {
        for(int i=0;i<grid.getGridWidth(); i++) {
            for(int j=0;j<grid.getGridHeight();j++) {
                if(grid.getBlock(i, j).alive())
                    writer.write('1');
                else 
                    writer.write('0');
            }
        }
    }
    private void setFileName(String filename) {
        this.fileName = filename;
        if(fileName == null) {
             window.setTitle("Escape - New file");
        } else {
            if(!fileName.endsWith(".ecp"))
                fileName += ".ecp";
            window.setTitle("Escape - " + fileName);
        }
    }
}
