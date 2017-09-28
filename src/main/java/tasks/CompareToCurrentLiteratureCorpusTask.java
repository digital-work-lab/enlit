package tasks;

import functionality.MainWindow;
import functionality.Helper;
import functionality.DataHolder;
import model.PDFPaper;
import model.ReferencedPaper;

import javax.swing.*;

/**
 * Created by Philipo on 13.09.2017.
 */

public class CompareToCurrentLiteratureCorpusTask  extends SwingWorker<Void, Integer>{

    JProgressBar jpb;
    JLabel label;
    int max;

    public CompareToCurrentLiteratureCorpusTask(JProgressBar jpb, JLabel label) {
        this.jpb = jpb;
        this.label = label;
        max = DataHolder.listPDFPaper.size();
        jpb.setMaximum(max);
    }

    @Override
    protected Void doInBackground() throws Exception {

        label.setText("Comparison to current literature corpus...");
        int count = 1;
        for(PDFPaper pdf : DataHolder.listPDFPaper)
        {
            publish(count);
            for(ReferencedPaper ref : DataHolder.listReferencedPaperAll)
            {
                if(ref.getMergeID() == null)
                {
                    double similiar = Helper.compareStrings(ref.getTitle(), pdf.getTitle());

                    if(similiar > 0.8)
                    {
                        ref.setInLiteratureSet(true);
                    }
                }
            }
            count +=1;
        }

        return null;
    }
    @Override
    protected void done() {

        VisualizationTaskMain visualizationTaskMain = new VisualizationTaskMain();
        visualizationTaskMain.execute();

        MainWindow.progressDialog.dispose();
    }
}
