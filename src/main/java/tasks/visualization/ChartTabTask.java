package tasks.visualization;

import functionality.MainWindow;
import tasks.visualization.charts.AuthorTask;
import tasks.visualization.charts.JournalTask;
import tasks.visualization.charts.TimeTask;

import javax.swing.*;

/**
 * Created by Philipo on 24.09.2017.
 */
public class ChartTabTask extends SwingWorker<Void, Integer> {


    public ChartTabTask() {


    }


    @Override
    protected Void doInBackground() throws Exception {


        AuthorTask authorTask = new AuthorTask(MainWindow.progressBarAuthor);
        authorTask.execute();

        TimeTask timeTask = new TimeTask(MainWindow.progressBarYear);
        timeTask.execute();

        JournalTask journalTask = new JournalTask(MainWindow.progressBarJournal);
        journalTask.execute();

        //title task

        return null;
    }

}
