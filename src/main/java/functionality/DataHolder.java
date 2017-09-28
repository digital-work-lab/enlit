package functionality;

import model.PDFPaper;
import model.ReferencedPaper;
import org.grobid.core.engines.Engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philipo on 13.09.2017.
 */
public class DataHolder {

    public static Engine engine;
    public static List<ReferencedPaper> listReferencedPaperAll = new ArrayList<>();
    public static List<PDFPaper> listPDFPaper = new ArrayList<>();
    public static List<File> listFiles;
    public static String pGrobidProperties;
    public static String pGrobidHome;

    public static String getpGrobidProperties() {
        return pGrobidProperties;
    }

    public static void setpGrobidProperties(String pGrobidProperties) {
        DataHolder.pGrobidProperties = pGrobidProperties;
    }

    public static String getpGrobidHome() {
        return pGrobidHome;
    }

    public static void setpGrobidHome(String pGrobidHome) {
        DataHolder.pGrobidHome = pGrobidHome;
    }

    public static Engine getEngine() {
        return engine;
    }

    public static void setEngine(Engine engine) {
        DataHolder.engine = engine;
    }

    public static List<ReferencedPaper> getListReferencedPaperAll() {
        return listReferencedPaperAll;
    }

    public static void setListReferencedPaperAll(List<ReferencedPaper> listReferencedPaperAll) {
        DataHolder.listReferencedPaperAll = listReferencedPaperAll;
    }

    public static List<PDFPaper> getListPDFPaper() {
        return listPDFPaper;
    }

    public static void setListPDFPaper(List<PDFPaper> listPDFPaper) {
        DataHolder.listPDFPaper = listPDFPaper;
    }

    public static List<String> getListEmptyOrError() {
        return listEmptyOrError;
    }

    public static void setListEmptyOrError(List<String> listEmptyOrError) {
        DataHolder.listEmptyOrError = listEmptyOrError;
    }

    public static List<ReferencedPaper> getListReferencedPaperNonRedundant() {
        return listReferencedPaperNonRedundant;
    }

    public static void setListReferencedPaperNonRedundant(List<ReferencedPaper> listReferencedPaperNonRedundant) {
        DataHolder.listReferencedPaperNonRedundant = listReferencedPaperNonRedundant;
    }

    public static List<String> listEmptyOrError = new ArrayList<>();
    public static List<ReferencedPaper> listReferencedPaperNonRedundant;
}
