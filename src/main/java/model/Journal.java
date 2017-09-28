package model;

/**
 * Created by Philipo on 25.09.2017.
 */
public class Journal {


    String journal;
    String abbr;

    public Journal(String abbr, String journal)
    {
        this.journal = journal;
        this.abbr = abbr;
    }
    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }



}
