package tasks;

import functionality.DataHolder;
import functionality.MainWindow;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.mock.MockContext;
import org.grobid.core.utilities.GrobidProperties;

import javax.swing.*;
import java.util.List;
import java.util.prefs.Preferences;

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

            Preferences preferences = Preferences.userNodeForPackage(MainWindow.class);

        dataHolder.setpGrobidHome(preferences.get("pGrobidHome", ""));
        dataHolder.setpGrobidProperties(preferences.get("pGrobidProperties", ""));


        if(DataHolder.pGrobidHome == null||  DataHolder.pGrobidProperties == null) {

                if(DataHolder.pGrobidHome == null)
                {
                    JOptionPane.showMessageDialog(MainWindow.frame, "Please set GROBID home path..", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
                else if(DataHolder.pGrobidProperties == null)
                {
                    JOptionPane.showMessageDialog(MainWindow.frame, "Please set GROBID properties path.", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(MainWindow.frame, "Please set GROBID paths. ", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }



        MockContext.setInitialContext(dataHolder.getpGrobidHome(), dataHolder.getpGrobidProperties());
        GrobidProperties.getInstance();

        DataHolder.engine = GrobidFactory.getInstance().createEngine();

        MockContext.destroyInitialContext();

        publish(100);
        return null;
    }

    @Override
    protected void done() {

        FileReaderTask fileReaderTask = new FileReaderTask(jpb, label, directory);
        fileReaderTask.execute();


    }


}
