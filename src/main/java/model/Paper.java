package model;

import org.grobid.core.data.Person;

import java.util.List;

public class Paper {

	String title;
	List<Person> authors;
	String journal;
	String volume;
	String issue;
	String page;

	public String getJournalAbbrev() {
		return journalAbbrev;
	}

	public void setJournalAbbrev(String journalAbbrev) {
		this.journalAbbrev = journalAbbrev;
	}

	public String journalAbbrev;

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	String year;
	int score;
	String bibTex;

	public String getBibTex() {
		return bibTex;
	}

	public void setBibTex(String bibTex) {
		this.bibTex = bibTex;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Person> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Person> authors) {
		this.authors = authors;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getAuthorsString()
	{
		String stringAuthor = "";

		if(authors != null) {
			for (Person person : authors) {
				stringAuthor = stringAuthor + person.getLastName() + ", " + person.getFirstName() + ".; ";
			}
		}
		else
		{
			stringAuthor = "-";
		}
		return stringAuthor;
	}
	public String getSingleAuthor(int i)
	{

		return authors.get(i).getLastName() + ", " + authors.get(i).getFirstName();
	}
}
