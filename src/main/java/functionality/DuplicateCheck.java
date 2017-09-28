package functionality;

import info.debatty.java.stringsimilarity.Levenshtein;
import model.ReferencedPaper;

import java.util.*;

/**
 * Created by Philipo on 13.09.2017.
 */
public class DuplicateCheck {

    private static final double DUPLICATE_THRESHOLD = 0.75; // The overall threshold to signal a duplicate pair


    private static final int NOT_EQUAL = 0;
    private static final int EQUAL = 1;

    private static final int EMPTY_IN_ONE = 2;
    private static final int EMPTY_IN_TWO = 3;
    private static final int EMPTY_IN_BOTH = 4;

    // Non-required fields are investigated only if the required fields give a value within
    // the doubt range of the threshold:
    private static final double DOUBT_RANGE = 0.05;

    private static final double REQUIRED_WEIGHT = 3; // Weighting of all required fields

    // Extra weighting of those fields that are most likely to provide correct duplicate detection:
    private static final Map<String, Double> FIELD_WEIGHTS = new HashMap<>();

    static {
        DuplicateCheck.FIELD_WEIGHTS.put("Author", 2.5);
        DuplicateCheck.FIELD_WEIGHTS.put("Title", 9.);
        DuplicateCheck.FIELD_WEIGHTS.put("Year", 2.);
    }

    private DuplicateCheck() {
    }

    public static boolean isDuplicate(ReferencedPaper ref1, ReferencedPaper ref2)
    {
        double req[];

        List<String> fields = new ArrayList<>();
        fields.add("Title");
        fields.add("Author");
        fields.add("Year");

        req = compareFieldSet(fields, ref1, ref2);


        if (Math.abs(req[0] - DuplicateCheck.DUPLICATE_THRESHOLD) > DuplicateCheck.DOUBT_RANGE) {


            return req[0] >= DuplicateCheck.DUPLICATE_THRESHOLD;
        }

        return req[0] >= DuplicateCheck.DUPLICATE_THRESHOLD;
    }

    private static double[] compareFieldSet(List<String> fields, ReferencedPaper ref1, ReferencedPaper ref2)
    {

        double res = 0;
        double totWeights = 0;

        for(String field : fields)
        {
            double weight;
            weight = DuplicateCheck.FIELD_WEIGHTS.get(field);
            totWeights += weight;

            int result = DuplicateCheck.compareSingleField(field, ref1, ref2);

            if(result == EQUAL)
            {
                res += weight;
            }
            else if (result == EMPTY_IN_BOTH)
            {
                totWeights -= weight;
            }
        }

        if(totWeights > 0)
        {
            return new double[]{res / totWeights, totWeights};
        }
        return new double[]{0.5, 0.0};
    }

    private static int compareSingleField(String field, ReferencedPaper ref1, ReferencedPaper ref2) {


        if ("Title".equals(field)) {
            String titleOne = ref1.getTitle().toLowerCase(Locale.ROOT);
            String titleTwo = ref2.getTitle().toLowerCase(Locale.ROOT);

            if(titleOne.equals("-"))
            {
                if(titleTwo.equals("-"))
                {
                    return EMPTY_IN_BOTH;
                }
                return EMPTY_IN_ONE;
            } else if (titleTwo.equals("-")) {

                return EMPTY_IN_TWO;
            }
            double similarity = DuplicateCheck.correlateByWords(titleOne, titleTwo);
            if (similarity > 0.8) {
                return EQUAL;


            }
            return NOT_EQUAL;

        } else if ("Author".equals(field)) {

            if(ref1.getAuthors() == null)
            {
                if(ref2.getAuthors() == null)
                {
                    return EMPTY_IN_BOTH;
                }
                return EMPTY_IN_ONE;
            } else if (ref2.getAuthors() == null) {

                return EMPTY_IN_TWO;
            }
            String authorOne = (ref1.getAuthors().get(0).getLastName() + ref1.getAuthors().get(0).getFirstName()).toLowerCase();
            String authorTwo = (ref2.getAuthors().get(0).getLastName() + ref2.getAuthors().get(0).getFirstName()).toLowerCase();
            double similarity = DuplicateCheck.correlateByWords(authorOne, authorTwo);
            if (similarity > 0.8) {

                return EQUAL;
            }
            return NOT_EQUAL;

        } else if ("Year".equals(field)) {
            String journalOne = ref1.getYear();
            String journalTwo = ref2.getYear();

            if(journalOne.equals("-"))
            {
                if(journalTwo.equals("-"))
                {
                    return EMPTY_IN_BOTH;
                }
                return EMPTY_IN_ONE;
            } else if (journalTwo.equals("-")) {

                return EMPTY_IN_TWO;
            }
            double similarity = DuplicateCheck.correlateByWords(journalOne, journalTwo);
            if (similarity == 1) {

                return EQUAL;
            }
            return NOT_EQUAL;
        }
        else {
            return 2;
        }
    }


    public static double correlateByWords(String s1, String s2) {
        String[] w1 = s1.split("\\s");
        String[] w2 = s2.split("\\s");
        int n = Math.min(w1.length, w2.length);
        int misses = 0;
        for (int i = 0; i < n; i++) {
            double corr = similarity(w1[i], w2[i]);
            if (corr < 0.75) {
                misses++;
            }
        }
        double missRate = (double) misses / (double) n;
        return 1 - missRate;
    }


    private static double similarity(String first, String second) {
        String longer = first;
        String shorter = second;

        if (first.length() < second.length()) {
            longer = second;
            shorter = first;
        }

        int longerLength = longer.length();
        // both strings are zero length
        if (longerLength == 0) {
            return 1.0;
        }
        double sim = (longerLength - editDistanceIgnoreCase(longer, shorter)) / (double) longerLength;
        return sim;
    }

    public static double editDistanceIgnoreCase(String a, String b)
    {
        Levenshtein METRIC_DISTANCE = new Levenshtein();

        return METRIC_DISTANCE.distance(a.toLowerCase(Locale.ENGLISH), b.toLowerCase(Locale.ENGLISH));
    }
}
