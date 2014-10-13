
package escape;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arnaud
 */
public class FileManager {
    private String fileName = null;
    private Escape window;
    private EscapeGrid grid;
    
    public FileManager(Escape parent, EscapeGrid grid) {
        window = parent;
        this.grid = grid;
    }
    public void save() {
        try {
            PrintWriter writer;
            writer =  new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            writer.println(window.getWidth());
            writer.println(window.getHeight());
            saveGrid(writer, grid.getBlocksState());
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void save(String fileName) {
        setFileName(fileName);
        save();
    }
    public boolean isNewFile() {
        return fileName == null;
    }
    public void newFile() {
        setFileName(null);
    }
    public void open(String filename) {
        setFileName(filename);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            int width = Integer.parseInt(reader.readLine());
            int height = Integer.parseInt(reader.readLine());
            window.setSize(width, height);
            grid.resize(width-12, height-34);
            grid.setBlocksState(loadGrid(reader));
            reader.close();
        } catch(FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture");
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.lang.NumberFormatException e) {
        }
        
    }
    private boolean[][] loadGrid(BufferedReader reader) throws IOException {
        boolean[][] states = new boolean[grid.getGridWidth()][grid.getGridHeight()];
        
        for(int i=0;i<grid.getGridWidth(); i++) {
            for(int j=0;j<grid.getGridHeight();j++) {
                states[i][j] = reader.read() == '1';
            }
        }
        return states;
    }
    private void saveGrid(PrintWriter writer, boolean[][] states) throws IOException {
        for(int i=0;i<grid.getGridWidth(); i++) {
            for(int j=0;j<grid.getGridHeight();j++) {
                if(states[i][j])
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
