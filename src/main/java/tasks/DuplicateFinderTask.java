package tasks;

import functionality.DataHolder;
import functionality.DuplicateCheck;
import model.PDFPaper;
import model.ReferencedPaper;

import javax.swing.*;
import java.util.List;

/**
 * Created by Philipo on 13.09.2017.
 */
public class DuplicateFinderTask extends SwingWorker<Void, Integer>{

    JProgressBar jpb;
    JLabel label;
    int max;

    public DuplicateFinderTask(JProgressBar jpb, JLabel label) {
        this.jpb = jpb;
        this.label = label;
        max = DataHolder.listReferencedPaperAll.size();
        jpb.setMaximum(max);
    }

    @Override
    protected void process(List<java.lang.Integer> chunks) {
        int i = chunks.get(chunks.size()-1);
        jpb.setValue((max*i)/max);
        label.setText("Calculate in-corpus centrality of reference (" + i + " of " + max+")");

    }
    @Override
    protected java.lang.Void doInBackground() throws Exception {

        int i = 1;
        for (ReferencedPaper refPaper : DataHolder.listReferencedPaperAll) {

            publish(i);
            if (refPaper.getMergeID() == null) {

                for (ReferencedPaper refPaper2 : DataHolder.listReferencedPaperAll) {

                    if (refPaper.getReferenceID() != refPaper2.getReferenceID()) {

                        boolean isDuplicate = DuplicateCheck.isDuplicate(refPaper, refPaper2);

                        if (isDuplicate) {
                            refPaper.setScore(refPaper.getScore() + 1);
                            refPaper2.setMergeID(refPaper.getReferenceID());

                            String[] id = refPaper2.parseReferenceID();
                            PDFPaper pdfPaper = refPaper2.findPDF(id[0]);
                            ReferencedPaper ref = pdfPaper.getSingleReference(refPaper2.getReferenceID());
                            ref.setMergeID(refPaper2.getReferenceID());
                        }
                    }
                }
            }
            i += 1;
        }
        return null;
    }
    @Override
    protected void done() {

        CompareToCurrentLiteratureCorpusTask compareToCurrentLiteratureCorpusTask = new CompareToCurrentLiteratureCorpusTask(jpb, label);
        compareToCurrentLiteratureCorpusTask.execute();

    }
}
