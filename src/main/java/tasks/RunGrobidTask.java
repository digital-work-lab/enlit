package tasks;

import functionality.DataHolder;
import functionality.MainWindow;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.main.GrobidHomeFinder;
import org.grobid.core.utilities.GrobidProperties;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

import static functionality.DataHolder.pGrobidHome;

/**
 * Created by Philipo on 13.09.2017.
 */
public class RunGrobidTask extends SwingWorker<Void, Integer> {

    JProgressBar jpb;
    JLabel label;
    String directory;

    public RunGrobidTask(JProgressBar jpb, JLabel label, String directory) {
        this.jpb = jpb;
        this.label = label;
        this.directory = directory;
    }
    @Override
    protected void process(List<Integer> chunks) {
        int i = chunks.get(chunks.size()-1);
        jpb.setValue(i); // The last value in this array is all we care about.
    }
    @Override
    protected Void doInBackground() throws Exception {

        DataHolder dataHolder = new DataHolder();

        publish(0);
        label.setText("Initializing...");
try {
    Preferences preferences = Preferences.userNodeForPackage(MainWindow.class);

    dataHolder.setpGrobidHome(preferences.get("pGrobidHome", ""));
    dataHolder.setpGrobidProperties(preferences.get("pGrobidProperties", ""));


    if (pGrobidHome == null || DataHolder.pGrobidProperties == null) {

        if (pGrobidHome == null) {
            JOptionPane.showMessageDialog(MainWindow.frame, "Please set GROBID home path..", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        } else if (DataHolder.pGrobidProperties == null) {
            JOptionPane.showMessageDialog(MainWindow.frame, "Please set GROBID properties path.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(MainWindow.frame, "Please set GROBID paths. ", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }


    GrobidHomeFinder grobidHomeFinder = new GrobidHomeFinder(Arrays.asList("C:/Users/Philip/AndroidStudioProjects/copying/grobid/grobid-home"));
    GrobidProperties.getInstance(grobidHomeFinder);
    DataHolder.engine = GrobidFactory.getInstance().createEngine();

    System.out.println(">>>>>>>> GROBID_HOME=" + GrobidProperties.get_GROBID_HOME_PATH());
    Thread.sleep(10000);
    publish(100);
}
catch(Exception e)
{
    e.printStackTrace();
}
        return null;
    }

    @Override
    protected void done() {

        FileReaderTask fileReaderTask = new FileReaderTask(jpb, label, directory);
        fileReaderTask.execute();


    }


}
