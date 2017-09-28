package ExportImport;

import functionality.MainWindow;

import javax.swing.*;

/**
 * Created by Philipo on 05.09.2017.
 */
public class ExportOptionWindow {

    String option;

    public ExportOptionWindow() {
        Object[] possibilities = {"all references", "exclusively new references"};
        option = (String) JOptionPane.showInputDialog(
                MainWindow.frame,
                "Which export should be processed for the result set?",
                "Export scope",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "all references");
    }

    public boolean getOption()
    {
        boolean b;
        if(option.equals("all references"))
        {
            b = true;
        }
        else
        {
            b = false;
        }
        return b;
    }
}
