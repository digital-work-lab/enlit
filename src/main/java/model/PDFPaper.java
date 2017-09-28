package model;

import org.grobid.core.data.Person;

import java.util.List;

public class PDFPaper extends Paper {

	List<ReferencedPaper> listReferencedPaper;

	int id;
	String filename;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PDFPaper()
	{
		
	}



	public PDFPaper(int id, String title, List<Person> authors, String journal, String journalAbbrev,
					String year, String volume, String issue, String page, int score) {
		this.id = id;
		super.title = title;
		super.authors = authors;
		super.journal = journal;
		super.journalAbbrev = journalAbbrev;
		super.year = year;
		super.score = score;
		super.volume = volume;
		super.issue = issue;
		super.page = page;

	}

	public List<ReferencedPaper> getListReferencedPaper() {
		return listReferencedPaper;
	}

	public void setListReferencedPaper(List<ReferencedPaper> listReferencedPaper) {
		this.listReferencedPaper = listReferencedPaper;
	}

	public String toString() {
		return String.valueOf(id);

	}

	public ReferencedPaper getSingleReference(String id)
	{
		ReferencedPaper referencedPaper = null;

		for(ReferencedPaper ref : listReferencedPaper)
		{
			if(ref.getReferenceID().equals(id))
			{
				referencedPaper = ref;
			}
		}
		return referencedPaper;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}


}
