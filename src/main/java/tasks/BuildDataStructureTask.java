package tasks;

import functionality.Helper;
import functionality.DataHolder;
import model.Journal;
import model.PDFPaper;
import model.ReferencedPaper;
import org.grobid.core.data.BibDataSet;
import org.grobid.core.data.BiblioItem;
import org.grobid.core.data.Person;
import org.grobid.core.exceptions.GrobidException;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Philipo on 13.09.2017.
 */
public class BuildDataStructureTask extends SwingWorker<Integer, Integer>{

    JProgressBar jpb;
    JLabel label;
    int max;
    List<Journal> abbrevList;

    public BuildDataStructureTask(JProgressBar jpb, JLabel label) {
        this.jpb = jpb;
        this.label = label;
        jpb.setIndeterminate(true);
        max = DataHolder.listFiles.size();
        jpb.setMaximum(max);
    }

    @Override
    protected void process(List<java.lang.Integer> chunks) {
        int i = chunks.get(chunks.size()-1);
        jpb.setValue(i);
        label.setText("Reading files... (" + i + " of " + max+")");

    }
    @Override
    protected Integer doInBackground() throws Exception {

        int idPDF = 1;
        label.setText("Scanning files...");

        loadJournalAbbrFile();
        for (File file : DataHolder.listFiles) {


            try {

                BiblioItem bibItem = new BiblioItem();

                try {
                    System.out.println(file.getAbsolutePath());

                    String header = DataHolder.engine.processHeader(file.getAbsolutePath(), 1,
                            bibItem);
                    System.out.println("check2");

                    if (header == null) {
                        DataHolder.listEmptyOrError.add(file.getAbsolutePath());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    DataHolder.listEmptyOrError.add(file.getAbsolutePath());
                }

                List<Person> authors = bibItem.getFullAuthors();
                if (authors != null) {
                    for (Person person : authors) {
                        if (person.getFirstName() == null) {
                            person.setFirstName("-");
                        }
                        if (person.getLastName() == null) {
                            person.setLastName("-");
                        }
                    }
                }
                if(bibItem.getJournal() == null)
                {
                    bibItem.setJournal("-");
                }
                if(bibItem.getJournalAbbrev() == null)
                {
                    bibItem.setJournalAbbrev("-");
                }
                bibItem.setJournalAbbrev(solveJournal(bibItem.getJournal().split(" ")));

                PDFPaper pdfPaper = new PDFPaper(idPDF, bibItem.getTitle(),
                        authors, bibItem.getJournal(), bibItem.getJournalAbbrev(),
                        bibItem.getPublicationDate(), bibItem.getVolumeBlock(), bibItem.getIssue(), bibItem.getPageRange(), 1);

                pdfPaper.setFilename(file.getName());
                pdfPaper.setBibTex(Helper.createBibTex(bibItem));
                Helper.checkNullValues(pdfPaper);

                List<BibDataSet> listReferences = DataHolder.engine.processReferences(
                        file, 1);

                if (!listReferences.isEmpty()) {
                    List<ReferencedPaper> listReferencedPaper = new ArrayList<>();
                    int idREF = 1;

                    for (BibDataSet bd : listReferences) {


                        solveFieldValues(bd);

                        if(!bd.getResBib().getJournal().equals("-")) {
                            bd.getResBib().setJournalAbbrev(solveJournal(bd.getResBib().getJournal().split(" ")));
                        }

                        ReferencedPaper referencedPaper = new ReferencedPaper(
                                bd.getResBib().getTitle(), bd.getResBib()
                                .getFullAuthors(), bd.getResBib()
                                .getJournal(), bd.getResBib().getJournalAbbrev(), bd.getResBib()
                                .getPublicationDate(), bd.getResBib().getVolumeBlock(), bd.getResBib().getIssue(), bd.getResBib().getPageRange(), 1, bd.getResBib().getType());

                        referencedPaper.setType(bd.getResBib().getType());
                        referencedPaper.setReferenceID(idPDF, idREF);
                        referencedPaper.setMergeID(null);
                        referencedPaper.setScore(1);
                        referencedPaper.setInLiteratureSet(false);
                        Helper.checkNullValues(referencedPaper);

                        referencedPaper.setBibTex(Helper.createBibTex(bd.getResBib()));
                        idREF += 1;
                        listReferencedPaper.add(referencedPaper);
                    }
                    jpb.setIndeterminate(false);
                    publish(idPDF);
                    idPDF += 1;
                    pdfPaper.setListReferencedPaper(listReferencedPaper);
                    pdfPaper.setScore(1);
                    DataHolder.listPDFPaper.add(pdfPaper);
                    DataHolder.listReferencedPaperAll.addAll(listReferencedPaper);


                } else {
                    DataHolder.listEmptyOrError.add(file.getAbsolutePath());
                }

                // engine.processAllCitationsInPDFPatent(file, nplResults,
                // patentResults, false);

            } catch (Exception e) {
                e.printStackTrace();
                DataHolder.listEmptyOrError.add(file.getAbsolutePath());
            }
        }

        return null;
    }

    private void loadJournalAbbrFile() {

        abbrevList = new ArrayList<>();
            File file = new File(getClass().getClassLoader().getResource("abbrevSet.txt").getFile());

            try (Scanner scanner = new Scanner(file)) {

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.contains("=")) {
                        String[] journalParts = line.split("=");

                        if (journalParts.length == 2) {
                            Journal journal = new Journal(journalParts[0].trim(), journalParts[1].trim());
                            abbrevList.add(journal);
                        }
                    }
                }
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    private void solveFieldValues(BibDataSet bd) {


        if (bd.getResBib()
                .getFullAuthors() != null) {
            for (Person person : bd.getResBib()
                    .getFullAuthors()) {
                if (person.getFirstName() == null) {
                    person.setFirstName("-");
                }
                if (person.getLastName() == null) {
                    person.setLastName("-");
                }
                if(person.getFirstName().length() > 1)
                {
                    String temp = person.getLastName();
                    person.setLastName(person.getFirstName());
                    person.setFirstName(temp);
                }
            }
        }
        if (bd.getResBib().getJournal() == null) {
            if (bd.getResBib().getReference() == null) {
                bd.getResBib().setType("book");
            } else {
                bd.getResBib().setType("conference");
            }
        } else {
            bd.getResBib().setType("article");
        }

        if(bd.getResBib().getTitle() != null)
        {
            if(bd.getResBib().getTitle().toLowerCase().startsWith("review:")
                    || bd.getResBib().getTitle().toLowerCase().startsWith("research commentary:")
                    || bd.getResBib().getTitle().toLowerCase().startsWith("note:")
                    || bd.getResBib().getTitle().toLowerCase().startsWith("research report:")
                    || bd.getResBib().getTitle().toLowerCase().startsWith("research design:"))
            {
                String[] parts = bd.getResBib().getTitle().split(":");

                String title = "";
                for(int i = 1; i < parts.length; i++)
                {
                    title = title + parts[i];
                }

                bd.getResBib().setTitle(title.trim());
            }
        }

        if(bd.getResBib().getJournal() == null)
        {
            bd.getResBib().setJournal("-");
        }

        if(bd.getResBib().getJournalAbbrev() == null)
        {
            bd.getResBib().setJournalAbbrev("-");
        }

    }

    private String solveJournal(String[] journalParts ) {


            String journalString = "";
            for(String parts : journalParts) {

                parts = parts.trim();


                if(parts.length() > 1 && (!parts.toLowerCase().equals("the") || !parts.toLowerCase().equals("and") || !parts.toLowerCase().equals("of")))
                {
                    if (!parts.contains(".")) {

                        boolean abbrevFound = false;

                        for (Journal journal : abbrevList) {

                            if (journal.getJournal().toLowerCase().equals(parts.toLowerCase()))
                            {
                                journalString = journalString + " "+ journal.getAbbr() +" ";
                                abbrevFound = true;
                                break;
                            }
                        }

                        if(abbrevFound == false)
                        {
                            journalString = journalString + " " + parts+ " ";

                        }
                    }
                    else
                    {
                        boolean abbrevFound = false;

                        int length = parts.length()-1;

                        for (Journal journal : abbrevList) {

                            if(journal.getAbbr().length()-1 >= length ) {

                                double similar = Helper.compareStrings(journal.getAbbr().toLowerCase().substring(0, length), parts.toLowerCase().substring(0, length));
                                {
                                    if(similar > 0.9) {
                                        journalString = journalString + " " + journal.getAbbr() + " ";
                                        abbrevFound = true;
                                        break;
                                    }
                                }
                            }
                        }

                        if(!abbrevFound)
                        {
                            journalString = journalString + " " + parts + " ";
                        }
                    }
                }
                else if(!parts.contains("."))
                {
                    journalString = journalString + " " + parts +".";
                }
                else
                {
                    journalString = journalString + " " + parts;
                }

            }

       return journalString.trim();
    }

    @Override
    protected void done() {

        DuplicateFinderTask duplicateFinder = new DuplicateFinderTask(jpb, label);
        duplicateFinder.execute();


    }
    }
