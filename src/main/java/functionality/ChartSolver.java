package functionality;

import model.ChartObject;
import model.PDFPaper;
import model.ReferencedPaper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.TableOrder;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ChartSolver {


	public static List<ChartObject> createChartObjectsPDFS() {

		List<Integer> listYear = new ArrayList<>();

		for (PDFPaper paper : DataHolder.listPDFPaper) {

			String regex = "[0-9]+";
			String year = paper.getYear();
			if (year.length() == 4 && year.matches(regex)) {
				listYear.add(Integer.valueOf(paper.getYear()));
			}

			Set<Integer> chas = new HashSet<>(listYear);
			chas.addAll(listYear);
			listYear.clear();
			listYear.addAll(chas);
		}
		List<ChartObject> listChartObject = new ArrayList<ChartObject>();

		for (int i = 0; i < listYear.size(); i++) {
			ChartObject chartObject = new ChartObject();
			String regex = "[0-9]+";

			chartObject.setYear(Integer.valueOf(listYear.get(i)));
			chartObject.setYearFrequency(0);
			for (int j = 0; j < DataHolder.listPDFPaper.size(); j++) {
				if (!DataHolder.listPDFPaper.get(j).getYear().equals("-")
						&& (DataHolder.listPDFPaper.get(j).getYear().length() == 4) && DataHolder.listPDFPaper.get(j).getYear().matches(regex)) {

					Integer year = Integer.valueOf(DataHolder.listPDFPaper
							.get(j).getYear());

					if (year.intValue() == listYear.get(i).intValue()) {
						chartObject.setYearFrequency(chartObject
								.getYearFrequency() + 1);
					}
				}
			}
			listChartObject.add(chartObject);
		}
		for (int i = 0; i < listChartObject.size(); i++) {

			int year1 = listChartObject.get(i).getYear();

			for (int j = 0; j < listChartObject.size(); j++) {
				int year2 = listChartObject.get(j).getYear();

				if ((year1 == year2) && (!(i == j))) {
					listChartObject.get(i).setYearFrequency(
							listChartObject.get(i).getYearFrequency() + 1);
				}
			}
		}

		Collections.sort(listChartObject, new Comparator<ChartObject>() {

			@Override
			public int compare(ChartObject ca1, ChartObject ca2) {
				return (ca1.getYear()).compareTo((ca2.getYear()));
			}

		});

		return listChartObject;

	}

	public static List<ChartObject> createChartObjectsReferences() {

		List<Integer> listYear = new ArrayList<>();

		for (ReferencedPaper paper : DataHolder.listReferencedPaperAll) {

			if ((!paper.getTitle().equals("-")) && (paper.getMergeID() == null)) {

				String regex = "[0-9]+";
				String year = paper.getYear();
				if (year.length() == 4 && year.matches(regex)) {
					listYear.add(Integer.valueOf(paper.getYear()));
				}

			}
			Set<Integer> chas = new HashSet<>(listYear);
			chas.addAll(listYear);
			listYear.clear();
			listYear.addAll(chas);
		}

		List<ChartObject> listChartObject = new ArrayList<ChartObject>();

		for (int i = 0; i < listYear.size(); i++) {
			ChartObject chartObject = new ChartObject();

			String regex = "[0-9]+";
			chartObject.setYear(Integer.valueOf(listYear.get(i)));
			chartObject.setYearFrequency(0);
			for (int j = 0; j < DataHolder.getListReferencedPaperAll().size(); j++) {

				if ((!DataHolder.getListReferencedPaperAll().get(j).getTitle().equals("-")) && (DataHolder.getListReferencedPaperAll().get(j).getMergeID() == null)) {

					if (!DataHolder.getListReferencedPaperAll().get(j).getYear().equals("-")
							&& (DataHolder.getListReferencedPaperAll().get(j).getYear().length() == 4) && DataHolder.getListReferencedPaperAll().get(j).getYear().matches(regex)) {
						Integer year = Integer.valueOf(DataHolder.getListReferencedPaperAll()
								.get(j).getYear());

						if (year.intValue() == listYear.get(i).intValue()) {
							chartObject.setYearFrequency(chartObject
									.getYearFrequency() + 1);
						}
					}
				}
			}
			listChartObject.add(chartObject);
		}

		for (int i = 0; i < listChartObject.size(); i++) {

			int year1 = listChartObject.get(i).getYear();

			for (int j = 0; j < listChartObject.size(); j++) {
				int year2 = listChartObject.get(j).getYear();

				if ((year1 == year2) && (!(i == j))) {
					listChartObject.get(i).setYearFrequency(
							listChartObject.get(i).getYearFrequency() + 1);
				}
			}
		}

		Collections.sort(listChartObject, new Comparator<ChartObject>() {

			@Override
			public int compare(ChartObject ca1, ChartObject ca2) {
				return (ca1.getYear()).compareTo((ca2.getYear()));
			}

		});

		return listChartObject;
	}

	public static JTable getScrollPaneBackward(
			List<ChartObject> listChartObject) {

		Vector<String> columnNames = new Vector<String>();

		columnNames.addElement("journal");
		columnNames.addElement("relative frequency");


		Vector<Vector> rowData = new Vector<Vector>();
		for (ChartObject chartObject : listChartObject) {


			Vector<String> row = new Vector<String>();

			row.addElement(chartObject.getJournal());
			row.addElement(String.valueOf(chartObject.getJournalFrequency()));

			rowData.add(row);

		}
		JTable table = new JTable(rowData, columnNames);
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dm);
		sorter.setComparator(1, new Comparator<String>() {

			@Override
			public int compare(String string1, String string2) {
				return Integer.parseInt(string1) - Integer.parseInt(string2);
			}
		});

		table.setRowSorter(sorter);
		table.setRowSelectionAllowed(true);
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null) {
			defaults.put("Table.alternateRowColor", new Color(240, 240, 240));
		}
		table.setEnabled(false);


		return table;
	}

	public static JScrollPane getScrollPaneAuthors(
			List<ChartObject> listChartObject) {

		Vector<String> columnNames = new Vector<String>();

		columnNames.addElement("author");
		columnNames.addElement("relative frequency");


		Vector<Vector> rowData = new Vector<Vector>();

		for (ChartObject chartObject : listChartObject) {


			Vector<String> row = new Vector<String>();

			row.addElement(chartObject.getAuthors());
			row.addElement(String.valueOf(chartObject.getAuthorsFrequency()));

			rowData.add(row);

		}
		JTable table = new JTable(rowData, columnNames);
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dm);
		sorter.setComparator(1, new Comparator<String>() {

			@Override
			public int compare(String string1, String string2) {
				return Integer.parseInt(string1) - Integer.parseInt(string2);
			}
		});
		table.setRowSorter(sorter);
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null) {
			defaults.put("Table.alternateRowColor", new Color(240, 240, 240));
		}
		table.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(table);

		return scrollPane;
	}

	public static List<String> getJournalList() {
		List<String> journal = new ArrayList<String>();

		for (ReferencedPaper ref : DataHolder.listReferencedPaperAll) {

			if ((!ref.getTitle().equals("-")) && (ref.getMergeID() == null)) {

				if (!ref.getJournalAbbrev().equals("-") && (ref.getJournalAbbrev() != null)) {
					journal.add(ref.getJournalAbbrev());
				}
			}
		}
		for (PDFPaper paper : DataHolder.listPDFPaper) {
			if (!paper.getJournalAbbrev().equals("-") && (paper.getJournalAbbrev() != null)) {
				journal.add(paper.getJournalAbbrev());
			}
		}
		List<String> journalCleaned = journal;

		for (int i = 0; i < journal.size(); i++) {
			for (int j = i + 1; j < journal.size(); j++) {
				double similar = Helper.compareStrings(journal.get(i),
						journal.get(j));

				if (similar > 0.8) {
					journalCleaned.set(j, journal.get(i));
				}
			}
		}
		Set<String> chas = new HashSet<>();
		chas.addAll(journalCleaned);
		journalCleaned.clear();
		journalCleaned.addAll(chas);

		return journalCleaned;
	}

	public static List<ChartObject> getJournalChartObjectReferences(
			List<String> listJournal) {

		List<ChartObject> listChartObjectJournal = new ArrayList<ChartObject>();

		for (int i = 0; i < listJournal.size(); i++) {

			ChartObject chartObject = new ChartObject();
			chartObject.setJournalFrequency(0);

			String journal = listJournal.get(i);
			chartObject.setJournal(journal);

			for (ReferencedPaper ref : DataHolder.listReferencedPaperAll) {

				if ((!ref.getTitle().equals("-")) && (ref.getMergeID() == null)) {


					double similar = Helper.compareStrings(listJournal.get(i),
							ref.getJournalAbbrev());

					if (similar > 0.9) {
						chartObject.setJournalFrequency(chartObject
								.getJournalFrequency() + 1);
						ref.setJournalAbbrev(listJournal.get(i));

					}

				}

			}
			if (chartObject.getJournalFrequency() > 0) {
				listChartObjectJournal.add(chartObject);

			}


		}


		return listChartObjectJournal;
	}

	public static List<ChartObject> getJournalChartObjectPDF(
			List<String> listJournal) {

		List<ChartObject> listChartObjectJournal = new ArrayList<ChartObject>();

		for (int i = 0; i < listJournal.size(); i++) {

			ChartObject chartObject = new ChartObject();
			chartObject.setJournalFrequency(0);

			String journal = listJournal.get(i);
			chartObject.setJournal(journal);

			for (PDFPaper paper : DataHolder.listPDFPaper) {

				double similar = Helper.compareStrings(listJournal.get(i),
						paper.getJournalAbbrev());

				if (similar > 0.9) {
					chartObject.setJournalFrequency(chartObject
							.getJournalFrequency() + 1);
				}
			}
			if (chartObject.getJournalFrequency() > 0) {
				listChartObjectJournal.add(chartObject);

			}

		}

		return listChartObjectJournal;
	}

	public static List<ChartObject> getAuthorsChartObjectPDF(
			List<String> listAuthors) {

		List<ChartObject> listChartObject = new ArrayList<ChartObject>();

		for (int i = 0; i < listAuthors.size(); i++) {
			ChartObject chartObject = new ChartObject();
			chartObject.setAuthorsFrequency(0);

			String stringAuthorList = listAuthors.get(i);
			chartObject.setAuthors(stringAuthorList);

			for (PDFPaper paper : DataHolder.listPDFPaper) {

				if (paper.getAuthors() != null) {

					for (int j = 0; j < paper.getAuthors().size(); j++) {

						String stringAuthor = paper.getSingleAuthor(j);

						if (!stringAuthor.equals("-") && (stringAuthor != null)) {

							double similar = Helper.compareStrings(stringAuthor,
									stringAuthorList);

							if (similar == 1) {
								chartObject.setAuthorsFrequency(chartObject
										.getAuthorsFrequency() + 1);
							}
						}
					}
				}
			}
			if (chartObject.getAuthorsFrequency() > 0) {
				listChartObject.add(chartObject);
			}
		}
		return listChartObject;
	}

	public static List<ChartObject> getAuthorsChartObjectReferences(
			List<String> listAuthors) {

		ArrayList<ChartObject> listChartObject = new ArrayList<ChartObject>();

		for (int i = 0; i < listAuthors.size(); i++) {
			ChartObject chartObject = new ChartObject();
			chartObject.setAuthorsFrequency(0);

			String author = listAuthors.get(i);
			chartObject.setAuthors(author);

			for (ReferencedPaper ref : DataHolder.listReferencedPaperAll) {


				if ((!ref.getTitle().equals("-")) && (ref.getMergeID() == null)) {

					if (ref.getAuthors() != null) {

						for (int j = 0; j < ref.getAuthors().size(); j++) {


							String stringAuthorString = ref.getSingleAuthor(j);

							double similar = Helper.compareStrings(author,
									stringAuthorString);

							if (similar == 1) {
								chartObject.setAuthorsFrequency(chartObject
										.getAuthorsFrequency() + 1);
							}
						}
					}
				}
			}
			if (chartObject.getAuthorsFrequency() > 0) {
				listChartObject.add(chartObject);
			}
		}
		return listChartObject;
	}

	public static List<String> getAuthorListReferences() {

		List<String> authors = new ArrayList<String>();

		for (ReferencedPaper ref : DataHolder.listReferencedPaperAll) {

			if ((!ref.getTitle().equals("-")) && (ref.getMergeID() == null)) {


				if (ref.getAuthors() != null) {

					for (int i = 0; i < ref.getAuthors().size(); i++)

						if (!ref.getAuthors().get(i).getLastName().equals("-")) {
							authors.add(ref.getSingleAuthor(i));
						}
				}
			}
		}

		Set<String> chas = new HashSet<>();
		chas.addAll(authors);
		authors.clear();
		authors.addAll(chas);

		return authors;
	}

	public static List<String> getAuthorListPDF() {

		List<String> authors = new ArrayList<String>();

		for (PDFPaper paper : DataHolder.listPDFPaper) {

			if (paper.getAuthors() != null) {

				for (int i = 0; i < paper.getAuthors().size(); i++) {

					authors.add(paper.getSingleAuthor(i));
				}
			}
		}
		Set<String> chas = new HashSet<>();
		chas.addAll(authors);
		authors.clear();
		authors.addAll(chas);

		for (String string : authors) {
			System.out.println(string);
		}
		return authors;
	}


}