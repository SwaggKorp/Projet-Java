
package escape;

import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * FileManager provides methods to open and load, save and create escape file
 * FileManager also shows the file chooser and the file is undefined
 */
public class FileManager {
    private String fileName;
    private Escape window;                // reference to the main window
    private Grid grid;                    // reference to the game grid
    private JFileChooser fileChooser;
    
    // create file manager with references to the main window and the game grid (used to load levels)
    public FileManager(Escape parent, Grid grid) {
        window = parent;
        this.grid = grid;
        setFileName(null);                                                      // set initial window title and filename as new file
        // create and configure the file chooser
        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Escape file","ecp");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new File(getClass().getResource("/levels/").getFile()) );
    }
    // shows a file chooser to open a file
    public void openFileDialog() {
        int result = fileChooser.showOpenDialog(window);
        if(result == JFileChooser.APPROVE_OPTION) {
            open(fileChooser.getSelectedFile().getPath());
        }
    }
    // shows a file chooser to save a file
    public void saveFileDialog() {
        int result = fileChooser.showSaveDialog(window);
        if(result == JFileChooser.APPROVE_OPTION)
            save(fileChooser.getSelectedFile().getPath()+".ecp");
    }
    // save the current grid state. If the current file has never been saved, open a save file dialog
    public void save() {
        if(fileName==null) { // if the file has never been saved
            saveFileDialog();
        } else { 
            save(fileName);
        }
    }
    // save the current grid state in the specified file
    // filename : the absolute path to the file 
    public void save(String fileName) {
        setFileName(fileName);
        try {
            PrintWriter writer;
            writer =  new PrintWriter(new BufferedWriter(new FileWriter(fileName))); // open a writer into the specified file
            writer.println(window.getWidth());                                       // save the size of the window (and thereby the size of the grid)
            writer.println(window.getHeight());
            saveGrid(writer);                                                        // save block states
            writer.close();
        } catch (java.io.IOException ex) {
            System.err.println("saving file error : cannot write the file");
        }
    }
    // create a new file and clear the grid
    public void newFile() {
        setFileName(null);
        // clear the grid (every block is set to alive)
        for(int i=0;i<grid.getGridWidth(); i++) {
            for(int j=0;j<grid.getGridHeight();j++) {
                grid.getBlock(i,j).setAlive(true);
                grid.getBlock(i,j).reconnectAll();   // update the references to neighbours (for shorter path)
            }
        }
        grid.repaint();
    }
    // open and load a grid state in the 
    public void open(String filename) {
        setFileName(filename);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName)); // open the file
            int width = Integer.parseInt(reader.readLine());                      // resize the window to the file's value
            int height = Integer.parseInt(reader.readLine());
            window.setSize(width, height);                                      
            grid.resizeGrid(width, height-22);                                    // create the missing blocks and/or remove the exceeding ones
            loadGrid(reader);                                                     // update block states (wall or ground)
       
            reader.close();
        } catch(java.io.FileNotFoundException exc) {
            System.err.println("opening error : file not found");
        } catch (java.io.IOException|java.lang.NumberFormatException e) {
            System.err.println("opening error : invalid file");
        }
    }
    // load the file's data into the grid
    private void loadGrid(BufferedReader reader) throws java.io.IOException {
        for(int i=0;i<grid.getGridWidth(); i++) {
            for(int j=0;j<grid.getGridHeight();j++) {
                grid.getBlock(i,j).setAlive(reader.read() == '1');   // translate file's 1 into alive states and 0 into dead (wall) state
            }
        }
        grid.repaint();
    }
    // translate the grid state into 0 and 1 and write it in the specified writer
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
    // set the current file absolute path, and update the window's title
    private void setFileName(String filename) {
        this.fileName = filename;
        if(fileName == null) {                                         // if the file has never been saved
             window.setTitle("Escape - New file");
        } else {
            if(!fileName.endsWith(".ecp"))                             // if the file extension is missing
                fileName += ".ecp";
            window.setTitle("Escape - " + fileName);
        }
    }
}
