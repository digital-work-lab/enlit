package ExportImport;

import functionality.DataHolder;
import functionality.MainWindow;
import model.PDFPaper;
import model.ReferencedPaper;
import org.grobid.core.data.Person;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class CSVFile {

	List<ReferencedPaper> listReferencedPaper;
	List<PDFPaper> listPDFPaper;

	public void saveData(String filename) throws FileNotFoundException {
		
		String format = "\'";
		String separator = ";";
		listPDFPaper = DataHolder.listPDFPaper;
		listReferencedPaper = DataHolder.listReferencedPaperAll;
		
		PrintWriter csvwriter = new PrintWriter(new File(filename +".ct"));
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("id");
		builder.append(separator);
		builder.append("title");
		builder.append(separator);
		builder.append("authors");
		builder.append(separator);
		builder.append("year");
		builder.append(separator);
		builder.append("journal");
		builder.append(separator);
		builder.append("volume");
		builder.append(separator);
		builder.append("issue");
		builder.append(separator);
		builder.append("page");
		builder.append(separator);
		builder.append("score");
		builder.append(separator);
		builder.append("bibTex");
		builder.append(separator);
		builder.append("mergeID");
		builder.append(separator);
		builder.append("isInLit");
		builder.append(separator);
		builder.append("file_name");
		builder.append(separator);
		builder.append("type");
		builder.append(separator);
		builder.append("journal_abbrev");
		builder.append(separator);
		builder.append('\n');

		for(PDFPaper paper : listPDFPaper)
		{
			builder.append(format);
			builder.append(paper.getId());
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(paper.getTitle());
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(solveAuthorsSaving(paper.getAuthors()));
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(paper.getYear());
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(paper.getJournal());
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(paper.getVolume());
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(paper.getIssue());
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(paper.getPage());
			builder.append(format);;
			
			builder.append(separator);
	
			builder.append(format);
			builder.append(paper.getScore());
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(paper.getBibTex());
			builder.append(format);
			
			builder.append(separator);
			
			//no merge id
			builder.append(format);
			builder.append(format);
			
			builder.append(separator);
			
			//no isIn id
			builder.append(format);
			builder.append(format);

			builder.append(separator);

			//file_name
			builder.append(format);
			builder.append(paper.getFilename());
			builder.append(format);

			builder.append(separator);

			//no type name
			builder.append(format);
			builder.append(format);

			builder.append(separator);

			//journal_abbrev
			builder.append(format);
			builder.append(paper.getJournalAbbrev());
			builder.append(format);

			builder.append('\n');

		}
		for(ReferencedPaper paper : listReferencedPaper)
		{
			builder.append(format);
			builder.append(paper.getReferenceID());
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(paper.getTitle());
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(solveAuthorsSaving(paper.getAuthors()));
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(paper.getYear());
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(paper.getJournal());
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(paper.getVolume());
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(paper.getIssue());
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(paper.getPage());
			builder.append(format);;
			
			builder.append(separator);

			builder.append(format);
			builder.append(paper.getScore());
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(paper.getBibTex());
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(paper.getMergeID());
			builder.append(format);
			
			builder.append(separator);
			
			builder.append(format);
			builder.append(paper.isInLiteratureSet());
			builder.append(format);

			builder.append(separator);

			//no file name
			builder.append(format);
			builder.append(format);

			builder.append(separator);

			//type
			builder.append(format);
			builder.append(paper.getType());
			builder.append(format);

			builder.append(separator);

			//journal_abbrev
			builder.append(format);
			builder.append(paper.getJournalAbbrev());
			builder.append(format);

			builder.append(System.getProperty("line.separator"));
		}
		
		csvwriter.write(builder.toString());
		csvwriter.close();
	    JOptionPane.showMessageDialog(MainWindow.frame, "successfully saved to: " +filename +".ct");

	}

	public void loadData(String filename) {

		BufferedReader br = null;
		if(filename.contains(".ct"))
		{
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(MainWindow.frame, "could not open file");
			e1.printStackTrace();
		}
		
		String line = "";
		List<PDFPaper> listPDFPaper = new ArrayList<PDFPaper>();
		List<ReferencedPaper> listReferencedPaper = new ArrayList<ReferencedPaper>();
		
		try {
			br.readLine();
			while(((line = br.readLine()) != null))
			{
				String[] object = line.split("\';\'");
				String id = object[0].replaceAll("\'", "");
				String title = object[1];
				String authors = object[2];
				String year = object[3];
				String journal = object[4];
				String volume = object[5];
				String issue = object[6];
				String page = object[7];
				int score = Integer.valueOf(object[8]);
				String bibTex = object[9];
				String journalAbbrev = object[14];


				if(object[0].contains(":"))
				{
					String mergeId = object[10];
					String isInLit = object[11];
					String type = object[13];
					ReferencedPaper ref = new ReferencedPaper(title, solveAuthorsLoading(authors), journal, journalAbbrev, year, volume, issue, page, score, type);
					ref.setReferenceID(id);
					if(!mergeId.equals("null"))
							{
						
						ref.setMergeID(mergeId);

							}
					else
					{
						ref.setMergeID(null);
					}
					ref.setInLiteratureSet(Boolean.valueOf(isInLit));
					ref.setBibTex(bibTex);
		
					listReferencedPaper.add(ref);
				}
				if(!object[0].contains(":"))
				{

						PDFPaper pdf = new PDFPaper(Integer.valueOf(id), title, solveAuthorsLoading(authors), journal, journalAbbrev, year, volume, issue, page, score);
						String file_Name = object[12];
						pdf.setFilename(file_Name);
						pdf.setBibTex(bibTex);
						listPDFPaper.add(pdf);

				}
				
			}
			DataHolder.listReferencedPaperAll = listReferencedPaper;
			DataHolder.listPDFPaper = listPDFPaper;
			
			for(PDFPaper pdfPaper : DataHolder.listPDFPaper)
			{
				List<ReferencedPaper> listRef = new ArrayList<>();
				for(ReferencedPaper ref : DataHolder.listReferencedPaperAll)
				{
					String[] refID = ref.parseReferenceID();

					if(refID[0].equals(pdfPaper.getId()))
					{
						listRef.add(ref);
					}
				}
				pdfPaper.setListReferencedPaper(listRef);
			}
		} catch (IOException e) {

			JOptionPane.showMessageDialog(MainWindow.frame, "File Is Incorrect");

			e.printStackTrace();
		}
		}
		else
		{
			JOptionPane.showMessageDialog(MainWindow.frame, "no ct file");

		}
	}
	
public void export(String filename) throws FileNotFoundException {
		
		String format = "\"";
		String separator = ",";
		listReferencedPaper = DataHolder.listReferencedPaperAll;
		
		PrintWriter csvwriter = new PrintWriter(new File(filename +".csv"));
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("id");
		builder.append(separator);
		builder.append("title");
		builder.append(separator);
		builder.append("authors");
		builder.append(separator);
		builder.append("year");
		builder.append(separator);
		builder.append("journal");
		builder.append(separator);
		builder.append("volume");
		builder.append(separator);
		builder.append("issue");
		builder.append(separator);
		builder.append("page");
		builder.append(separator);
		builder.append("score");
		
		builder.append(System.getProperty("line.separator"));

	ExportOptionWindow exportOptionWindow = new ExportOptionWindow();
	boolean allReferences = exportOptionWindow.getOption();

		int id = 1;

		for(ReferencedPaper paper : listReferencedPaper)
		{
			if((paper.getMergeID() == null && !allReferences && !paper.isInLiteratureSet())
					|| paper.getMergeID() == null && allReferences) {
				builder.append(format);
				builder.append(id);
				builder.append(format);

				builder.append(separator);

				builder.append(format);
				builder.append(paper.getTitle());
				builder.append(format);

				builder.append(separator);

				builder.append(format);
				builder.append(paper.getAuthors());
				builder.append(format);

				builder.append(separator);

				builder.append(format);
				builder.append(paper.getYear());
				builder.append(format);

				builder.append(separator);

				builder.append(format);
				builder.append(paper.getJournal());
				builder.append(format);

				builder.append(separator);

				builder.append(format);
				builder.append(paper.getVolume());
				builder.append(format);

				builder.append(separator);

				builder.append(format);
				builder.append(paper.getIssue());
				builder.append(format);

				builder.append(separator);

				builder.append(format);
				builder.append(paper.getPage());
				builder.append(format);

				builder.append(separator);

				builder.append(format);
				builder.append(paper.getScore());
				builder.append(format);

				builder.append(separator);

				builder.append(System.getProperty("line.separator"));
			}
		}
		
	    JOptionPane.showMessageDialog(MainWindow.frame, "successfully saved to: " +filename +".csv");
		csvwriter.write(builder.toString());
		csvwriter.close();
	}

	private String solveAuthorsSaving(List<Person> authors)
	{
		if(authors != null) {
			String stringAuthor = "";
			for (int i = 0; i < authors.size(); i++) {

				if(authors.get(i).getLastName() == null) {

					authors.get(i).setLastName("-");
				}
				if(authors.get(i).getFirstName() == null) {

					authors.get(i).setLastName("-");
				}

				if (i == authors.size() - 1){
					stringAuthor = stringAuthor + authors.get(i).getLastName() + "," + authors.get(i).getFirstName();
				} else {
					stringAuthor = stringAuthor + authors.get(i).getLastName() + "," + authors.get(i).getFirstName() + "#";
				}
			}
			return stringAuthor;
		}
		else
		{
			return "-";
		}
	}

	private List<Person> solveAuthorsLoading(String authors)
	{
		List<Person> listAuthors = new ArrayList<>();

		if(authors.equals("-"))
		{
			return null;
		}
		else
		{
			String[] parts = authors.split("#");

			for(String author : parts)
			{
				Person person = new Person();
				String[] divide = author.split(",");
				person.setLastName(divide[0].replace(" ",""));
				person.setFirstName(divide[1].replace(" ",""));

				listAuthors.add(person);
			}
			return listAuthors;
		}
	}
}
