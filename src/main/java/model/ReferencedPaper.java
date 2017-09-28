package model;


import functionality.DataHolder;
import functionality.Helper;
import org.grobid.core.data.Person;

import java.util.List;

public class ReferencedPaper extends Paper {

	String id;

	String mergeID;
	
	boolean inLiteratureSet;

	String type;

	public boolean isInLiteratureSet() {
		return inLiteratureSet;
	}

	public void setInLiteratureSet(boolean inLiteratureSet) {
		this.inLiteratureSet = inLiteratureSet;
	}



	public ReferencedPaper(String title, List<Person> authors, String journal, String journalAbbrev,
						   String year, String volume, String issue, String page, int score, String type) {
		super.title = title;
		super.authors = authors;
		super.journal = journal;
		super.journalAbbrev = journalAbbrev;
		super.year = year;
		super.volume = volume;
		super.issue = issue;
		super.page = page;
		super.score = score;
	}

	public String getMergeID() {
		return mergeID;
	}

	public void setMergeID(String mergeID) {
		this.mergeID = mergeID;
	}


	public String getReferenceID() {
		return id;
	}

	public void setReferenceID(int idParentPaper, int idReferencePaper) {

		this.id = idParentPaper + ":" + idReferencePaper;
	}
	public void setReferenceID(String id) {

		this.id = id;
	}

	public String toString() {
		return title + ", " + authors + ", " + year + ", " + journal;

	}

	public ReferencedPaper findParent() {
		for (ReferencedPaper paper : DataHolder.listReferencedPaperAll) {
			if (paper.getReferenceID().equals(this.getMergeID())) {
				return paper;
			}
		}
		return null;
	}
	
	public PDFPaper findPDF(String id){
		for(PDFPaper paper : DataHolder.listPDFPaper)
		{
			if(String.valueOf(paper.getId()).equals(id))
			{
				return paper;
			}
		}
		return null;
	}
	
	public String[] parseReferenceID()
	{
		return this.id.split(":");
		
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


}
