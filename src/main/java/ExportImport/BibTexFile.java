package ExportImport;

import functionality.DataHolder;
import functionality.MainWindow;
import model.ReferencedPaper;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class BibTexFile {

	public BibTexFile(String path) throws IOException {

		BufferedWriter br = new BufferedWriter(new FileWriter(path + ".bib"));

		ExportOptionWindow exportOptionWindow = new ExportOptionWindow();
		boolean allReferences = exportOptionWindow.getOption();

		for (ReferencedPaper paper : DataHolder.listReferencedPaperAll)

			if((paper.getMergeID() == null && !allReferences && !paper.isInLiteratureSet())
					|| paper.getMergeID() == null && allReferences) {
				br.write(paper.getBibTex()
						+ System.getProperty("line.separator"));
			}
		br.close();

		JOptionPane.showMessageDialog(MainWindow.frame,
				"successfully saved to: " + path + ".bib");

	}

}
