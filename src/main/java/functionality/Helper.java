package functionality;

import model.Paper;
import org.apache.commons.lang3.StringUtils;
import org.grobid.core.data.BiblioItem;
import org.grobid.core.data.Person;

import java.util.List;

/**
 * Cobat main class
 * 
 * @author Philip Empl
 * 
 */
public class Helper {


	public static String createBibTex(BiblioItem resBib) {

		List<Person> authors = resBib.getFullAuthors();
		
		String author = ""; 
		
		if(authors != null)
		{
		for(Person person : authors)
		{
			author = person.getLastName();
			if(author != null)
			{
			break;
			}
		}
		}
		String year = resBib.getPublicationDate();
		
		if(year == null)
		{
			year = "0000";
		}
		if(author == null || author.equals(""))
		{
			author = "Author";
		}

		String bibTex = resBib.toBibTeX().replaceAll("\\{id", "\\{"+author+year);	

		bibTex = bibTex.replace("\n","").replace("\r", "");
		bibTex = bibTex.trim().replaceAll(" +", " ");
		return bibTex;
	}

	// checks if title is null and handle special cases
	public static void checkNullValues(Paper paper) {

		String title = paper.getTitle();
		String year = paper.getYear();
		String journal = paper.getJournal();
		String volume = paper.getVolume();
		String issue = paper.getIssue();
		String page = paper.getPage();

		if (title == null) {
			paper.setTitle("-");
		}
		if (year == null) {
			paper.setYear("-");
		}
		if (journal == null) {
			paper.setJournal("-");
		}
		if (volume == null) {
			paper.setVolume("-");
		}
		if (issue == null) {
			paper.setIssue("-");
		}
		if (page == null) {
			paper.setPage("-");
		}
		if (title != null) {
			title = title.replace("\n","").replace("\r", "");
			paper.setTitle(title);
		}
		if (year != null) {
			year = year.replace("\n","").replace("\r", "");
			paper.setYear(year);
		}
		if (journal != null) {
			journal = journal.replace("\n","").replace("\r", "");
			paper.setJournal(journal);
		}
		if (volume != null) {
			volume = volume.replace("\n","").replace("\r", "");
			paper.setVolume(volume);
		}
		if (issue != null) {
			issue = issue.replace("\n","").replace("\r", "");
			paper.setIssue(issue);
		}
		if (page != null) {
			page = page.replace("\n","").replace("\r", "");
			paper.setPage(page);
		}
	}


	// compare strings adn returns percentage in similiarity
	public static double compareStrings(String string1, String string2) {

		double leven = StringUtils.getLevenshteinDistance(
				string1.toUpperCase(), string2.toUpperCase());
		int max = Math.max(string1.length(), string2.length());

		return (max - leven) / max;
	}

	// get subsequence
	public static String getSubSequence(String title) {

		if (title.length() > 80) {
			title = title.subSequence(0, 80).toString();
		}

		return title;
	}
}
