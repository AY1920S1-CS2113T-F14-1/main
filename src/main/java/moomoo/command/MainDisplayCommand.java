package moomoo.command;

import moomoo.feature.Budget;
import moomoo.feature.category.CategoryList;
import moomoo.feature.MooMooException;
import moomoo.feature.ScheduleList;
import moomoo.feature.storage.Storage;
import moomoo.feature.Ui;
import moomoo.feature.MainDisplay;

public class MainDisplayCommand extends Command {
    private int month;
    private int year;

    /**
     * Command that takes in a month and year from parser to be converted into int.
     * @param month Month that the user wants to view a summary of.
     * @param year Year that the user wants to view a summary of.
     */
    public MainDisplayCommand(int month, int year) {
        super(false, "");
        this.month = month;
        this.year = year;
    }

    /**
     * Executes a MainDisplay Command when called.
     * @param calendar ScheduleList object containing the schedules.
     * @param budget Budget object containing the budget.
     * @param categoryList CategoryList object containing the categories.
     * @param storage Storage object for interaction with filesystem.
     */
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Storage storage) throws MooMooException {
        MainDisplay newMainDisplay = new MainDisplay();
        int rows;
        int cols;
        if (this.month != 0 && this.year != 0) {      // means user did specify a valid month and year
            rows = newMainDisplay.getMonthsExpSize(categoryList, month, year);
            cols = newMainDisplay.getCatListSize(categoryList);
        } else {
            cols = newMainDisplay.getCatListSize(categoryList);
            rows = newMainDisplay.getMaxCatSize(categoryList);
        }

        DetectOsCommand getOS = new DetectOsCommand();
        String output = "";
        if (getOS.osName.contains("mac")) {
            try {
                output = newMainDisplay.newToPrint(month, year, rows, cols, categoryList, budget, 0);
                System.out.println("MainDisplay on Linux -> Color available");
            } catch (Exception e) {
                throw new MooMooException("An error has occurred. Please close the terminal.");
            }
        } else if (getOS.osName.contains("win")) {
            try {
                output = newMainDisplay.newToPrint(month, year, rows, cols, categoryList, budget, 1);
                System.out.println("MainDisplay on Windows -> Color currently unavailable");
            } catch (Exception e) {
                throw new MooMooException("An error has occurred. Please close the terminal.");
            }
        }
        Ui.printMainDisplay(output);
    }
}
