package model;

public class ChartObject {

	Integer year;
	int yearFrequency;
	String journal;
	int journalFrequency;
	String authors;
	int authorsFrequency;
	
	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public int getAuthorsFrequency() {
		return authorsFrequency;
	}

	public void setAuthorsFrequency(int authorsFrequency) {
		this.authorsFrequency = authorsFrequency;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public int getJournalFrequency() {
		return journalFrequency;
	}

	public void setJournalFrequency(int journalFrequency) {
		this.journalFrequency = journalFrequency;
	}

	public ChartObject(){
		
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public int getYearFrequency() {
		return yearFrequency;
	}

	public void setYearFrequency(int yearFrequency) {
		this.yearFrequency = yearFrequency;
	}
}
