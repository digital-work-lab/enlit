package tasks;

import functionality.MainWindow;
import functionality.DataHolder;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philipo on 13.09.2017.
 */
public class FileReaderTask extends SwingWorker<Void, Integer>{

    JProgressBar jpb;
    JLabel label;
    String directory;

    public FileReaderTask(JProgressBar jpb, JLabel label, String directory) {
        this.jpb = jpb;
        this.directory = directory;
        this.label = label;
    }

    @Override
    protected void process(List<Integer> chunks) {
        int i = chunks.get(chunks.size()-1);
        jpb.setValue(i); // The last value in this array is all we care about.

    }
    @Override
    protected Void doInBackground() throws Exception {

        label.setText("Get folder...");
        publish(0);
        File path = new File(directory);
        DataHolder.listFiles = new ArrayList<>();

        if (path.isFile()) {
            DataHolder.listFiles.add(path);
        }
        if (path.isDirectory()) {

            if (path.listFiles() != null) {
                for (final File file : path.listFiles()) {
                    if (file.isFile()) {
                        DataHolder.listFiles.add(file);

                    }
                }
            } else {
                JOptionPane.showMessageDialog(MainWindow.frame,
                        "no pdfs included");
            }
        }
        publish(100);

        return null;
    }
    @Override
    protected void done() {

        BuildDataStructureTask buildData = new BuildDataStructureTask(jpb, label);
        buildData.execute();

    }

}
